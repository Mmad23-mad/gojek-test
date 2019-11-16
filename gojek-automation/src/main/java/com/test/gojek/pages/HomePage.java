package com.test.gojek.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.test.gojek.commons.PropertyUtility;
import com.test.gojek.managers.PageObjectManager;



public class HomePage {
	private WebDriver driver;
	PropertyUtility putility = new PropertyUtility();
	PageObjectManager pageObjManager;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		pageObjManager = new PageObjectManager(driver);
	}
	
	private By midTranPillowElement = putility.getObject("midTranPillowElement");
	private By buyNowButton = putility.getObject("buyNowButton");
	private By successMsg1 = putility.getObject("successMsg1");
	private By successMsg2 = putility.getObject("successMsg2");

	
	public void validateMidTransPillowElementDisplayed() {
		Assert.assertTrue(pageObjManager.getCommonFunctionPage().isElementDisplayed(midTranPillowElement));
	}
	
	public void clickBuyNowBtn() {
		pageObjManager.getCommonFunctionPage().clickElement(buyNowButton);
		pageObjManager.getCommonFunctionPage().waitForGivenTime();
	}
	
	public String getSuccessTransactionMessage1(){
		return pageObjManager.getCommonFunctionPage().getText(successMsg1).trim();
	}
	
	public String getSuccessTransactionMessage2(){
		return pageObjManager.getCommonFunctionPage().getText(successMsg2).trim();
	}
	
}