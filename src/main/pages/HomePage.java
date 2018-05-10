package main.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends PageObject {

    @FindBy(xpath = "//a[contains(@href, 'telefony-tv-i-ehlektronika')]")
    private WebElement phonesTvElectronicsLink;

    @Step("User opens login page")
    public void open() {
        driver.get(baseUrl);
        driver.navigate().refresh();
    }

    @Step
    public String getLoginForLoggedUser(){
        waitUntilVisible("//button[@id='setting']", 3).click();
        return findBy("//header//li/span[contains(@class, 'text-primary')]").getText();

    }

}