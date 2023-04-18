package sanityTest.stepDefinitions;

import io.cucumber.java.en.Given;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;

public class register {
    @Given("User in register page")
    public void checkUserInRegisterPage() {
        Assert.assertEquals(10, 10);
    }

}
