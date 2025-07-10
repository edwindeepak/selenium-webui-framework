package com.edwindpk.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.edwindpk.automation.base.BaseTest;
import com.edwindpk.automation.config.ConfigReader;
import com.edwindpk.automation.dataproviders.ExcelDataProvider;
import com.edwindpk.automation.pages.LoginPage;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class LoginTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(LoginTest.class);

    @Epic("Login")
    @Feature("Negative Login")
    @Story("Invalid username/password")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProvider = "loginData", dataProviderClass = ExcelDataProvider.class, enabled = false)
    public void testInvalidLoginWithExcel(String username, String password, String expectedErrorMessage) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        String actualErrorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message mismatch!");
    }

    @Epic("Login")
    @Feature("Positive Login")
    @Story("Valid credentials")
    @Severity(SeverityLevel.BLOCKER)
    @Test(enabled = true)
    public void testValidLogin() {
        String username = ConfigReader.get("validUsername");
        String password = ConfigReader.get("validPassword");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        logger.info("✅ Login attempt made with valid credentials.");
    }
}
