package com.edwindpk.automation.practice;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.edwindpk.automation.pages.LoginPage;

public class PracticePage {
	
	 private static final Logger logger = LogManager.getLogger(LoginPage.class); // ✅ Logger added

	    private WebDriver driver;
	    private WebDriverWait wait;
	    
	    @FindBy(xpath = "//h2[contains(text(),'Login Page')]")
	    private WebElement loginPageHeader;
	    
	    @FindBy(id="username")
	    private WebElement userNameinput;
	    
	    @FindBy(xpath = "//input[@id='password']")
	    private WebElement passwordinput;
	    
	    @FindBy(xpath = "//button[@type='submit']")
	    private WebElement loginBtn;
	    
	    @FindBy(xpath = "//div[@id='flash-messages']//div")
	    private WebElement feedbackmsg;
	    
	    @FindBy(xpath = "//a//i[contains(text(),' Logout')]")
	    private WebElement logoutBtn;
	    
	    public PracticePage(WebDriver driver) {
	    	this.driver=driver;
	    	this.wait=new WebDriverWait(driver, Duration.ofSeconds(10));
	    	PageFactory.initElements(driver, this);
	    }
	    
	    public void navigateToLoginPage() {
	    	driver.navigate().to("https://the-internet.herokuapp.com/login");
	    }
	    
	    public String getcurrentpageUrl() {
	    	return driver.getCurrentUrl();
	    }
	   
	    public boolean testLogOutBtn() {
	    	boolean logoutIsVisible = logoutBtn.isDisplayed();
	    	return logoutIsVisible;
	    }
	    
	    public String loginpageheader() {
	    	return loginPageHeader.getText();
	    }
	    public String login(String username, String password) {
	    	userNameinput.sendKeys(username);
	    	passwordinput.sendKeys(password);
	    	loginBtn.click();	    	
	    	String message = feedbackmsg.getText();
	    	System.out.println(message);	    	
	    	driver.navigate().back();
	    	driver.navigate().refresh();
	    	driver.navigate().forward();
	    	return message;
	    	
	    	
	    	
	    }

}
