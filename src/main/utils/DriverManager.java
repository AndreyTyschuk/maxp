package main.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {

    private static final String CHROME = "chrome";
    private static final String FIREFOX = "firefox";
    private static final String CHROME_DRIVER_PATH = "src/main/resources/chrome_2.33/chromedriver";

    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();


    private final static DriverManager INSTANCE = new DriverManager();

    private DriverManager() {
    }

    public static DriverManager getInstance() {
        return INSTANCE;
    }

    public WebDriver createDriver(String browser) {


        if (CHROME.equalsIgnoreCase(browser)) {
            setChromeDriver();
            webDriver.set(new ChromeDriver());
        } else if (FIREFOX.equalsIgnoreCase(browser)) {
            webDriver.set(new FirefoxDriver());
        } else {
            throw new UnsupportedOperationException(String.format("Browser %1$s is not supported!", browser));
        }
        return webDriver.get();
    }

    public WebDriver getDriver() {

        return webDriver.get();
    }

    private void setChromeDriver() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
    }
}
