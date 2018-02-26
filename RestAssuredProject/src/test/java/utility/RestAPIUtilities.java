package utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.relevantcodes.extentreports.LogStatus;

import cucumber.api.java.Before;
import io.restassured.RestAssured;


public class RestAPIUtilities {

	public static String apikey;
	public static String baseUri = RestAssured.baseURI;

	
	@Before
		public void initiate(){
			apikey= readProperties( System.getProperty("user.dir") + "/src/test/resources/config.properties", "apikey");
			RestAssured.baseURI = "https://maps.googleapis.com/maps/api";
		}
		
		/**
	     * 
	     * @param filepath : Path of properties file
	     * @param key : What is the key you are searching for
	     * @return
	     */
		public static String readProperties(String filepath,String key)
		{
			String value=null;
			File f=new File(filepath);
			FileInputStream fis;
			
			try
			{
				fis=new FileInputStream(f);
				Properties prop=new Properties();
				prop.load(fis);
				value=prop.getProperty(key);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			return value;
		}
		
		/**
		 * Report  for a Step,
		 * @param result : enter Either PASS/FAIL (ignore case)
		 * @param step : Need to enter Step Details
		 */
		public static void reportStep(String result, String step){
			
			if (result.equalsIgnoreCase("PASS")){
			ExtentTestManager.getTest().log(LogStatus.PASS, "report", step);
			}
			
			else if(result.equalsIgnoreCase("FAIL")){
			ExtentTestManager.getTest().log(LogStatus.FAIL, "report", step);
			}
			
			else if(result.equalsIgnoreCase("INFO")){
				ExtentTestManager.getTest().log(LogStatus.INFO, "report", step);
				}
			
			else{
			ExtentTestManager.getTest().log(LogStatus.WARNING, "report", step);
			}

		}
	
	

}
