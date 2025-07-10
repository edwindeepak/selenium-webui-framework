package com.edwindpk.automation.dataproviders;

import com.edwindpk.automation.utils.ExcelUtils;
import org.testng.annotations.DataProvider;

public class ExcelDataProvider {

    @DataProvider(name = "loginData")
    public static Object[][] getLoginData() {
        String filePath = "src/test/resources/testdata/LoginData.xlsx";
        String sheetName = "Sheet1";

        ExcelUtils excel = new ExcelUtils(filePath, sheetName);

        int rows = excel.getRowCount();
        int cols = excel.getColumnCount();

        Object[][] data = new Object[rows - 1][cols]; // exclude header row

        for (int i = 1; i < rows; i++) { // start from 1 to skip header
            for (int j = 0; j < cols; j++) {
                data[i - 1][j] = excel.getCellData(i, j);
            }
        }

        return data;
    }
}
