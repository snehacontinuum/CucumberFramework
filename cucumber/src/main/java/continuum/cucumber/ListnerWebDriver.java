package continuum.cucumber;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import continuum.cucumber.testRunner.Runner;
import cucumber.api.Scenario;

	public class ListnerWebDriver implements ITestListener{
		
		public static String resultParameter[],testResultStatus,timeStamp,imagePath;
		public static String errorMessage,screenShotPath;
		static RemoteWebDriver driver=null;
		
//		 public static File report = new File(absolutePath+"\\test-report\\");
		  public static String filePath = new File("").getAbsolutePath()+"\\target\\Screenshots";
			
		   
			@Override
			public void onTestStart(ITestResult result) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onTestSuccess(ITestResult result) {
				if(result.isSuccess())
		    		{Reporter.log("*****  " + result.getName() + " test has Passed *****");}
				
			}
			@Override
			public void onTestFailure(ITestResult result) {
				if(!result.isSuccess())
		    		{
					         Reporter.log("*****  " + result.getName() + " test has Failed *****");
					         DateFormat dateFormat = new SimpleDateFormat( "dd_MMM_yyyy__hh_mm_ssaa");
							String screenShotName = Runner.getScenarioName()+ dateFormat.format(new Date())+".jpg";
		    		  			takeScreenShot(DriverFactory.getDriver(),screenShotName);
	            	
		    	    }
				
			}
			@Override
			public void onTestSkipped(ITestResult result) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onStart(ITestContext context) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onFinish(ITestContext context) {
				// TODO Auto-generated method stub
				
			}


	    		

				public static void takeScreenShot(WebDriver driver, String screenShotName) {
//	    			String jenkins = Utilities.getConfigValues("jenkins");
//	    			if(jenkins.equalsIgnoreCase("true"))
//	    			{
//	    				String filepath="\\\\145.224.219.188\\Jenkins\\jobs\\"+testClassName+"\\workspace\\Screenshots\\";
		    			try {
		    				File file = new File(filePath);
		    				if (!file.exists()) {
		    					System.out.println("File created " + file);
		    					file.mkdir();
		    				}

		    				File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		    				File targetFile = new File(filePath, screenShotName);
		    				FileUtils.copyFile(screenshotFile, targetFile);

		    				//return screenShotName;
		    				imagePath = "file:///"+ filePath+"\\"+ screenShotName;
		    				reportlog(imagePath);
		    				Reporter.log("Screenshot can be found : " + imagePath);
		    				
		    				
		    				
		    			} catch (Exception e) {
		    				System.out.println("An exception occured while taking screenshot " + e);
		    				
		    			}
	    				
	    			
	    		}
	    		public static String getTestName(String testName) {
	    			String[] reqTestClassname = testName.split("\\.");
	    			int i = reqTestClassname.length - 1;
	    			System.out.println("Required Test Name : " + reqTestClassname[i]);
	    			return reqTestClassname[i];
	    		}
	    		
	    		public static void reportlog(String imagePath) {
	    	            	       
	    	            final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";
	    	            System.setProperty(ESCAPE_PROPERTY, "false");
	    	            Reporter.log("<br> <a target =\"_blank\"href=\""+imagePath+"\">"+
		    				"<img src=\""+imagePath+"\"alt=\"screenshot Not available\"height=\"400\"width=\"400\"></a>");
	    	       
	    	        }
			
		
				
				
	    		
	}		