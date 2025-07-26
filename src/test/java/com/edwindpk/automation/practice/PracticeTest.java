package com.edwindpk.automation.practice;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.edwindpk.automation.base.BaseTest;

public class PracticeTest extends BaseTest{
	
	@Test
	public void testLoginPage() {
		PracticePage practicePage = new PracticePage(driver);
		practicePage.navigateToLoginPage();
		Assert.assertTrue(practicePage.loginpageheader().contains("Login"));
		String msg = practicePage.login("tomsmith", "SuperSecretPassword!");
		Assert.assertTrue(msg.contains("You logged into a secure area!"), "msg not proper");
		Assert.assertTrue(practicePage.testLogOutBtn(),"logoutbtn not visible");
	}

}
