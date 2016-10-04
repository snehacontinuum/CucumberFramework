package continuum.cucumber;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverFactory {
	

	static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>() ;
	static ThreadLocal<WebDriver> webDriver2 = new ThreadLocal<WebDriver>() ;
	
		    public static WebDriver getDriver() {
		    //	System.out.println("df driver id:"+ webDriver);
		        return webDriver.get();
		    }
		 
		    public static void setWebDriver(WebDriver driver) {
		        webDriver.set(driver);
		    }
		    
		    public static WebDriver getDriver2() {
			    //	System.out.println("df driver id:"+ webDriver);
			        return webDriver2.get();
			    }
			 
			    public static void setWebDriver2(WebDriver driver2) {
			        webDriver2.set(driver2);;
			    }

	

}
