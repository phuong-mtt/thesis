package listeners;

import com.aventstack.extentreports.Status;
import extentReport.extentTestManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import log.log;

import static extentReport.extentManager.getExtentReports;

public class reportListener implements ITestListener{
    public String getTestName(ITestResult result) {
        return result.getTestName() != null ? result.getTestName()
                : result.getMethod().getConstructorOrMethod().getName();
    }

    public String getTestDescription(ITestResult result) {
        return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        log.info("Start testing " + iTestContext.getName());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        log.info("End testing " + iTestContext.getName());
        //Kết thúc và thực thi Extents Report
        getExtentReports().flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        log.info(getTestName(iTestResult) + " test is starting...");
        extentTestManager.saveToReport(iTestResult.getName(), iTestResult.getTestName());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        log.info(getTestName(iTestResult) + " test is passed.");
        //ExtentReports log operation for passed tests.
        extentTestManager.logMessage(Status.PASS, getTestDescription(iTestResult));
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        log.error(getTestName(iTestResult) + " test is failed.");

        extentTestManager.logMessage(Status.FAIL, iTestResult.getThrowable().toString());
        extentTestManager.logMessage(Status.FAIL, iTestResult.getName() + " is failed.");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        log.warn(getTestName(iTestResult) + " test is skipped.");
        extentTestManager.logMessage(Status.SKIP, getTestName(iTestResult) + " test is skipped.");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        log.error("Test failed but it is in defined success ratio " + getTestName(iTestResult));
        extentTestManager.logMessage("Test failed but it is in defined success ratio " + getTestName(iTestResult));
    }
}
