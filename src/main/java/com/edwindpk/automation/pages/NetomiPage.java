package com.edwindpk.automation.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NetomiPage {
	
	 private WebDriver driver;
	 private WebDriverWait wait;
	    
	 public NetomiPage(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        PageFactory.initElements(driver, this);
	    }
	 
	 @FindBy(xpath = "(//a[contains(text(),'Click Here')])[1]")
	 WebElement clickBtn;
	 
	 @FindBy(xpath = "//li[@id='iFrame']")
	 WebElement framebtn;
	 
	 @FindBy(xpath = "//p//iframe")
	 WebElement reqFrame;
	 
	 @FindBy(xpath = "//button[@class='button_search']//following-sibling::input")
	  List<WebElement> searchboxes;
	 
	 @FindBy(xpath = "")
	 WebElement searchIcon;
	 
	 public void clickButton() {
		 
		 wait.until(ExpectedConditions.visibilityOf(clickBtn));
	     clickBtn.click();
	     
	     //Handle if new window
	     //driver.switchTo().newWindow(WindowType.TAB);
	     Set<String> windows = driver.getWindowHandles();
	     List<String> windowlist = new ArrayList<>();
	     for(String x : windows) {
	    	 windowlist.add(x);
	     }
	      driver.switchTo().window(windowlist.get(1));
	     
	    
	     
	     framebtn.click();
	     driver.switchTo().frame(reqFrame);
	     try {
	     searchboxes.get(0).clear();
	     searchboxes.get(0).sendKeys("Selenium");
	     }
	     catch (Exception e) {
	    searchboxes.get(1).clear();
		searchboxes.get(1).sendKeys("Selenium");
		}
	     
	     
	     searchIcon.click();
	     
	     
	     
	 }


}
