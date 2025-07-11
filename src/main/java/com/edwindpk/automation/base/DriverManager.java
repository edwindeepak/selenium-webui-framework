package com.edwindpk.automation.base;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.edwindpk.automation.config.ConfigReader;
import com.edwindpk.automation.utils.LoggerUtils;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {

    private static final Logger log = LoggerUtils.getLogger(DriverManager.class);

    // Thread-local WebDriver to support parallel execution
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Initializes WebDriver for current thread.
     */
    public static void initDriver(String browserFromTestNG) {
        String browserName = (browserFromTestNG != null && !browserFromTestNG.isBlank())
                ? browserFromTestNG.toLowerCase()
                : ConfigReader.getBrowser().toLowerCase();

        String runMode = ConfigReader.getRunMode().toLowerCase();
        long threadId = Thread.currentThread().getId();

        log.info("[Thread-{}] Launching browser: {} in {} mode", threadId, browserName, runMode);

        try {
            WebDriver driverInstance;

            if ("grid".equals(runMode)) {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setBrowserName(browserName);
                driverInstance = new RemoteWebDriver(new URL(ConfigReader.getGridUrl()), capabilities);
            } else {
                switch (browserName) {
                    case "chrome":
                        WebDriverManager.chromedriver().setup();
                        driverInstance = new ChromeDriver();
                        break;
                    case "firefox":
                        WebDriverManager.firefoxdriver().setup();
                        driverInstance = new FirefoxDriver();
                        break;
                    case "edge":
                        WebDriverManager.edgedriver().setup();
                        driverInstance = new EdgeDriver();
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported browser: " + browserName);
                }
            }

            driver.set(driverInstance);

            WebDriver currentDriver = getDriver();
            currentDriver.manage().window().maximize();
            currentDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            //currentDriver.get(ConfigReader.getBaseUrl());

            log.info("[Thread-{}] Driver setup complete and navigated to base URL", threadId);

        } catch (MalformedURLException e) {
            log.error("Malformed Grid URL: {}", ConfigReader.getGridUrl(), e);
            throw new RuntimeException("Invalid Grid URL format.", e);
        } catch (IllegalArgumentException e) {
            log.error("[Thread-{}] Browser setup error: {}", threadId, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("[Thread-{}] Unexpected error during driver initialization", threadId, e);
            throw new RuntimeException("WebDriver setup failed.", e);
        }
    }

    /**
     * Returns the WebDriver instance for the current thread.
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Quits and removes WebDriver for the current thread.
     */
    public static void quitDriver() {
        long threadId = Thread.currentThread().getId();
        if (driver.get() != null) {
            log.info("[Thread-{}] Quitting driver", threadId);
            driver.get().quit();
            driver.remove();
        }
    }
}
