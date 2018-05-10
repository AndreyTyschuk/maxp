package tests.java;

import main.pages.LoginPage;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;
import main.utils.*;

import java.lang.reflect.Method;


public abstract class BaseTest {
    protected Assertion hardAssert = new Assertion();
    protected SoftAssert softAssert = new SoftAssert();


}
