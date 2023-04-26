package extentReport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class extentManager {
    private static final ExtentReports extentReports = new ExtentReports();

    public synchronized static ExtentReports getExtentReports() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("./ExtentReports/ExtentReport.html");
        reporter.config().setReportName("API Testing");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Framework Name", "RestAssured for API Testing");
        extentReports.setSystemInfo("Author", "PhuongMTT");
        return extentReports;
    }
}
