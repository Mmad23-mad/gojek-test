package com.test.gojek.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.test.gojek.commons.ConfigFileReader;
import com.test.gojek.extentreport.ExtentTestManager;
import com.test.gojek.managers.PageObjectManager;
import com.test.gojek.managers.WebDriverManager;
import com.test.gojek.pages.CreditCardInfoPage;
import com.test.gojek.pages.HomePage;
import com.test.gojek.pages.OrderSummaryPage;
import com.test.gojek.pages.PaymentPage;
import com.test.gojek.pages.ShoppingCartPage;



public class BuyingPillowSuccessTest {
	private static final Logger LOGGER = Logger.getLogger(BuyingPillowSuccessTest.class.getName());
	private WebDriver driver;
	PageObjectManager pageObjManager;
	HomePage homePage;
	Properties prop;
	ShoppingCartPage shopCartPg;
	OrderSummaryPage ordrSummaryPg;
	PaymentPage paymentPage;
	CreditCardInfoPage cardPage;
	
	
	@Test
	@Parameters ({ "testCaseId", "browser" })
	public void purchasePillowUsingValidPaymentDetails(String testCaseId, String browser){
		printAllLogs(Status.INFO, "Executing Test Case '"+testCaseId+ "' in '"+browser+"' browser" );
		printAllLogs(Status.INFO, "Going to start Pillow purchase Transaction");
		
		prop = ConfigFileReader.getConfigReader().loadReader();
		
		pageObjManager = new PageObjectManager(driver);
		homePage = pageObjManager.getHomePage();
		shopCartPg = pageObjManager.getShopCartPg();
		ordrSummaryPg = pageObjManager.getOrdrSummaryPg();
		paymentPage = pageObjManager.getPaymentPg();
		cardPage = pageObjManager.getCreditCardPg();
		
		//Asserting Home page
		assertEquals(driver.getTitle().trim(), "Sample Store");
		printAllLogs(Status.INFO, "Sample Store Home Page verified");
		
		homePage.validateMidTransPillowElementDisplayed();
		
		homePage.clickBuyNowBtn();
		printAllLogs(Status.INFO, "Buy Now button clicked sucessfully");
		
		Assert.assertTrue(shopCartPg.isShoppingcartPageDisplayed());
		printAllLogs(Status.INFO, "In shopping cart page");
		
		//Since all field values are already pre-filled so just getting each value and setting again in same
		String midTransPillowAmt = shopCartPg.getAttributeValuesFromEachInputLocator("Midtrans Pillow");
		shopCartPg.setAttributeValueInEachInputTextField("Midtrans Pillow", midTransPillowAmt);
		printAllLogs(Status.INFO, "Pillow Amount '"+midTransPillowAmt+"' set succsessfully");
		
		String customerName = shopCartPg.getAttributeValuesFromEachInputLocator("Name");
		shopCartPg.setAttributeValueInEachInputTextField("Name", customerName);
		printAllLogs(Status.INFO, "Customer Name '"+customerName+"' entered succsessfully");
		
		String customerEmailId = shopCartPg.getAttributeValuesFromEachInputLocator("Email");
		shopCartPg.setAttributeValueInEachInputTextField("Email", customerEmailId);
		printAllLogs(Status.INFO, "Customer Email '"+customerEmailId+"' entered succsessfully");
		
		String customerPhoneNumber = shopCartPg.getAttributeValuesFromEachInputLocator("Phone no");
		shopCartPg.setAttributeValueInEachInputTextField("Phone no", customerPhoneNumber);
		printAllLogs(Status.INFO, "Customer Phone No. '"+customerPhoneNumber+"' entered succsessfully");
		
		String custmCity = shopCartPg.getAttributeValuesFromEachInputLocator("City");
		shopCartPg.setAttributeValueInEachInputTextField("City", custmCity);
		printAllLogs(Status.INFO, "Customer City '"+custmCity+"' entered succsessfully");
		
		String custmAddress = shopCartPg.getAttributeValuesFromEachTextAreaLocator("Address");
		shopCartPg.setAttributeValueInEachTextAreaField("Address", custmAddress);
		printAllLogs(Status.INFO, "Customer Address '"+custmAddress+"' entered succsessfully");
		
		String custmPostal = shopCartPg.getAttributeValuesFromEachInputLocator("Postal Code");
		shopCartPg.setAttributeValueInEachInputTextField("Postal Code", custmPostal);
		printAllLogs(Status.INFO, "Customer Postal Code '"+custmPostal+"' entered succsessfully");
		
		shopCartPg.clickCheckOutBtn();
		printAllLogs(Status.INFO, "Check Out button clicked succsessfully");
		
		Assert.assertTrue(ordrSummaryPg.isOrderSummaryPageDisplayed());
		printAllLogs(Status.INFO, "Order summary page displayed succsessfully");
		
		Assert.assertEquals(ordrSummaryPg.getOrdSumaryPageItemNameElement().getText(), "Midtrans Pillow");
		Assert.assertEquals(ordrSummaryPg.getOrdSumaryPageItemAmountElement().getText().replaceAll("[^a-zA-Z0-9]", ""), midTransPillowAmt);
		
		ordrSummaryPg.clickShippingDetailTabBtn();
		
		Assert.assertEquals(ordrSummaryPg.getOrdSumaryEmailElement().getText(), customerEmailId);
		Assert.assertEquals(ordrSummaryPg.getOrdSumaryShipingNameElement().getText(), customerName);
		Assert.assertTrue(ordrSummaryPg.getOrdSumaryPhnNumberElement().getText().contains(customerPhoneNumber.substring(1)));
		Assert.assertEquals(ordrSummaryPg.getOrdSumaryAddressElement().getText(), custmAddress + " "+custmCity+" "+custmPostal);
		
		printAllLogs(Status.INFO, "All shipping detail info validated successfully");
		
		ordrSummaryPg.clickContinuBtn();
		printAllLogs(Status.INFO, "Continue Button clicked succsessfully in Shipping Info Page");
		
		Assert.assertTrue(paymentPage.isPaymentPageDisplayed());
		printAllLogs(Status.INFO, "Payment page displayed sucessfully");
		
		paymentPage.clickCreditCardPayLinkTxt();
		
		
		Assert.assertTrue(cardPage.isCreditCardPageDisplayed());
		printAllLogs(Status.INFO, "Credit Card payment page dislayed successfully");
		
		cardPage.setCardDetails(prop.getProperty("validCardNumber"), 
				prop.getProperty("cardExpDate"), prop.getProperty("cardCvv"));
		
		printAllLogs(Status.INFO, "All card details esntered successfully");
		
		cardPage.clickPayNowBtn();
		
		cardPage.setOtp(prop.getProperty("cardOtp"));
		printAllLogs(Status.INFO, "Otp eneterd successfully");
		
		if (cardPage.chkPaymentSuccessMessage()) {
			printAllLogs(Status.INFO, "Payment is successfull");
			cardPage.clickDoneBtn();
			pageObjManager.getCommonFunctionPage().switchToDefaultFrame();
			driver.switchTo().defaultContent();
			String succesMsg1 = homePage.getSuccessTransactionMessage1();
			String succesMsg2 = homePage.getSuccessTransactionMessage2();
			Assert.assertEquals(succesMsg1, "Thank you for your purchase.");
			Assert.assertEquals(succesMsg2, "Get a nice sleep.");
			printAllLogs(Status.PASS, "Purchase is successfull and message displayed as:  "+succesMsg1+" . "+succesMsg2);
		} else {
			Assert.fail("Payment failed for the Mid-Trans pillow");
			printAllLogs(Status.FAIL, "Payment failed for the Mid-Trans pillow with valid payment details");
		}
	}

	/**
	 * Description Method to combine all logs together
	 * @param info Message type like pass/fail/info
	 * @param message Message to be displayed in report
	 */
	private void printAllLogs(Status info, String message) {
		ExtentTestManager.getTest().log(info, message);
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
