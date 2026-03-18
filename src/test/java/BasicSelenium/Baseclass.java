package BasicSelenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class Baseclass {


    WebDriver driver;
//These are basic tests cases
    // @Description("User sucessful lanched the browser")
     @BeforeMethod
    public void SetUp_Launch_Browser() {
        // ensure chromedriver binary is available
        WebDriverManager.chromedriver().setup();
        // Use ChromeOptions inline to avoid unused variable warnings
        driver = new ChromeDriver(new ChromeOptions());
        driver.get("https://practice.expandtesting.com/dynamic-table");
        String strUrl = driver.getCurrentUrl();
        System.out.println("Current Url is:" + strUrl);
        // set implicit wait correctly
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //Assert.assertEquals(driver.getCurrentUrl(), "https://practice.expandtesting.com/dynamic-table#google_vignette");

        // accept alert if present
        try {
            driver.switchTo().alert().accept();
        } catch (org.openqa.selenium.NoAlertPresentException e) {
            // no alert displayed, continue
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


}
