package com.edwindpk.automation.pages;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebTablesPracticePage {

	private WebDriver driver;
	private WebDriverWait wait;
	private static final Logger logger = LogManager.getLogger(WebTablesPage.class);

	public WebTablesPracticePage(WebDriver driver) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath = "//table[@id='taskTable']")
	WebElement table;

	@FindBy(xpath = "//table[@id='taskTable']//thead//tr//th")
	List<WebElement> headers;

	@FindBy(xpath = "//table[@id='taskTable']//tbody//tr")
	List<WebElement> rows;
	
	
	
	 public void navigateTo() {
	        String url = "https://testautomationpractice.blogspot.com/";
	        driver.get(url);
	        logger.info("🔹 Navigated to Web Tables page: {}", url);
	    }
	 
	 public void getnetworkdata() {
		 int columnno=-1;
		 
		 for(int i=0;i<headers.size();i++) {
			 if(headers.get(i).getText().equalsIgnoreCase("Network (Mbps)")) {
				 columnno=i;
				 break;
			 }		 
		 }
		 
		 for(int i=0;i<rows.size();i++) {
			 
			 List<WebElement> columnsincurrow = rows.get(i).findElements(By.tagName("td"));
			 
			 System.out.println(columnsincurrow.get(columnno).getText());
			 
			 
		 }
		 
		 
		 
		 
		 
		 
	 }
	 

}
