package utility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;
public class ExtentManager {
	private static ExtentReports instance;
	// extent report
		public static String extentReportPath = System.getProperty("user.dir") + "/Reports/";
		public static String screenshotPath = System.getProperty("user.dir") + "/Reports/Screenshots/"; 
	
	public static synchronized ExtentReports getInstance() {
		if (instance == null) {
			System.out.println(System.getProperty("user.dir"));
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("MM_dd_yyyy_h_mm_ss_a");
			String time = sdf.format(date);
			instance = new ExtentReports(extentReportPath + "AutomationReport_" + time + ".html");
		}
		
		return instance;
	}
	
	/**
	 * Add the screenshot link in extent report
	 */
	public static String addScreenCapture(String imagePath) {
		
		String img1 = " <a class=\"right\" href=\"" + imagePath + "\" onclick=\"window.open(this.href);return false;\" >Screenshot</a> ";
		return img1;
	}

	
}
