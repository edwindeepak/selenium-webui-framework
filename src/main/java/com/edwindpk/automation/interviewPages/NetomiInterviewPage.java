package com.edwindpk.automation.interviewPages;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.edwindpk.automation.pages.WebTablesPage;

public class NetomiInterviewPage {

	private WebDriver driver;
	private WebDriverWait wait;
	private static final Logger logger = LogManager.getLogger(WebTablesPage.class);

	public NetomiInterviewPage(WebDriver driver) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//table[@id='table1']//thead//tr//th")
	List<WebElement> headers;

	@FindBy(xpath = "//table[@id='table1']//tbody//tr")
	List<WebElement> rows;

	@FindBy(xpath = "//select")
	WebElement selectelement;

	@FindBy(xpath = "//button[contains(text(),'Click for JS Prompt')]")
	WebElement jsprompt;
	
	
	
	@FindBy(xpath="//input[@id='username']")
	WebElement userid;
	
	@FindBy(xpath="//input[@id='password']")
	WebElement pwd;
	
	@FindBy(xpath = "//button[@type='submit']")
	WebElement loginbtn;
	
	
	@FindBy(xpath = "//div[@id='flash-messages']//div")
	WebElement message;
	

	public void navigateToTablesPage() {
		String url = "https://the-internet.herokuapp.com/tables";
		driver.get(url);
		logger.info("🔹 Navigated to Web Tables page: {}", url);
	}
	
	public void navigateToherokuappLogin() {
		String url = "https://the-internet.herokuapp.com/login";
		driver.get(url);
	}
	
	public String  login(String username,String password) {
		userid.clear();
		userid.sendKeys(username);
		pwd.clear();
		pwd.sendKeys(password);
		loginbtn.click();
		
		String loginmsg = message.getText();
		System.out.println(loginmsg);
		
		return loginmsg;
	}
	
	public String navigateToherokuappdashboard() {
		String url = "https://the-internet.herokuapp.com/dashboard";
		driver.get(url);
		return driver.getTitle();
	}


	public void navigateToSelectPage() {
		String url = "https://the-internet.herokuapp.com/dropdown";
		driver.get(url);
		logger.info("🔹 Navigated to Web Tables page: {}", url);
	}

	public void selectoption(String option) throws InterruptedException {

		Select select = new Select(selectelement);

		List<WebElement> options = select.getOptions();

		for (WebElement x : options) {
			System.err.println(x.getText());
		}

		select.selectByVisibleText(option);
		Thread.sleep(5000);
	}

	public void getDue(String name) {

		int columnIndex = -1;

		for (int i = 0; i < headers.size(); i++) {
			if (headers.get(i).getText().equalsIgnoreCase("Due")) {
				columnIndex = i + 1;
				break;
			}
		}

		if (columnIndex == -1) {
			System.out.println("Column Due not found");
		}

		for (int i = 1; i < rows.size(); i++) {

			String firstnamexpath = "//table[@id='table1']//tbody//tr[" + i + "]//td[2]";
			WebElement firstname = driver.findElement(By.xpath(firstnamexpath));

			if (firstname.getText().equalsIgnoreCase(name)) {
				String due = "//table[@id='table1']//tbody//tr[" + i + "]//td[" + columnIndex + "]";
				System.out.println(driver.findElement(By.xpath(due)).getText());
			}

		}

	}

	// Navigate to JS Alerts page
	public void navigateToAlertsPage() {
		String url = "https://the-internet.herokuapp.com/javascript_alerts";
		driver.get(url);
		logger.info("🔹 Navigated to JS Alerts page");
	}

	// Handle JS Alerts
	public void handleAlerts() throws InterruptedException {
		// Alert
		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		driver.switchTo().alert().accept();

		// Confirm
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		driver.switchTo().alert().dismiss();

		// Prompt
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		driver.switchTo().alert().sendKeys("Netomi Candidate");
		driver.switchTo().alert().accept();

		Thread.sleep(2000);
	}

	// Navigate to iframe page
	public void navigateToIframePage() {
		String url = "https://the-internet.herokuapp.com/iframe";
		driver.get(url);
		logger.info("🔹 Navigated to iframe page");
	}

	// Interact with iframe
	public void handleIframe() throws InterruptedException {
	    driver.get("https://the-internet.herokuapp.com/iframe");
	    logger.info("🔹 Navigated to iframe page");
	    driver.switchTo().frame("mce_0_ifr");
	    WebElement editor = driver.findElement(By.id("tinymce"));

	    editor.sendKeys(Keys.CONTROL + "a");
	    editor.sendKeys(Keys.DELETE);
	    editor.sendKeys("Hi, this is Edwin");
	    
	    driver.switchTo().defaultContent(); // always switch back!
	}

	// Navigate to windows page
	public void navigateToWindowsPage() {
		String url = "https://the-internet.herokuapp.com/windows";
		driver.get(url);
		logger.info("🔹 Navigated to multiple windows page");
	}

	// Handle new window
	public void handleWindow() {
		String original = driver.getWindowHandle();
		driver.findElement(By.linkText("Click Here")).click();

		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(original)) {
				driver.switchTo().window(handle);
				System.out.println("New window title: " + driver.getTitle());
				driver.close();
			}
		}

		driver.switchTo().window(original);
	}

}
