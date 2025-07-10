package com.edwindpk.automation.listeners;

import com.edwindpk.automation.utils.LoggerUtils;
import com.edwindpk.automation.utils.ScreenshotUtils;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TestListener implements ITestListener {

    private static final Logger log = LoggerUtils.getLogger(TestListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getName();
        log.error("❌ Test Failed: " + testName);

        String screenshotPath = ScreenshotUtils.captureScreenshot(testName);
        if (screenshotPath != null) {
            log.info("Screenshot captured at: " + screenshotPath);

            // Attach screenshot to Allure
            try {
                Allure.addAttachment("Failure Screenshot", new FileInputStream(screenshotPath));
            } catch (FileNotFoundException e) {
                log.error("Unable to attach screenshot to Allure", e);
            }
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info("🔹 Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("✅ Test Passed: " + result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("⚠️ Test Skipped: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        log.info("🚀 Test Suite Started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("🏁 Test Suite Finished: " + context.getName());
    }
}
