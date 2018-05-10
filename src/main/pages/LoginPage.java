package main.pages;

import io.qameta.allure.Step;

public class LoginPage extends PageObject {

    @Step("User opens login page")
    public void open() {
        driver.get(baseUrl);
        driver.navigate().refresh();
    }

    @Step("User sign in")
    public HomePage login(String login, String password) {
        findByAndType("//input[@id='login-email']", login);
        findByAndType("//input[@id='login-password']", password);
        findBy("//button[text()= 'Sign in']").click();
        return new HomePage();
    }

    @Step("error message")
    public String getErrorMessage(){
        return waitUntilVisible("//div[contains(@class, 'alert-danger')]/p", 3).getText();
    }
}
