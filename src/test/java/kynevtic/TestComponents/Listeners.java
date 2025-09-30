package kynevtic.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import kynevtic.resources.ExtentReportNG;

public class Listeners extends BaseTest implements ITestListener{
	
	ExtentTest test;
	ExtentReports extent = ExtentReportNG.getReportObject();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); // For fixing report concurrency issue
	
	@Override
	public void onTestStart(ITestResult result) {
		    test = extent.createTest(result.getMethod().getMethodName());
		    extentTest.set(test); //creates unique thread ID
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		    extentTest.get().log(Status.PASS, "Test is passed");
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		    extentTest.get().fail(result.getThrowable());
		    
		    try {
				driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    try {
		    	extentTest.get().addScreenCaptureFromPath(getScreenshot(result.getMethod().getMethodName(), driver), result.getMethod().getMethodName());
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	@Override
	public void  onFinish(ITestContext context) {
	    // not implemented
		extent.flush();
	}
}
