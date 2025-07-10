package com.edwindpk.automation.tests;

import org.testng.annotations.Test;

import com.edwindpk.automation.base.BaseTest;
import com.edwindpk.automation.pages.NetomiPage;

public class NetomiTest extends BaseTest {

	
	
	
	@Test
	
	public void testNetomi() {
		
		NetomiPage netomi = new NetomiPage(driver);
		System.out.println("Browser Launcer with url");
		netomi.clickButton();
		
		
	}
}
