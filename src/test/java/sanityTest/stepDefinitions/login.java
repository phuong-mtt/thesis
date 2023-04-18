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

import java.util.ResourceBundle;


public class login{
    WebDriver driver;
    ResourceBundle rb; //read properties file
    String br;
//    @Before
//    public void setup() {
//        rb = ResourceBundle.getBundle("userConfig");
//        br=rb.getString("browser");
//    }
//
//    @After
//    public void tearDown(){
//        driver.quit();
//    }

    @Given("Users are in login page")
    public void users_are_in_login_page() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://cellphones.com.vn/smember/");
    }
    @When("Users enter valid userName and password")
    public void users_enter_valid_user_name_and_password() {
        String userName = rb.getString("userName");
        String password = rb.getString("passWord");
        WebElement userNameBox = driver.findElement(By.xpath("//input[@id='email']"));
        WebElement passWordBox = driver.findElement(By.xpath("//input[@id='password']"));
        userNameBox.sendKeys(userName);
        passWordBox.sendKeys(password);
    }
    @When("Users click login button")
    public void users_click_login_button() {
        WebElement loginBtn  = driver.findElement(By.xpath("//div[@class='btn-form__submit']"));
        loginBtn.click();
    }

    @Then("Users are in their homepage")
    public void users_are_in_homepage(){
        //check in homepage
        WebElement welcomeBox = driver.findElement(By.xpath("//p[@class='welcome-smember__2nd-contentDesktop']"));
        Assert.assertTrue(welcomeBox.isDisplayed());

    }


    @When("Users enter invalid userName")
    public void users_enter_invalid_user_name() {
        String userName = "abc";
        WebElement userNameBox = driver.findElement(By.xpath("//input[@id='email']"));
        userNameBox.sendKeys(userName);

    }
    @When("Users enter password")
    public void users_enter_password() {
        String password = rb.getString("passWord");
        WebElement passWordBox = driver.findElement(By.xpath("//input[@id='password']"));
        passWordBox.sendKeys(password);
    }

    @Then("Users should see error message")
    public void users_should_see_error_message() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("Users enter userName and invalid password")
    public void users_enter_user_name_and_invalid_password() {
        String userName = rb.getString("userName");
        String password = "aaaaa";
        WebElement userNameBox = driver.findElement(By.xpath("//input[@id='email']"));
        WebElement passWordBox = driver.findElement(By.xpath("//input[@id='password']"));
        userNameBox.sendKeys(userName);
        passWordBox.sendKeys(password);
    }

}
