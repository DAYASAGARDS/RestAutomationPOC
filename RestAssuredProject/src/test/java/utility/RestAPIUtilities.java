package utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;


public class RestAPIUtilities {
	
		public void initiate(){
			String apikey= readProperties( System.getProperty("user.dir") + "/src/test/resources/properties/config.properties", "apikey");
			
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
	
	

}
