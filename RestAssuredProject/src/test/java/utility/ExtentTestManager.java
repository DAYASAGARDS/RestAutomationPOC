package utility;

import java.util.HashMap;
import java.util.Map;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentTestManager {
	public static  ExtentTest test;
	public static String configpath = System.getProperty("user.dir") + "/src/test/resources/properties"; 
	public static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();	    
    public static ExtentReports extent = ExtentManager.getInstance();

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized void endTest() {
        extent.endTest(extentTestMap.get((int) (long) (Thread.currentThread().getId())));
    }

    public static synchronized ExtentTest startTest(String testName) {
        return startTest(testName, "");
    }

    public static synchronized ExtentTest startTest(String testName, String desc) {
        test = extent.startTest(testName, desc);
        test.log(LogStatus.INFO, "Started execution of : " + testName);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }
}
