package com.edwindpk.automation.base;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.edwindpk.automation.config.ConfigReader;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Initialize WebDriver based on the browser parameter passed from TestNG or fallback to config.
     * @param browserFromTestNG Browser name passed from TestNG XML parameter (nullable)
     */
    public static void initDriver(String browserFromTestNG) {
        // Determine browser name from TestNG parameter or config.properties fallback
        String browserName = (browserFromTestNG != null && !browserFromTestNG.isBlank())
                ? browserFromTestNG.toLowerCase()
                : ConfigReader.getBrowser().toLowerCase();

        String runMode = ConfigReader.getRunMode().toLowerCase();

        System.out.println(">>> Launching browser: " + browserName + " in " + runMode + " mode");

        WebDriver driverInstance = null;

        try {
            if ("grid".equals(runMode)) {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                switch (browserName) {
                    case "chrome":
                        capabilities.setBrowserName("chrome");
                        break;
                    case "firefox":
                        capabilities.setBrowserName("firefox");
                        break;
                    case "edge":
                        capabilities.setBrowserName("MicrosoftEdge");
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported browser for Grid: " + browserName);
                }
                driverInstance = new RemoteWebDriver(new URL(ConfigReader.getGridUrl()), capabilities);
            } else {
                // Local mode
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
                        throw new IllegalArgumentException("Unsupported browser for local run: " + browserName);
                }
            }

            driver.set(driverInstance);
            getDriver().manage().window().maximize();
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            getDriver().get(ConfigReader.getBaseUrl());

        } catch (MalformedURLException e) {
            System.err.println("Malformed Grid URL: " + ConfigReader.getGridUrl());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize RemoteWebDriver due to bad grid URL.", e);
        } catch (IllegalArgumentException e) {
            System.err.println("Error during driver initialization: " + e.getMessage());
            throw e;  // Re-throw to fail fast on unsupported browser
        } catch (Exception e) {
            System.err.println("Unexpected error during driver initialization.");
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize WebDriver.", e);
        }
    }

    /**
     * Get current thread's WebDriver instance.
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Quit and remove current thread's WebDriver instance.
     */
    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}
