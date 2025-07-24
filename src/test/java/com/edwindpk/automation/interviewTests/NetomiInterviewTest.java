package com.edwindpk.automation.interviewTests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.edwindpk.automation.base.BaseTest;
import com.edwindpk.automation.interviewPages.NetomiInterviewPage;

public class NetomiInterviewTest extends BaseTest {
	
	@Test(enabled=false)
	
	public void logintest() {
		
		NetomiInterviewPage page = new NetomiInterviewPage(driver);
		page.navigateToherokuappLogin();
		String msg = (page.login("tomsmith" ,"SuperSecretPassword!"));
		boolean flag = false;
		if(msg.contains("You logged into a secure area")) {
			flag=true;
		}
		Assert.assertTrue(flag);
		
		
	}
	
	@Test(enabled=true)
	public void dashboardtest() {
		NetomiInterviewPage page = new NetomiInterviewPage(driver);
		page.navigateToherokuappLogin();
		String title = page.navigateToherokuappdashboard();
		Assert.assertEquals(title,"Dashboard");
	}
	
	
}
