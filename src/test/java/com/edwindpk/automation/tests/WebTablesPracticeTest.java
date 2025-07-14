package com.edwindpk.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.edwindpk.automation.base.BaseTest;
import com.edwindpk.automation.pages.WebTablesPage;
import com.edwindpk.automation.pages.WebTablesPracticePage;

public class WebTablesPracticeTest extends BaseTest {
	
	
	@Test	
	 public void testFindRowByColumnText() {
	        WebTablesPracticePage page = new WebTablesPracticePage(driver);
	        page.navigateTo();
	        page.getnetworkdata();

	        
	    }

}
