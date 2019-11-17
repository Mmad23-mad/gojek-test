package com.test.gojek.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.test.gojek.commons.CommonFunction;
import com.test.gojek.commons.PropertyUtility;
import com.test.gojek.managers.PageObjectManager;



public class ShoppingCartPage {
	private WebDriver driver;
	PropertyUtility putility = new PropertyUtility();
	PageObjectManager pageObjManager;
	CommonFunction commonFunctionPage;
	
	public ShoppingCartPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		pageObjManager = new PageObjectManager(driver);
	}
	
	private By shoppingCartPage = putility.getObject("shopCartHeaderTitle");
	private String shopCartLabelTxtBox = putility.getProperty("shopCartLabelTxtBox");
	private String shopCartLabelTxtAreaBox = putility.getProperty("shopCartLabelTxtAreaBox");
	private By checkOutBtn = putility.getObject("checkOutBtn");
	
	
	
	public boolean isShoppingcartPageDisplayed() {
		pageObjManager.getCommonFunctionPage().waitForElement(shoppingCartPage);
		return pageObjManager.getCommonFunctionPage().isElementDisplayed(shoppingCartPage);
	}
	
	public String getAttributeValuesFromEachInputLocator (String labelName) {
		return pageObjManager.getCommonFunctionPage().getAtrributeValue(By.xpath(shopCartLabelTxtBox.replace("Label", labelName)), "value");
	}
	
	public void setAttributeValueInEachInputTextField (String labelName, String value) {
		pageObjManager.getCommonFunctionPage().waitForGivenTime();
		pageObjManager.getCommonFunctionPage().setValue(By.xpath(shopCartLabelTxtBox.replace("Label", labelName)), value);
	}
	
	public String getAttributeValuesFromEachTextAreaLocator (String labelName) {
		return pageObjManager.getCommonFunctionPage().getText(By.xpath(shopCartLabelTxtAreaBox.replace("Label", labelName)));
	}
	
	public void setAttributeValueInEachTextAreaField (String labelName, String value) {
		pageObjManager.getCommonFunctionPage().waitForGivenTime();
		pageObjManager.getCommonFunctionPage().setValue(By.xpath(shopCartLabelTxtAreaBox.replace("Label", labelName)), value);
	}
	
	public void clickCheckOutBtn() {
		pageObjManager.getCommonFunctionPage().clickElement(checkOutBtn);
	}
	
}