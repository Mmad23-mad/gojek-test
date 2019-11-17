package com.test.gojek.managers;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.test.gojek.commons.ConfigFileReader;
import com.test.gojek.constants.ApplicationConstants;
import com.test.gojek.enums.Browser;


public class WebDriverManager implements ApplicationConstants{
	private WebDriver driver;
	private Browser browserType;
	Properties prop = ConfigFileReader.getConfigReader().loadReader();
	private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
	private static final String GECKO_DRIVER_PROPERTY = "webdriver.gecko.driver";

	public WebDriver getDriver(String browser) throws IOException {
		browserType = browser.equalsIgnoreCase("chrome") ? Browser.CHROME : Browser.FIREFOX;
		return (driver == null) ? createDriver() : driver;
	}

	private WebDriver createDriver() throws IOException {
		driver = createBrowserSpecificDriver();
		return driver;
	}

	private WebDriver createBrowserSpecificDriver() throws IOException {
        switch (browserType) {	    
        case FIREFOX : 
        	driver = getFireFoxDriver();
	    	break;
        case CHROME : 
        	driver = getChromeDriver();
    		break;
        }
        
        driver.get(APP_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(prop.getProperty("implicitlyWait")), TimeUnit.SECONDS);
		return driver;
	}

	private WebDriver getChromeDriver() {
		System.setProperty(CHROME_DRIVER_PROPERTY, prop.getProperty("chromeDriverPath"));
		return driver = new ChromeDriver(createChromeCapabalities());
	}

	private static ChromeOptions createChromeCapabalities() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		options.merge(capabilities);
		return options;
	}

	private WebDriver getFireFoxDriver() {
		System.setProperty(GECKO_DRIVER_PROPERTY, prop.getProperty("geckoDriverPath"));
		return driver = new FirefoxDriver();
	}	

	public void closeDriver() {
		driver.close();
	}
	
	public void quitDriver() {
		 if (driver == null) {
		        return;
		    }
		 driver.quit();
		 driver = null;
	}
}