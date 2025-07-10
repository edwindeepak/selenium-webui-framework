package com.edwindpk.automation.listeners;

import com.edwindpk.automation.config.ConfigReader;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int attempt = 0;
    private final int maxRetry;

    public RetryAnalyzer() {
        String retryConfig = ConfigReader.get("maxRetryCount");
        int retries;
        try {
            retries = Integer.parseInt(retryConfig);
        } catch (NumberFormatException e) {
            retries = 1; // default if invalid/missing
        }
        this.maxRetry = retries;
    }

    @Override
    public boolean retry(ITestResult result) {
        if (attempt < maxRetry) {
            attempt++;
            return true;
        }
        return false;
    }
}
