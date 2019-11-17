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
import com.test.gojek.managers.PageObjectManager;
import com.test.gojek.managers.WebDriverManager;
import com.test.gojek.pages.CreditCardInfoPage;
import com.test.gojek.pages.HomePage;
import com.test.gojek.pages.OrderSummaryPage;
import com.test.gojek.pages.PaymentPage;
import com.test.gojek.pages.ShoppingCartPage;


public class BuyingPillowFailureTest {
	private static final Logger LOGGER = Logger.getLogger(BuyingPillowFailureTest.class.getName());
	private WebDriver driver;
	PageObjectManager pageObjManager;
	HomePage homePage;
	ShoppingCartPage shopCartPg;
	OrderSummaryPage ordrSummaryPg;
	PaymentPage paymentPage;
	CreditCardInfoPage cardPage;
	Properties prop = ConfigFileReader.getConfigReader().loadReader();
	
	
	@Test
	@Parameters ({ "testCaseId", "browser" })
	public void purchasePillowUsingInvalidPaymentDetails(String testCaseId, String browser){
		printAllLogs(Status.INFO, "Executing Test Case '"+testCaseId+ "' in '"+browser+"' browser" );
		printAllLogs(Status.INFO, "Going to start Pillow purchase Transaction with invalid payment details");
		
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
		
		cardPage.setCardDetails(prop.getProperty("invalidCardNumber"), 
				prop.getProperty("cardExpDate"), prop.getProperty("cardCvv"));
		
		printAllLogs(Status.INFO, "All card details esntered successfully");
		
		cardPage.clickPayNowBtn();
		
		cardPage.setOtp(prop.getProperty("cardOtp"));
		printAllLogs(Status.INFO, "Otp eneterd successfully");
		
		if (cardPage.chkPaymentFailureMessage()) {
			Assert.assertTrue(cardPage.chkCardDeclineErrorMessage());
			printAllLogs(Status.PASS, "Payment failed message displayed successfully as '"+cardPage.getCardDeclineErrorMessage()+"'.");
			cardPage.clickRetryBtn();
		} else {
			Assert.fail("Payment did not failed for the Mid-Trans pillow with wrong Card number");
			printAllLogs(Status.FAIL, "Payment failed message did not get displayed");
		}
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
			Reporter.log(browser+" - Driver created successfully");
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
