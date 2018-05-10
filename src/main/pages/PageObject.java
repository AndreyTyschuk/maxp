package main.pages;

import io.qameta.allure.Step;
import main.core.Logger;
import main.utils.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import main.utils.PropertyLoader;

import java.util.ArrayList;
import java.util.List;

public abstract class PageObject {

    protected WebElement element;
    protected final WebDriver driver;
    protected Logger logger;
    private List<String> windowHandles;
    String originalWindow;
    private final int DEFAULT_WAIT_TIME = 5;
    public final String baseUrl;

    public PageObject() {
        driver = DriverManager.getInstance().getDriver();
        logger = Logger.getInstance();
        baseUrl = PropertyLoader.getInstance().getBaseUrl();
        originalWindow = driver.getWindowHandle();
        if (windowHandles == null) {
            windowHandles = new ArrayList<>();
            windowHandles.add(0, "");
        }
    }

    public WebElement waitUntilVisible(final String xpathSelector, long time) {
        logger.info("FindBy: " + xpathSelector);
        return new WebDriverWait(driver, time, 250)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathSelector)));
    }

    public WebElement findBy(final String xpathSelector) {

        if (element != null) {
            logger.info("FindBy: " + element + " -> " + xpathSelector);
            waitUntilVisible(xpathSelector, 2);
            return element.findElement(By.xpath(xpathSelector));

        } else {
            logger.info("FindBy: " + xpathSelector);
            waitUntilVisible(xpathSelector, 2);
            return driver.findElement(By.xpath(xpathSelector));
        }
    }

    public final void findByAndType(final String xpathSelector, final String value) {
        WebElement elem = findBy(xpathSelector);
        elem.clear();
        elem.sendKeys(value);
        if (elem.getAttribute("value").length() != value.length()) {
            elem.clear();
            elem.sendKeys(value);
        }
    }
}
