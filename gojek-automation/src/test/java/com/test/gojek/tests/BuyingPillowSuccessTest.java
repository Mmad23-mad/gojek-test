package com.test.gojek.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.test.gojek.commons.ConfigFileReader;
import com.test.gojek.managers.PageObjectManager;
import com.test.gojek.managers.WebDriverManager;
import com.test.gojek.pages.HomePage;



public class BuyingPillowSuccessTest {
	private static final Logger LOGGER = Logger.getLogger(BuyingPillowSuccessTest.class.getName());
	private WebDriver driver;
	PageObjectManager pageObjManager;
	HomePage homePage;
	Properties prop;
	
	
	@Test
	@Parameters ({ "testCaseId", "browser" })
	public void purchasePillowUsingValidPaymentDetails(String testCaseId, String browser){
		printAllLogs(Status.INFO, "Executing Test Case '"+testCaseId+ "' in '"+browser+"' browser" );
		printAllLogs(Status.INFO, "Going to start Pillow purchase Transaction");
		
		prop = ConfigFileReader.getConfigReader().loadReader();
		
		pageObjManager = new PageObjectManager(driver);
		homePage = pageObjManager.getHomePage();
		
		
		//Asserting Home page
		assertEquals(driver.getTitle().trim(), "Sample Store");
		printAllLogs(Status.INFO, "Sample Store Home Page verified");
		
		homePage.validateMidTransPillowElementDisplayed();
		
		homePage.clickBuyNowBtn();
		printAllLogs(Status.INFO, "Buy Now button clicked sucessfully");
		
		
	}

	/**
	 * Description Method to combine all logs together
	 * @param info Message type like pass/fail/info
	 * @param message Message to be displayed in report
	 */
	private void printAllLogs(Status info, String message) {
		Reporter.log(message);
		LOGGER.info(message);
	}

	@BeforeMethod
	@Parameters ({ "browser" })
	public void beforeTest(String browser) {
		LOGGER.info("In before Test");
		// Creating Driver instance based on browser Type
		try {
			driver = new WebDriverManager().getDriver(browser);
			Reporter.log("Driver created successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@AfterMethod
	public void afterTest() throws IOException{
		try{
			driver.quit();
			driver=null;
			printAllLogs(Status.INFO, "Test Case Completed and calling quit driver");
		} catch (Exception e){
			printAllLogs(Status.INFO, "Test Case failed -"+e);
		}
	}
}
