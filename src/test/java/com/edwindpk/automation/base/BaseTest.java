package com.edwindpk.automation.base;

import com.edwindpk.automation.utils.LoggerUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.Logger;

public class BaseTest {

    protected WebDriver driver;  // Accessible to test classes
    private static final Logger log = LoggerUtils.getLogger(BaseTest.class);

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        long threadId = Thread.currentThread().getId();
        log.info("========== [Thread-{}] Test Started ==========", threadId);
        log.info("[Thread-{}] Requested Browser: {}", threadId, browser);

        DriverManager.initDriver(browser);
        driver = DriverManager.getDriver();

        log.info("[Thread-{}] Driver initialized and navigated to base URL", threadId);
    }

    @AfterMethod
    public void tearDown() {
        long threadId = Thread.currentThread().getId();
        log.info("[Thread-{}] Tearing down the test and quitting driver", threadId);

        DriverManager.quitDriver();

        log.info("========== [Thread-{}] Test Finished ==========\n", threadId);
    }
}
