package sanityTest.testRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/sanityTest/featureFiles/viewProduct.feature",
        glue = "sanityTest/stepDefinitions",
        plugin = {"pretty",
                "html:reports/myreport.html",
                "json:reports/myreport.json",
                "rerun:target/rerun.txt"
        },
        dryRun = false, //dryRun = true -> only check steps initialed or not instead of executing them
        monochrome = true, //return pretty format on console
        tags = "@sanity"
)

public class testRunner {
}
