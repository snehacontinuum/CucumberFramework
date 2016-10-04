package continuum.cucumber;

import java.io.File;
import java.net.URL;
import java.util.Arrays;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;

public class WebDriverInitialization {
	
	

	
	public static RemoteWebDriver createInstance(RemoteWebDriver driver, String browserName) {
		
		//System.out.println("Browsername:"+ browserName);
	 try{
		String hubURL =  Utilities.getMavenProperties("hubUrl");
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
        switch(browserName.toUpperCase())
        {
	    	case "FIREFOX":
		   {
			capabilities = DesiredCapabilities.firefox();	
		
			capabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS,true);
			capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, org.openqa.selenium.UnexpectedAlertBehaviour.DISMISS);
			driver = new RemoteWebDriver(new URL(hubURL),capabilities);
			break;
		   }
        
	    	case "IE":
	    	{
    		capabilities = DesiredCapabilities.internetExplorer();
    		capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
    		capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS,false);
    		//capabilities.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
    		capabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
    		capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, org.openqa.selenium.UnexpectedAlertBehaviour.DISMISS);
    	    capabilities.setCapability("ignoreProtectedModeSettings", true);
    	    
    		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); 
    		
    				 File IEDriverLocation = new File("\\drivers\\IEDriverServer.exe");
    			System.setProperty("webdriver.ie.driver", IEDriverLocation.getAbsolutePath());
    		driver = new RemoteWebDriver(new URL(hubURL),capabilities);
    		break;
          }
        
	    	case "CHROME":
	    	{
	    		File chromedriver = new File("\\drivers\\chromedriver.exe");
				System.setProperty("webdriver.chrome.driver", chromedriver.getAbsolutePath());
        	
        	capabilities = DesiredCapabilities.chrome();
        //	capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        	capabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS,true);
			capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, org.openqa.selenium.UnexpectedAlertBehaviour.DISMISS);
			
			driver = new RemoteWebDriver(new URL(hubURL),capabilities);
             break;
	    	}
        }
      
			
			
		  }
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Unable to launch browser instance due to following exception : "+e.getMessage());}
		
//		((RemoteWebDriver)driver).setFileDetector(new LocalFileDetector());
	    driver.manage().window().maximize();
	  driver.manage().deleteAllCookies();
       return driver;

       }
	
}
