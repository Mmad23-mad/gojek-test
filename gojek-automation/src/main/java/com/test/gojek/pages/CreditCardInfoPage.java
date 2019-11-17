package com.test.gojek.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.test.gojek.commons.CommonFunction;
import com.test.gojek.commons.PropertyUtility;
import com.test.gojek.managers.PageObjectManager;


public class CreditCardInfoPage {
	private WebDriver driver;
	PropertyUtility putility = new PropertyUtility();
	PageObjectManager pageObjManager;
	CommonFunction commonFunctionPage;
	
	public CreditCardInfoPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		pageObjManager = new PageObjectManager(driver);
	}
	
	private By credirCardPgHeader = putility.getObject("credirCardPgHeader");
	private By credCardNumTxtField = putility.getObject("credCardNumTxtField");
	private By credCardExpField = putility.getObject("credCardExpField");
	private By credCardCvv = putility.getObject("credCardCvv");
	private By payNowBtn = putility.getObject("payNowBtn");
	private By sucessPageDoneBtn = putility.getObject("sucessPageDoneBtn");
	private By paymentFailureText=putility.getObject("paymentFailureText");
	private By paymentCardDeclineMsg=putility.getObject("paymentCardDeclineMsg");
	private By retryBtn=putility.getObject("retryBtn");

	public boolean isCreditCardPageDisplayed() {
		try{
			return pageObjManager.getCommonFunctionPage().isElementDisplayed(credirCardPgHeader);
		}catch (Exception e) {
			System.out.println("exception  "+e);
		}
		return false;
	}

	public void getCardAttributAndSetValue() {
		// TODO Auto-generated method stub
	}
	
	
	public void setCardDetails(String cardNumber, String date, String cvv) {
		setCreditCardNumber(cardNumber);
		setCreditCardExpDate(date);
		setCreditCardCVV(cvv);
	}
	
	private void setCreditCardNumber (String value) {
		pageObjManager.getCommonFunctionPage().setValue(credCardNumTxtField, value);
	}
	
	private void setCreditCardCVV (String value) {
		pageObjManager.getCommonFunctionPage().setValue(credCardCvv, value);
	}
	
	private void setCreditCardExpDate (String value) {
		pageObjManager.getCommonFunctionPage().setValue(credCardExpField, value);
	}
	
	public void clickPayNowBtn() {
		pageObjManager.getCommonFunctionPage().clickUsingJavaScript(payNowBtn);
		pageObjManager.getCommonFunctionPage().waitForGivenTime(5);
	}
	
	public boolean chkPaymentSuccessMessage () {
		pageObjManager.getCommonFunctionPage().waitForGivenTime(2);
		return pageObjManager.getCommonFunctionPage().isElementDisplayed(By.xpath("//div[text()='Transaction successful']"));
	}
	
	public void setOtp(String otp) {
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src,'https://api.sandbox.veritrans.co.id/v2/token/')]")));
		driver.findElement(By.xpath("//input[@id='PaRes']")).sendKeys(otp);
		driver.findElement(By.name("ok")).click();
		driver.switchTo().defaultContent();
		pageObjManager.getCommonFunctionPage().waitForElement(By.xpath("//iframe[@id='snap-midtrans']"));
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='snap-midtrans']")));
	}

	public void clickDoneBtn() {
		pageObjManager.getCommonFunctionPage().clickUsingJavaScript(sucessPageDoneBtn);
	}
	
	public boolean chkPaymentFailureMessage () {
		pageObjManager.getCommonFunctionPage().waitForGivenTime(2);
		return pageObjManager.getCommonFunctionPage().isElementDisplayed(paymentFailureText);
	}
	
	public boolean chkCardDeclineErrorMessage () {
		return pageObjManager.getCommonFunctionPage().isElementDisplayed(paymentCardDeclineMsg);
	}
	
	public String getCardDeclineErrorMessage () {
		return pageObjManager.getCommonFunctionPage().getText(paymentCardDeclineMsg);
	}

	public void clickRetryBtn() {
		pageObjManager.getCommonFunctionPage().clickUsingJavaScript(retryBtn);
	}
	
	
}