//package sanityTest.stepDefinitions;
//
//import org.openqa.selenium.WebDriver;
//import io.cucumber.java.Before;
//import io.cucumber.java.After;
//
//import java.util.ResourceBundle;
//
//public class baseStep {
//    WebDriver driver;
//    ResourceBundle rb; //read properties file
//    String br;
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
//}
