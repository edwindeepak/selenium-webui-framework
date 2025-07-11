package com.edwindpk.automation.tests;

import com.edwindpk.automation.base.BaseTest;
import com.edwindpk.automation.pages.WebTablesPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

@Epic("Web Tables Feature")
@Feature("Web Tables UI Tests")
public class WebTablesTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(WebTablesTest.class);

    @Story("Find row by column text")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void testFindRowByColumnText() {
        WebTablesPage page = new WebTablesPage(driver);
        page.navigateTo();

        String searchText = "Cierra";
        int colIndex = 0;

        int rowIndex = page.findRowIndexByColumnText(colIndex, searchText);
        logger.info("Row index found for '{}': {}", searchText, rowIndex);
        Assert.assertTrue(rowIndex >= 0, "Row with text '" + searchText + "' should be found.");
    }

    @Story("Verify row count and content")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void testRowCountAndCellContent() {
        WebTablesPage page = new WebTablesPage(driver);
        page.navigateTo();

        int rowCount = page.getRowCount();
        logger.info("Row count: {}", rowCount);
        Assert.assertTrue(rowCount > 0, "There should be at least one row.");

        // Check cell text in first row, 2nd column (index 1)
        String cellText = page.getCellText(0, 1);
        logger.info("Text at row 0, col 1: {}", cellText);
        Assert.assertNotNull(cellText, "Cell text should not be null");
        Assert.assertFalse(cellText.isEmpty(), "Cell text should not be empty");
    }

    @Story("Verify table headers count")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void testTableHeadersCount() {
        WebTablesPage page = new WebTablesPage(driver);
        page.navigateTo();

        int headerCount = page.getTableHeaders().size();
        logger.info("Header count: {}", headerCount);
        Assert.assertTrue(headerCount > 0, "There should be at least one table header.");
    }
}
