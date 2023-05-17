package sanityTest.stepDefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.checkerframework.checker.units.qual.C;
import org.junit.After;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;

import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class viewProduct{
    WebDriver driver;
    ResourceBundle rb; //read properties file
    String br;
    @Before
    public void setup() {
        rb = ResourceBundle.getBundle("userConfig");
        br = rb.getString("browser");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Given("Users search for product")
    public void users_search_product() {
        //homepage
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://cellphones.com.vn");
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);

        //search
        WebElement searchBox = driver.findElement(By.xpath("//input[@id='inp$earch']"));
        searchBox.sendKeys("apple");
        searchBox.sendKeys(Keys.ENTER);
    }

    @When("Users click product")
    public void users_click_product() {
        WebElement product = driver.findElement(By.xpath("//img[@alt='Tai nghe Apple EarPods Lightning (MMTN2) Chính hãng Apple Việt Nam']"));
        product.click();
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
    }

    @Then("User should see the detailed information of product")
    public void user_see_detail() {
        WebElement detail = driver.findElement(By.xpath("//div[@class='box-gallery']"));
        Assert.assertTrue(detail.isDisplayed());
    }
}


