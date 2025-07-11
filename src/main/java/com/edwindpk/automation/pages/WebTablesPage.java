package com.edwindpk.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebTablesPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(WebTablesPage.class);

    @FindBy(xpath = "//div[@class='rt-tr']//div[@class='rt-resizable-header-content']")
    private List<WebElement> tableHeaders;

    // Updated XPath for rows on demoqa.com
    @FindBy(xpath = "//div[@class='rt-tbody']/div[contains(@class,'rt-tr')]")
    private List<WebElement> tableRows;

    public WebTablesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void navigateTo() {
        String url = "https://demoqa.com/webtables";
        driver.get(url);
        logger.info("🔹 Navigated to Web Tables page: {}", url);
    }

    public List<WebElement> getTableHeaders() {
        logger.info("Waiting for table headers to be visible...");
        wait.until(ExpectedConditions.visibilityOfAllElements(tableHeaders));
        logger.info("Headers count found: {}", tableHeaders.size());
        return tableHeaders;
    }

    public int getRowCount() {
        logger.info("Waiting for table rows to be visible...");
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(tableRows));
        } catch (Exception e) {
            logger.error("Timeout waiting for table rows. Current found count: {}", tableRows.size());
            throw e;
        }
        int size = tableRows.size();
        logger.info("Found {} table rows", size);
        return size;
    }

    public WebElement getRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= getRowCount()) {
            logger.warn("Requested row index {} is out of bounds", rowIndex);
            return null;
        }
        return tableRows.get(rowIndex);
    }

    public String getCellText(int rowIndex, int colIndex) {
        WebElement row = getRow(rowIndex);
        if (row == null) {
            logger.warn("Row is null for index {}", rowIndex);
            return null;
        }
        List<WebElement> cells = row.findElements(By.xpath(".//div[contains(@class,'rt-td')]"));
        if (colIndex < 0 || colIndex >= cells.size()) {
            logger.warn("Column index {} is out of bounds. Total cells: {}", colIndex, cells.size());
            return null;
        }
        String text = cells.get(colIndex).getText();
        logger.info("Text at row {}, column {}: '{}'", rowIndex, colIndex, text);
        return text;
    }

    public int findRowIndexByColumnText(int colIndex, String searchText) {
        logger.info("Searching for text '{}' in column index {}", searchText, colIndex);
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(tableRows));
        } catch (Exception e) {
            logger.error("Timeout waiting for table rows for searching by column text. Rows found: {}", tableRows.size());
            throw e;
        }
        for (int i = 0; i < tableRows.size(); i++) {
            String cellText = getCellText(i, colIndex);
            logger.info("Row {}, Col {} text: '{}'", i, colIndex, cellText);
            if (cellText != null && cellText.equals(searchText)) {
                logger.info("Match found at row index {}", i);
                return i;
            }
        }
        logger.warn("No match found for '{}' in column {}", searchText, colIndex);
        return -1;
    }

    public void printAllRows() {
        logger.info("Printing all row texts:");
        int rowCount = getRowCount();
        for (int i = 0; i < rowCount; i++) {
            logger.info("Row {}: {}", i, getRow(i).getText());
        }
    }
}
