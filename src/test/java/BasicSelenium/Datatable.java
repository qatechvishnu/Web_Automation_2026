package BasicSelenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

//Simple test cases validation for Data Table
public class Datatable extends Baseclass {
    /*
    Scenarios:
    1. Load table and verify basic structure
    2. Verify "Task Manager" text is displayed on the dynamic table page
     */

    @Test(description = "Verify Task Manager text is displayed on the dynamic table page")
    public void verifyTaskManagerTextDisplayed() {
        // Read visible page text from the body and do a case-insensitive check.
        WebElement body = driver.findElement(By.tagName("body"));
        String pageText = body.getText();
        String lower = pageText == null ? "" : pageText.toLowerCase();

        // Accept both correct spelling and the user's provided spelling ("manger") to be tolerant.
        boolean found = lower.contains("task manager") || lower.contains("task manger") || lower.contains("task-manager");

        Assert.assertTrue(found, "Expected 'Task Manager' text to be present on the page, but it was not. Page text snapshot: " + (pageText == null ? "" : (pageText.length() > 300 ? pageText.substring(0, 300) + "..." : pageText)));
    }


    @Test(description = "Verify number of rows and columns are displaying")
    public void verifyRows_Columns(){
        // Find the table element
        WebElement table = driver.findElement(By.tagName("table"));
        Assert.assertNotNull(table, "Table should be present on the page");
        System.out.println("✓ Table found on the page");

        // Get all table headers to count columns
        java.util.List<WebElement> headers = table.findElements(By.tagName("th"));
        int columnCount = headers.size();
        Assert.assertFalse(headers.isEmpty(), "Table should have at least one column header");
        System.out.println("✓ Number of Columns: " + columnCount);

        // Print each column header
        System.out.println("Column Headers:");
        for (int i = 0; i < headers.size(); i++) {
            String headerText = headers.get(i).getText();
            System.out.println("  Column " + (i + 1) + ": " + headerText);
            Assert.assertFalse(headerText.isEmpty(), "Column header " + (i + 1) + " should not be empty");
        }

        // Get all table body rows to count rows
        WebElement tableBody = driver.findElement(By.tagName("tbody"));
        java.util.List<WebElement> rows = tableBody.findElements(By.tagName("tr"));
        int rowCount = rows.size();
        Assert.assertFalse(rows.isEmpty(), "Table should have at least one data row");
        System.out.println("✓ Number of Rows: " + rowCount);

        // Print row details
        System.out.println("Row Details:");
        for (int i = 0; i < rows.size(); i++) {
            WebElement row = rows.get(i);
            java.util.List<WebElement> cells = row.findElements(By.tagName("td"));

            // Verify each row has the same number of columns
            Assert.assertEquals(cells.size(), columnCount,
                "Row " + (i + 1) + " should have " + columnCount + " columns, but found " + cells.size());

            System.out.print("  Row " + (i + 1) + ": ");
            for (int j = 0; j < cells.size(); j++) {
                String cellText = cells.get(j).getText();
                System.out.print(cellText);
                if (j < cells.size() - 1) {
                    System.out.print(" | ");
                }
                Assert.assertFalse(cellText.isEmpty(), "Cell at Row " + (i + 1) + ", Column " + (j + 1) + " should not be empty");
            }
            System.out.println();
        }

        // Final assertions to confirm table structure
        System.out.println("\n✓ Final Summary:");
        System.out.println("  Total Rows: " + rowCount);
        System.out.println("  Total Columns: " + columnCount);
        Assert.assertTrue(rowCount > 0, "Table should have at least one row");
        Assert.assertTrue(columnCount > 0, "Table should have at least one column");

        // Extract and display Name column values
        System.out.println("\n✓ Name Column Values:");
         int nameColumnIndex = getColumnIndex(headers, "Name");
        if (nameColumnIndex >= 0) {
            for (int i = 0; i < rows.size(); i++) {
                WebElement row = rows.get(i);
                java.util.List<WebElement> cells = row.findElements(By.tagName("td"));
                if (nameColumnIndex < cells.size()) {
                    String nameValue = cells.get(nameColumnIndex).getText();
                    System.out.println("  Row " + (i + 1) + " Name: " + nameValue);
                    Assert.assertFalse(nameValue.isEmpty(), "Name value in row " + (i + 1) + " should not be empty");
                }
            }
        } else {
            System.out.println("  WARNING: 'Name' column not found in table headers");
        }
    }

    /**
     * Helper method to find the column index by header name
     * @param headers List of header elements
     * @param columnName Name of the column to find
     * @return Index of the column, or -1 if not found
     */
    private int getColumnIndex(java.util.List<WebElement> headers, String columnName) {
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).getText().equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        return -1;
    }
}
