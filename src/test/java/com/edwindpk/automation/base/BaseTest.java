package com.edwindpk.automation.base;

import com.edwindpk.automation.utils.LoggerUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.Logger;

public class BaseTest {

    protected WebDriver driver;  // Accessible in subclasses like LoginTest
    private static final Logger log = LoggerUtils.getLogger(BaseTest.class);

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        log.info("========== Test Started ==========");
        DriverManager.initDriver(browser);  // Pass browser param here
        driver = DriverManager.getDriver();
        log.info("Driver initialized and navigated to base URL.");
    }

    @AfterMethod
    public void tearDown() {
        log.info("Tearing down the test and quitting driver.");
        DriverManager.quitDriver();
        log.info("========== Test Finished ==========\n");
    }
}
