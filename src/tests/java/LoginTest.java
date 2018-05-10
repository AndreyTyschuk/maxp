package tests.java;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import main.pages.HomePage;
import main.pages.LoginPage;
import main.utils.DriverManager;
import main.utils.PropertyLoader;
import main.utils.Screenshooter;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import static main.core.WaitUtil.waitForAsyncExecution;

public class LoginTest extends BaseTest {

    @BeforeClass
    public void beforeClass() {
        WebDriver driver = DriverManager.getInstance().createDriver(PropertyLoader.getInstance().getBrowserName());
        driver.manage().window().
                setSize(new Dimension(1600, 900));
        //.maximize();
    }

    @DataProvider()
    public Object[][] dataForLoginWithLowerAndUpperCaseTest(ITestContext context) {
            return new Object[][]{
                    {"atyschuk@gmail.com", "Andrey1209"},
                    {"ATYSCHUK@gmail.com", "Andrey1209"},
            };
        }

    @Feature("Register/Login")
    @Story("User login with lower and uppercase")
    @Test(dataProvider = "dataForLoginWithLowerAndUpperCaseTest")
    public void loginWithLowerAndUpperCaseTest(String login, String password){
        LoginPage loginPage = new LoginPage();
        loginPage.open();
        HomePage homePage = loginPage.login(login, password);
        waitForAsyncExecution();
        hardAssert.assertEquals(login.toLowerCase(), homePage.getLoginForLoggedUser().toLowerCase());

    }

    @DataProvider()
    public Object[][] dataForLoginNegaive(ITestContext context) {
        return new Object[][]{
                {"atyschuk@gmail.com", "Andrey1208"},
                {"atyschuk1@gmail.com", "Andrey1209"},
        };
    }

    @Feature("Register/Login")
    @Story("User login with lower and uppercase")
    @Test(dataProvider = "dataForLoginNegaive")
    public void dataForLoginNegaive(String login, String password){
        LoginPage loginPage = new LoginPage();
        loginPage.open();
        loginPage.login(login, password);
        waitForAsyncExecution();
        hardAssert.assertEquals(loginPage.getErrorMessage(), "Password or email are incorrect");

    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (!result.isSuccess()) {
            Screenshooter.getInstance().takeScreenshotForManual(result);
        }
        DriverManager.getInstance().getDriver().manage().deleteAllCookies();
    }

    @AfterClass
    public void afterClass() {
        DriverManager.getInstance().getDriver().quit();
    }
}
