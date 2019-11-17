package com.test.gojek.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.test.gojek.commons.CommonFunction;
import com.test.gojek.commons.PropertyUtility;
import com.test.gojek.managers.PageObjectManager;


public class PaymentPage {
	private WebDriver driver;
	PropertyUtility putility = new PropertyUtility();
	PageObjectManager pageObjManager;
	CommonFunction commonFunctionPage;
	
	public PaymentPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		pageObjManager = new PageObjectManager(driver);
	}
	
	private By paymentPageHeader = putility.getObject("paymentPageHeader");
	private By creditCardPayLinkTxt = putility.getObject("creditCardPayLinkTxt");
	
	public boolean isPaymentPageDisplayed() {
		try{
			pageObjManager.getCommonFunctionPage().waitForGivenTime(2);
			return pageObjManager.getCommonFunctionPage().isElementDisplayed(paymentPageHeader);
		}catch (Exception e) {
			System.out.println("exception  "+e);
		}
		return false;
	}
	
	public void clickCreditCardPayLinkTxt() {
		pageObjManager.getCommonFunctionPage().clickElement(creditCardPayLinkTxt);
	}
	
}