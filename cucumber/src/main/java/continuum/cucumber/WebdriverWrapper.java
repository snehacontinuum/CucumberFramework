package continuum.cucumber;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.Reporter;

public class WebdriverWrapper {
	WebDriver driver=null;

	
	public WebdriverWrapper(){
	   driver = DriverFactory.getDriver();
	}
	

	
	public WebDriver getWebdriver(){
		return driver;
	}
	
	public void switchDriver(WebDriver newDriver){
		this.driver=newDriver;
		
		
	}
	
//	public WebDriver openNewWebdriver(WebDriver driver2, String browserName,String url){
//		 Reporter.log("Opening new application with url"+url+" for browser "+browserName);
//		 driver2=WebDriverInitialization.createInstance((RemoteWebDriver) driver2,browserName);
//		 DriverFactory.setWebDriver2(driver2);
//	    
//		 driver2.get(url);
//		 return driver2;
//	  }
	
  public void openApplication(String url){
	  Reporter.log("Opening application with url"+url);
	  driver.get(url);    
	  waitFor(3000);
	 }
  
 // public void openOtherApplication(String url ){
//	  RemoteWebDriver driver2=null;
//	  driver2=WebDriverInitialization.createInstance(driver2,browser);
//  	 
//	    DriverFactory.setWebDriver2(driver2);
//	    driver2.get(url);
//        driver2.manage().deleteAllCookies();
//    	driver2.manage().window().maximize();
//	  driver.navigate().to(url);
	 
//    }
       
  
  
  public void closeApplication(WebDriver driver2){
	  driver2.close();
      driver2.quit();
  }
	public  WebElement getWebElement(Locator loc)
	{
		 String by=loc.getByType().toLowerCase();
		 String ele=loc.getValue();
		 String key=loc.getKey();
		WebElement webEle=null;
		try{
		if(by.equalsIgnoreCase("id"))
			webEle=driver.findElement(By.id(ele));
		else if(by.equalsIgnoreCase("class"))
			webEle=driver.findElement(By.className(ele));
		else if(by.equalsIgnoreCase("name"))
			webEle=driver.findElement(By.name(ele));
		else if(by.equalsIgnoreCase("link"))
			webEle=driver.findElement(By.linkText(ele));
		else if(by.equalsIgnoreCase("xpath"))
			webEle=driver.findElement(By.xpath(ele));
		else if(by.equalsIgnoreCase("css"))
			webEle=driver.findElement(By.cssSelector(ele));
		else if(by.equalsIgnoreCase("tag"))
			webEle=driver.findElement(By.tagName(ele));
		if(by.toString().equals(null))
			webEle=driver.findElement(By.xpath(ele));
		if(!Utilities.getMavenProperties("browser").equalsIgnoreCase("IE"))
		{
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
					webEle, "color: orange; border: 3px solid orange;");
		}
		}catch(WebDriverException e){
			System.out.println("Not able to locate element : "+key +" with locator :" +ele);
			Assert.fail("Not able to locate element : "+key +" with locator :" +ele, e);
			System.out.println("Exception "+e);

		}
		
		return webEle;
}
	
	public By getBy(Locator loc){
		By by=null;
		String locValue= null;
		locValue = loc.getValue();
		
					switch(loc.byType.toUpperCase()){
					   case "ID":
						by = By.name(locValue);
						break;
						case "NAME":
							by = By.name(locValue);
							break;
						case "LINKTEXT":
							by = By.linkText(locValue);
							break;
						case "XPATH":
							by = By.xpath(locValue);
							break;
						case "TAGNAME":
							by = By.tagName(locValue);
							break;
						case "CSS":  case "CSSSELECTOR":
							by = By.cssSelector(locValue);
							break;	
							default:
								by = By.xpath(locValue);
			
						}
			
				return by;
		}
	

//	public void elementHighlight(Locator loc ) {
//		WebElement element = null;
//		element=getWebElement(loc);
//		if(!(element==null)){
//		for (int i = 0; i < 10; i++) {
//			
//			JavascriptExecutor js = (JavascriptExecutor) driver;
//			js.executeScript(
//					"arguments[0].setAttribute('style', arguments[1]);",
//					element, "color: orange; border: 3px solid orange;");
//			js.executeScript(
//					"arguments[0].setAttribute('style', arguments[1]);",
//					element, "");
//		}
//		}else{
//			Reporter.log( "Highlight Element "+loc.getKey()+" Fail Found Exception while accessing  ");	
//		}
//	}


	
	  public void waitForPageLoad() {
		   
		    (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
		        public Boolean apply(WebDriver d) {
		            JavascriptExecutor js = (JavascriptExecutor) d;
		            String readyState = js.executeScript("return document.readyState").toString();
		          //  System.out.println("Ready State: " + readyState);
		            return (Boolean) readyState.equals("complete");
		        }
		    });
		}

	  
	   public void waitForAjax() {
		    (new WebDriverWait(driver, 5)).until(new ExpectedCondition<Boolean>() {
		        public Boolean apply(WebDriver d) {
		            JavascriptExecutor js = (JavascriptExecutor) d;
		            return (Boolean) js.executeScript("return !!window.jQuery && window.jQuery.active == 0");
		        }
		    });
		}
	public WebElement waitForElementToBeDisplayed(final Locator loc,
			int timeOutPeriod) {
		Reporter.log("Waiting for webelement"+loc.getKey());
		final WebElement element= getWebElement(loc); 
		WebDriverWait webDriverWait = new WebDriverWait(driver, timeOutPeriod);
		webDriverWait.pollingEvery(10, TimeUnit.MICROSECONDS);

		return webDriverWait.until(new ExpectedCondition<WebElement>() {

			public WebElement apply(WebDriver driver) {
				try {
					if (element.isDisplayed())
					{
						Reporter.log(loc.getKey()+ "Element is displayed");
						return element;
						
					}
						else
						return null;
				} catch (NoSuchElementException ex) {
					return null;
				} catch (StaleElementReferenceException ex) {
					return null;
				} catch (NullPointerException ex) {
					return null;
				}
			}

		});
	}

	public void waitImplicit(int timeOut) {
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.MILLISECONDS);;

	}
	
	public boolean waitForElementToInvisible(Locator loc,
			int timeOutPeriod) {
		Reporter.log("Waiting for webelement to be invisible"+loc.getKey());
		
		WebDriverWait webDriverWait = new WebDriverWait(driver, timeOutPeriod);
		webDriverWait.pollingEvery(10, TimeUnit.MICROSECONDS);

		if (driver.findElements(getBy(loc)).size()==0)
			{
				Reporter.log(loc.getKey()+ "Element is invisble now");
				return true;
			}
			else
				return false;	
		
	}


	public WebElement waitForElementToBeClickable( final Locator locate,int timeOutPeriod) {
		Reporter.log("Waiting for webelement"+locate.getKey());
		WebDriverWait webDriverWait = new WebDriverWait(driver, timeOutPeriod);
		webDriverWait.pollingEvery(10, TimeUnit.MICROSECONDS);
		return webDriverWait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				try {
					WebElement element=getWebElement(locate); 
					if (element.isEnabled() && element.isDisplayed())
						return element;
					else
						return null;
				} catch (NoSuchElementException ex) {
					return null;
				} catch (StaleElementReferenceException ex) {
					return null;
				} catch (NullPointerException ex) {
					return null;
				}
			}

		});

	}
	
	public WebElement waitForFrame( final Locator locate,int timeOutPeriod) {
		Reporter.log("Waiting for frame"+locate.getKey());
		WebDriverWait webDriverWait = new WebDriverWait(driver, timeOutPeriod);
		webDriverWait.pollingEvery(10, TimeUnit.MICROSECONDS);
		return webDriverWait.until(new ExpectedCondition<WebElement>() {
					public WebElement apply(WebDriver driver) {
						try {
							WebElement frameLocator= getWebElement(locate);
							if(frameLocator.isDisplayed())
							    return frameLocator;
							else
								return null;
						} catch (NoSuchFrameException e) {
							return null;
					} catch (NoSuchElementException ex) {
						return null;
					} catch (StaleElementReferenceException ex) {
						return null;
					} catch (NullPointerException ex) {
						return null;
					}
				}		
				});
		}



	

	

	

	public WebElement waitForElementToBeEnabled(final Locator loc, int timeOutPeriod) {
		Reporter.log("Waiting for webelement"+loc.getKey());
		WebDriverWait webDriverWait = new WebDriverWait(driver, timeOutPeriod);
		webDriverWait.pollingEvery(10, TimeUnit.MICROSECONDS);
		return webDriverWait.until(new ExpectedCondition<WebElement>() {
			 WebElement element=getWebElement(loc);
			public WebElement apply(WebDriver driver) {
				try {
					
					if (element.isEnabled())
						return element;
					else
						return null;
				} catch (NoSuchElementException ex) {
					return null;
				} catch (StaleElementReferenceException ex) {
					return null;
				} catch (NullPointerException ex) {
					return null;
				}
			}

		});

	}

	



	public WebElement waitForTextToAppearInTextField(final Locator loc, int timeOutPeriod) {
		Reporter.log("Waiting for text in locator"+loc.getKey());
		WebDriverWait webDriverWait = new WebDriverWait(driver, timeOutPeriod);
		webDriverWait.pollingEvery(10, TimeUnit.MICROSECONDS);
		return webDriverWait.until(new ExpectedCondition<WebElement>() {
            WebElement webElement=getWebElement(loc);
			public WebElement apply(WebDriver driver) {
				try {
					String text = webElement.getText();
					if (text != null && text.equals("")) {
						return webElement;
					} else
						return null;

				} catch (NoSuchElementException ex) {
					return null;
				} catch (StaleElementReferenceException ex) {
					return null;
				} catch (NullPointerException ex) {
					return null;
				}
			}

		});

		
		
	}

	public boolean waitForOptionToBePresentInList(Locator loc, String value,
			int timeOutPeriod) {
		Reporter.log("Waiting for webelement"+loc.getKey());
		By by=getBy(loc);
		WebDriverWait webDriverWait = new WebDriverWait(driver, timeOutPeriod);
		webDriverWait.pollingEvery(10, TimeUnit.MICROSECONDS);
		try {
			webDriverWait.until(ExpectedConditions.textToBePresentInElement(
					driver.findElement(by), value));
			return true;
		} catch (TimeoutException e) {
			return false;
		}

		
	}

	public void switchToAlert() {
		waitForAlert(2000);
		Reporter.log("Switch to alert");
		
		driver.switchTo().alert();
	}
	
	public void switchToFrame(Locator loc) {
		waitForFrame(loc,2000);
		Reporter.log("Switch to frame "+loc.getKey());
		driver.switchTo().frame(getWebElement(loc));
		
	}
	public void switchToFrameByIndex(int i) {
		
		Reporter.log("Switch to frameof index "+i);
		driver.switchTo().frame(i);
		
	}
	
	public String getWindowHandle()
	{
		Reporter.log("Getting window handle");
		String winHandleBefore = driver.getWindowHandle();
		return winHandleBefore;
	}
	
	public void switchToNewWindow() {
		Reporter.log("Switching to new window");
		// Switch to new window opened
		Set<String> winHandles = driver.getWindowHandles();
		driver.switchTo().window((String) winHandles.toArray()[winHandles.size() - 1]);

        waitFor(2000);
		
	}
	
	public  void switchtoParentWindow() {
		Reporter.log("Switch to main window");
		driver.switchTo().defaultContent();
		waitFor(2000);
	}
	

	public void bringElementInView(Locator loc) {
		WebElement element=getWebElement(loc);
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", element);
		waitFor(2000);
	}

	

	public void waitForAlert(int timeOutPeriod) {
		Reporter.log("Waiting to alert");
		WebDriverWait webDriverWait = new WebDriverWait(driver, timeOutPeriod);
		webDriverWait
				.ignoring(NoSuchElementException.class,
						StaleElementReferenceException.class)
				.pollingEvery(10, TimeUnit.MILLISECONDS)
				.until(ExpectedConditions.alertIsPresent());
	}


	public String getAlertMessage(int timeOutPeriod) {
		Reporter.log("Getting alert message");
		waitForAlert(timeOutPeriod);
		Alert alert = driver.switchTo().alert();
		String AlertMessage = alert.getText();
		return AlertMessage;
	}


	

	public String dismissAlert(int timeOutPeriod) {
		waitForAlert(timeOutPeriod);
		Reporter.log("Dismissing alert");
		Alert alert = driver.switchTo().alert();
		String alertMessage = alert.getText();

		alert.dismiss();
		return alertMessage;
	}

	public void acceptAlert() {
		Reporter.log("Accepting alert");
			try {
				waitForAlert(200);
				driver.switchTo().alert().accept();
			} catch (Exception e) {

			System.out.println("Not able to accept alert"+e.getMessage());
		}
	}



	public boolean isAlertPresent() {
		boolean isAlertPresent = false;
		try {
			waitForAlert(300);
			driver.switchTo().alert();
			Reporter.log("Alert is present");
			isAlertPresent = true;
		} catch (Exception e) {
			Reporter.log("Alert is not present");
		}
		return isAlertPresent;
	}

	public boolean isAlertWithSpecifiedMessagePresent(String errorMessage) {
		boolean isAlertPresent = false;
		try {
			// waitForAlert(3);
			isAlertPresent = driver.switchTo().alert().getText()
					.contains(errorMessage);
			Reporter.log("Alert is present");
		} catch (Exception e) {
			Reporter.log("Alert is not present");
		}
		return isAlertPresent;
	}

	public boolean isAlertNotPresent() {
		boolean isAlertNotPresent = false;
		try {
			driver.switchTo().alert();
			isAlertNotPresent = false;
		} catch (Exception e) {
			isAlertNotPresent = true;
		}
		Reporter.log("Alert is not present");
		return isAlertNotPresent;
	}

	public void waitFor(int waitTime) {

		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {

		}
	}



	public String getText( Locator loc){
		
		String strTextValue=null;
		try{
			WebElement element = null;
			element=getWebElement(loc);	

			strTextValue=element.getText();
			Reporter.log("Get Value from " +loc.getKey()+ "Value stored is "+strTextValue);
			
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Failed to Fetch Text Value"+loc.getKey()+ "</font></I></B>");
			Assert.fail("Found Exception while fetching value " +e.getMessage());
		}
		return strTextValue;	
	}
	
	public void verifyTextPresent(Locator loc) {
		waitFor(2000);
       try{
			
			
			WebElement element =getWebElement(loc);	
			
			String strTextValue=element.getText();
			
			
			if(!strTextValue.isEmpty()){
				Reporter.log(strTextValue+"Text is present");
				
			}
			else{
				Reporter.log(strTextValue+" Text is not present");
				
			}
			
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Not able to fetch text from " +loc.getKey()+"</font></I></B>");
			
		}
	}
	
	public void verifyTextValue(Locator loc,String expectedValue){
		waitFor(2000);
		String strTextValue=null;
		try{
			
			
			WebElement element = null;
			element=getWebElement(loc);	
			
			strTextValue=element.getText();
			
			
			if(strTextValue.equalsIgnoreCase(expectedValue)){
				Reporter.log("Verify Text value of " +loc.getKey()+" Expected value is "+expectedValue+" Pass Actual value  is "+strTextValue);
				
			}
			else{
				Reporter.log("Verify Text value of "+loc.getKey()+" Expected value is "+expectedValue+" Fail Actual value  is "+strTextValue);
				
			}
			
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Verify Text value of " +loc.getKey()+" Expected value is "+expectedValue+"</font></I></B>");
			Assert.fail("Verify Text value of " +loc.getKey()+" Expected value is "+expectedValue+" Fail Found Exception while comparing "+e.getMessage());


		}
			
	}
	
	
	
	
	public void sendKeys( String key,Locator loc){
		waitFor(2000);
	   Reporter.log("Writing "+key+ "to "+loc.getKey());
		try{
			
			getWebElement(loc).sendKeys(key);

		}catch(Exception e){
		
			Reporter.log( "Send key command "+key+" Fail Found Exception while sending keys"+e.getMessage());
			
		}
		
	}
	
	public String getDriverTitle( ){
		String strTextValue=null;
		
		try{
		
			strTextValue=driver.getTitle();
			Reporter.log( "Driver Title"+strTextValue );
			
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Get Browser Title"+strTextValue+"Fail Found Exception while storing driver title "+"</font></I></B>");
			Assert.fail( "Get Browser Title"+strTextValue+"Fail Found Exception while storing driver title "+e.getMessage());
			
		
		}
		return strTextValue;	
	}

	
	
	public void clearandSendKeys(String textTobeEntered,Locator loc){
		try{
			if(textTobeEntered == null || textTobeEntered == ""||textTobeEntered.equalsIgnoreCase("NA")){
				
				return;
			}

			Reporter.log("Writing "+textTobeEntered+ "to "+loc.getKey());		
			WebElement element = null;
			element=getWebElement(loc);
			
			element.clear();
			waitFor(2000);
			
			 element.sendKeys(textTobeEntered);
			
			
			//System.out.println("Enter Value in " +loc.getKey()+" text field "+textTobeEntered+" Pass Value "+textTobeEntered+ " entered successfully");
			Reporter.log( "Enter Value in " +loc.getKey()+" text field "+textTobeEntered+" Pass Value "+textTobeEntered+ " entered successfully");	
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Enter Value in " +loc.getKey()+" text field "+textTobeEntered+" Fail Found Exception "+e.getMessage()+"</font></I></B>");
		//	System.out.println("Enter Value in " +loc.getKey()+" text field "+textTobeEntered+" Fail Found Exception  "+e.getMessage());
		}
		
	}
	

	public String getWindowId(){
		Reporter.log("Get window id");
		String existingWindowID=null;
		
			try{
			existingWindowID = driver.getWindowHandle();
			}catch(Exception e){
				Reporter.log("<B><I><font size='4' color='Blue'>"+"Get current  window id Fail Found Exception while getting window id"+e.getMessage()+"</font></I></B>");
				
			}
		
			return existingWindowID;
			
		}

	public void switchToWindow(String winHandle){
	//	Reporter.log("Switching to window with handle "+winHandle);
		try{
			driver.switchTo().window(winHandle);
						
			Reporter.log( "Switch to window with handle "+winHandle);
			
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Switch to window Fail Found Exception while switching to new window "+e.getMessage()+"</font></I></B>");
			
		}
		driver.manage().window().maximize();
		waitFor(2000);
	}
	
	public String getAttributeValue(String strElementName, Locator loc, String attribute){
		String strTextValue=null;
		
		try{
			
			WebElement element = null;
			element=getWebElement(loc);
			
			strTextValue=element.getAttribute(attribute);
			Reporter.log( "Get "+attribute+" from " +strElementName+" "+strTextValue+" Pass Value stored is "+strTextValue);
			
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Get "+attribute+" from " +strElementName+" "+strTextValue+" Fail Found Exception while storing value "+e.getMessage()+"</font></I></B>");
			
		  
		}
		return strTextValue;	
	}
	
	public void changeCheckboxStatus(Locator loc, String statuReq){
				
	
				WebElement element = null;
				try{
				
				
				element=getWebElement(loc);
				if(statuReq.equalsIgnoreCase("uncheck") && element.isSelected()){
					element.click();	
				}
				if(statuReq.equalsIgnoreCase("check") && !element.isSelected()){
					element.click();
				}
				Reporter.log( "Change "+loc.getKey()+" checkbox status to "+statuReq);
		}catch(Exception e1){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Change "+loc.getKey()+" checkbox status"+statuReq+" Fail Found Exception "+e1.getMessage()+"</font></I></B>");
			
		}
	}
	
	public void mouseHoverAndClick(Locator loc){
		try{
		String javaScript = "var evObj = document.createEvent('MouseEvents');" +
                "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                "arguments[0].dispatchEvent(evObj);";

		  ((JavascriptExecutor)driver).executeScript(javaScript,getWebElement(loc));
		}catch(Exception e)
		{System.out.println("Error in exceuting javascript");}
		
		  waitFor(2000);
	}
	
	public void clickUsingJavaScript(Locator loc){
		try{
		WebElement element = getWebElement(loc);
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
		}catch(Exception e)
		{System.out.println("Error in exceuting javascript");}
		waitFor(2000);
		}
		
		public void clickIdusingJavaScript(String id){
			try{
		String script="document.getElementById('"+id+"').click()";
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript(script);
			}catch(Exception e)
			{System.out.println("Error in exceuting javascript");}
		waitFor(2000);
		}
		
	public boolean getCheckboxStatus(Locator loc){
		boolean status= false;
		waitFor(2000);
		WebElement element = null;
		
		try{
		
		
		element=getWebElement(loc);
		status= element.isSelected();
		Reporter.log( "Verify checkbox status "+loc.getKey()+" Pass Checkbox is " + status);
		}catch(Exception e1){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Verify checkbox status "+loc.getKey()+" Fail Found Exception " + status+"</font></I></B>");

		  }
		
		return status;
		}

	public void verifyAttributeValueContains(String strElementName, Locator loc, String expectedValue,String attribute,RemoteWebDriver driver){
		
		String strTextValue=null;
		try{
			
			WebElement element = null;
			element=waitForElementToBeDisplayed(loc,2000);
			strTextValue=element.getAttribute(attribute);
			if(strTextValue.contains(",")){
				strTextValue=strTextValue.replace(",", "");
			}
			if(expectedValue.contains(",")){
				expectedValue=expectedValue.replace(",", "");
			}
			if(strTextValue.contains(expectedValue)){
				Reporter.log( "Verify "+attribute+" of " +strElementName+" Expected : "+expectedValue+" should contain "+strTextValue+" Pass Actual value  is "+strTextValue);	
			}
			else{
				Reporter.log( "Verify "+attribute+" of " +strElementName+" Expected : "+expectedValue+" should contain "+strTextValue+" Fail Actual value  is "+strTextValue);
			}
			
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Verify "+attribute+" of " +strElementName+" Expected : "+expectedValue+" should contain "+strTextValue+" Fail Found Exception "+e.getMessage()+"</font></I></B>");
			
		}
		
	}
		
	
	public void clickElement(Locator loc){
//		
//		
			WebElement element = null;
			element=getWebElement(loc);
			if(element.isDisplayed())
			{
				element.click();
				Reporter.log("Clicking on  "+loc.getKey());
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					Reporter.log("Not able to click on "+e.getMessage());
				}
				
			}
			else
			{
				
			    Reporter.log(loc.getKey()+" Element is not displayed for clicking");
			}
	}
	
	
	public void clickWebListElement(Locator loc,String identification){
		
		try{
			
			String[] identity=identification.split(":");
			List<WebElement> lst= getWebList(loc);
			WebElement element = null;
			if(identity[0].equalsIgnoreCase("index")){
				element=lst.get(Integer.parseInt(identity[1]));
			}else if(identity[0].equalsIgnoreCase("text")){
				for(int i=0; i<lst.size();i++){
					try{
						if(lst.get(i).getText().equalsIgnoreCase(identity[1])) element=lst.get(i);
					}catch(Exception e ){
						e.printStackTrace();
					}
				}
			}
			
		if(!(element== null)) element.click();	
			Reporter.log( "Clicked on " +loc.getKey()+" Successfully");	
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Clicked on " +loc.getKey()+" Failed Found Exception "+e.getMessage()+"</font></I></B>");
			
		}
			
	}
		
	public void doubleClick(Locator loc){
		
		try{
		
			Actions doubleClickAction = new Actions(driver); 
			
			
			WebElement element = null;
			element=getWebElement(loc);
			
			doubleClickAction.moveToElement(element).doubleClick().build().perform();
			Reporter.log( "Double Click " +loc.getKey()+" Pass Clicked "+loc.getKey()+" Successfully");	
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Double Click " +loc.getKey()+" Fail Found Exception "+e.getMessage()+"</font></I></B>");
		//	executionFlag=false;
		}
			
	}
	
	public void selectByTextFromDropDown(Locator loc,String valueTobeSelected){
		if(valueTobeSelected == null || valueTobeSelected == ""||valueTobeSelected.equalsIgnoreCase("NA")){
			return;
		}
		try{
			
			
			WebElement element = null;
			element=getWebElement(loc);
			//System.out.println("Selecting "+valueTobeSelected);
			Select webCheckBox= new Select(element);
			if(valueTobeSelected.equalsIgnoreCase("EMPTY")){
				webCheckBox.selectByVisibleText("");
			}else{
				webCheckBox.selectByVisibleText(valueTobeSelected);
			}
			
			Reporter.log( "Select value from " +loc.getKey()+" "+valueTobeSelected+" sucessfully");	
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Select value from " +loc.getKey()+" "+valueTobeSelected+" Fail  Found Exception while selecting value "+e.getMessage()+"</font></I></B>");	
			
		}
			
	}
		
	public void selectValueByIndexFromDropDown(Locator loc,int index ){
		String valueSelected="";
		try{
			
			
			WebElement element = null;
			element=getWebElement(loc);
			Select webCheckBox= new Select(element);
			webCheckBox.selectByIndex(index);
			valueSelected=webCheckBox.getFirstSelectedOption().getText();
			Reporter.log( "Select value from " +loc.getKey()+" "+valueSelected+" Selected  Successfully");	
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Select value from " +loc.getKey()+" "+valueSelected+" Fail  Found Exception while selecting value "+e.getMessage()+"</font></I></B>");	
			
		}
			
	}
			
	public void selectByValueFromDropDown(Locator loc,String valueTobeSelected){
		if(valueTobeSelected == null || valueTobeSelected == ""||valueTobeSelected.equalsIgnoreCase("NA")){
			return;
		}
		try{
			
			
			WebElement element = null;
			element=getWebElement(loc);
			//System.out.println("Selecting by value "+valueTobeSelected);
			Select webCheckBox= new Select(element);
			if(valueTobeSelected.equalsIgnoreCase("EMPTY")){
				webCheckBox.selectByValue("");
			}else{
				webCheckBox.selectByValue(valueTobeSelected);
			}
			
			Reporter.log( "Select value from " +loc.getKey()+" "+valueTobeSelected+" Selected  Successfully");	
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Select value from " +loc.getKey()+" "+valueTobeSelected+" Fail  Found Exception while selecting value "+e.getMessage()+"</font></I></B>");	
			
		}
			
	}
	
	
	public void verifySelectedValue( Locator loc,String expectedValue){
		
		try{
			WebElement element = null;
			element=waitForElementToBeDisplayed(loc,2000);
			Select webSelectList= new Select(element);
			String item = webSelectList.getFirstSelectedOption().getText();
				if (item.trim().equals(expectedValue.trim()))
				{
				Reporter.log( "Verify Selected List value from " +loc.getKey()+" "+ expectedValue+" Pass Selected value is"+expectedValue);
				}
				else
				{
				Reporter.log( "Verify Selected List value from " +loc.getKey()+" "+expectedValue+" Fail Selected value "+item+" does not match "+expectedValue);
				}
		}catch(Exception e){
		Reporter.log("<B><I><font size='4' color='Blue'>"+"Verify Selected List value from  " +loc.getKey()+" "+expectedValue+"Fail Found Exception while verifying selected element "+e.getMessage()+"</font></I></B>");
		 
		}
	}
	
	public String getSelectedValue(Locator loc){
	String item =null;
	
		try{
			WebElement element = null;
			element=getWebElement(loc);
			Select webSelectList= new Select(element);
			item = webSelectList.getFirstSelectedOption().getText();
			Reporter.log( "Get Selected List value from " +loc.getKey()+" Selected value is "+item);
		}catch(Exception e){
		Reporter.log("<B><I><font size='4' color='Blue'>"+"Get Selected List value from " +loc.getKey()+"  Done Found Exception while verifying selected element "+e.getMessage()+"</font></I></B>");
		}
	
	return item;
	}

			
	public void selectValueFromTable(Locator loc,String valueTobeSelected){
		if(valueTobeSelected == null || valueTobeSelected == ""||valueTobeSelected.equalsIgnoreCase("NA")){
			return;
		}
		try{

			

			WebElement element = null;
			element=getWebElement(loc);
			List<WebElement> options1 = element.findElements(By.tagName("option"));
			boolean isPresent = false;
			for(WebElement option : options1){
				if(option.getText().equals(valueTobeSelected)){
					element.click();
					option.click();
					isPresent = true;
					break;
				}
			}
			if(isPresent){
				Reporter.log( "Select value from " +loc.getKey()+ " Listbox "+ valueTobeSelected+" Pass "+valueTobeSelected+" option is selected from the dropdown");
			}else{
				Reporter.log( "Select value from " +loc.getKey()+ " Listbox "+ valueTobeSelected+ " Fail "+valueTobeSelected+" option is not selected from the dropdown");
			}
				
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Select value from " +loc.getKey()+" "+valueTobeSelected+" Fail Found Exception while selecting value "+e.getMessage()+"</font></I></B>");	
		
		}
			
	}
	
	
	
	
	
	
	
	
	public List<WebElement> getWebList(Locator loc ){
		Reporter.log("Getting list of webElemënt");
		List<WebElement> lst = null;
		String[] objProperties= null;
	//	objProperties = identifier.split("_", 2);
		
				try{
					By by=getBy(loc);
					lst=driver.findElements(by);
					} catch(Exception elenotFound){
						Reporter.log("<B><I><font size='4' color='Blue'>"+"Get Web List "+objProperties[1]+" Fail Found Exception while accessing element "+elenotFound.getMessage()+"</font></I></B>");
					}
				
				return lst;
			}
	
public void verifyElementPresent(Locator loc){
	waitFor(2000);
		
	Assert.assertEquals(getWebElement(loc).isDisplayed(),true,"PASS:Element "+loc.getKey()+" is present");
			    
	}

public boolean findElementPresent(Locator loc){
	
	boolean exists = false;
	//CreateResult result = new CreateResult();
	
	if(getWebdriver().findElements(getBy(loc)).size()!=0)
		    {
			 exists=true;
		    }
	Reporter.log(loc.getKey()+" exists "+exists);
	return exists;
	
}


public void verifyCurrentUrl(String url) {
   Assert.assertEquals(url,driver.getCurrentUrl(), "Navigated to correct url");
		
}
public void verifyTitle(String tittle) {
	   Assert.assertEquals(tittle,driver.getTitle(), "Navigated to correct application");
			
	}

public void mouseHover(Locator loc) {
	Reporter.log("Mouse hovering over "+loc.key);
	WebElement hoverElement=getWebElement(loc);
	Actions builder = new Actions(driver);
	builder.moveToElement(hoverElement).build().perform();
	waitFor(2000);
	
}


public boolean verifyElementNotPresent(Locator loc){
	boolean status=false;
		
		try{
		
		
	
		By by1=getBy(loc);
		
		 if(driver.findElements(by1).size()==0){
			 Reporter.log( "Expected: Element "+loc.getKey()+"is not present");	 
			 status= true;
		 }
		 else{
			 Reporter.log( "Fail: Element "+loc.getKey()+"is present");	 
			
		 }
		
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Expected: Element "+loc.getKey()+"is not present"+"</font></I></B>");	 
	
		}
		return status;
	}

    public String getAttribute( Locator loc,String attribute){
    	Reporter.log("Getting "+attribute+" of "+loc.getKey());
	    return getWebElement(loc).getAttribute(attribute);
    }

   public void verifyAttributeofElement(Locator loc,String attribute, String expectedAttribute){
	   String eleAttribute=getAttribute(loc,attribute);
	   Assert.assertEquals(eleAttribute, expectedAttribute, "Expected attribute "+expectedAttribute+" is present");
		   
   }
   
   public void verifyPageTitleContains(String partialTitle) {
		String actualTitle = driver.getTitle();
		Assert.assertTrue(actualTitle.contains(partialTitle),
				"Expected text in page title '" + partialTitle
						+ "', but actual title was '" + actualTitle + "'");
	}

	public void verifyElementCount(Locator locator, int expectedCount) {
		int actualCount = driver.findElements(getBy(locator)).size();
		Assert.assertEquals(actualCount, expectedCount,
				"Expected locator count '" + expectedCount + "', but was '"
						+ actualCount + "'");
	}
	
	
	public void executeScript(String javascriptToExecute, Locator locator) {
		try {
			
			WebElement webElement = driver.findElement(getBy(locator));

			
			((JavascriptExecutor) this.driver).executeScript(
					javascriptToExecute, new Object[] { webElement });
		} catch (WebDriverException webDrivExc) {
			Reporter.log(
					"Exception on Javascript Execution occurred while performing executeScript");
		     }
	}

	public void refreshPage() {
		Reporter.log("Refreshing page");
		driver.navigate().refresh();
	}
	
	public void refreshFrame() {
		Reporter.log("Refreshing frame");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.location.reload(true);");
	}
	public WebElement getParentElement() {
		Reporter.log("Getting parent element");
		return driver.findElement(By.xpath(".."));
	}


	public boolean isVerticalScrollBarPresent() {
		Reporter.log("Verify vertical scroll bar is present");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return (Boolean) js.executeScript("return document.documentElement.scrollHeight > document.documentElement.clientHeight;");
	}

	public boolean isHorizontalScrollBarPresent() {
		Reporter.log("Verify horizontal scroll bar is present");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return (Boolean) js.executeScript("return document.documentElement.scrollWidth > document.documentElement.clientWidth;");
	}

    public void executeWindowsCommand(String cmd){
    	 int processComplete = 0;
         Process runtimeProcess;
         String command="cmd /c" + " "+cmd;
         try {                           
                 runtimeProcess = Runtime.getRuntime().exec(command);
                 processComplete = runtimeProcess.waitFor();
                 
         } catch (IOException | InterruptedException e) {
             Reporter.log("Error to execute command on windows: "+ e);  
             }                
        if (processComplete == 0 || processComplete == 1056){                    
        	Reporter.log("Command executed on Windows");
              }else{
            	  Reporter.log("It was not possible to execute command on Windows" );  
                }           
    }
    
    public String executeWindowsCommandGetResult(String command){        
        String line;  
        String result = "";
        String executeCmd = "cmd /c" + " "+command;             
        int processComplete = 0;
        Process runtimeProcess;
        try {                           
                runtimeProcess = Runtime.getRuntime().exec(executeCmd);
                TimeUnit.SECONDS.sleep(60);
                processComplete = runtimeProcess.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(runtimeProcess.getInputStream()));
                line = reader.readLine();
                while (line != null) {
                    if (result.compareTo("") == 0)
                        result = line;
                    else
                        result = result +"\n"+line;
                    line = reader.readLine();                                        
                }
        } catch (IOException | InterruptedException e) {
            Reporter.log("Error to execute command on windows: "+ e);  
        }                
        if (processComplete == 0 || processComplete != 1056 || processComplete != 3240){                    
          Reporter.log("It was not possible to execute command on Windows or exit code is unexpected:" + processComplete);  
        }                                    
        return result;
    }
}
