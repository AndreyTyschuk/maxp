package main.core;

import com.google.common.base.Stopwatch;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.testng.SkipException;
import main.utils.DriverManager;

import java.util.Optional;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static java.lang.String.format;
import static java.lang.System.lineSeparator;
import static org.apache.commons.lang3.BooleanUtils.toBoolean;

public class WaitUtil {

    private static String componentsLoadedScript = null;
    private static Predicate<String> componentsLoaded = scriptToCheck -> {
        ((JavascriptExecutor) DriverManager.getInstance().getDriver()).executeScript(scriptToCheck);
        final String cookieValue = Optional.ofNullable(DriverManager.getInstance().getDriver().manage().getCookieNamed("SC" +
                ".componentsAreLoaded"))
                .map(Cookie::getValue)
                .orElse("false");
        return toBoolean(cookieValue);
    };

    public static void waitForAsyncExecution() {
        sleepMillisec(100);
        if (componentsLoadedScript == null) {
            componentsLoadedScript = new StringJoiner(lineSeparator())
                    .add("if ((typeof jQuery !== 'undefined') && (jQuery.active > 0)) {")
                    .add(" document.cookie = 'SC.componentsAreLoaded=false'").add("} else {")
                    .add(" document.cookie = 'SC.componentsAreLoaded=true'").add("};").toString();
        }
//        DriverManager.getInstance().getDriver().manage().deleteCookieNamed("SC.componentsAreLoaded");
        WaitUtil.waitSilently(componentsLoaded, componentsLoadedScript, 30000, 500);
    }

    private static <T> void waitSilently(final Predicate<T> condition, final T arg, final long
            waitTimeout, final long waitInterval) {
        final Stopwatch waitTimer = Stopwatch.createStarted();
        for (long secs = 0; secs <= waitTimeout; secs += waitInterval) {
            sleepMillisec(waitInterval);
            if (condition.test(arg) || (waitTimer.elapsed(TimeUnit.MILLISECONDS) > waitTimeout)) {
                break;
            }
        }
    }

    public static void sleepMillisec(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException("System timer interrupted", e);
        }
    }
}
