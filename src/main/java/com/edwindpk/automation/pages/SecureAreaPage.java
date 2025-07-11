package com.edwindpk.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SecureAreaPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "div#flash")
    private WebElement successMessage;

    @FindBy(css = "a.button.secondary.radius")
    private WebElement logoutButton;

    public SecureAreaPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String getSuccessMessage() {
        wait.until(ExpectedConditions.visibilityOf(successMessage));
        return successMessage.getText().trim();
    }

    public boolean isLogoutButtonVisible() {
        wait.until(ExpectedConditions.visibilityOf(logoutButton));
        return logoutButton.isDisplayed();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
