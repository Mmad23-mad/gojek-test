package com.test.gojek.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.test.gojek.commons.CommonFunction;
import com.test.gojek.commons.PropertyUtility;
import com.test.gojek.managers.PageObjectManager;


public class OrderSummaryPage {
	private WebDriver driver;
	PropertyUtility putility = new PropertyUtility();
	PageObjectManager pageObjManager;
	CommonFunction commonFunctionPage;
	
	public OrderSummaryPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		pageObjManager = new PageObjectManager(driver);
	}
	
	private By orderSummaryPageHeader = putility.getObject("orderSummaryPageHeader");
	private By ordSumaryPageItemName= putility.getObject("ordSumaryPageItemName");
	private By ordSumaryPageItemAmount= putility.getObject("ordSumaryPageItemAmount");
	private By ordSumaryShipingName= putility.getObject("ordSumaryShipingName");
	private By ordSumaryAddress= putility.getObject("ordSumaryAddress");
	private By ordSumaryPhnNumber= putility.getObject("ordSumaryPhnNumber");
	private By ordSumaryEmail= putility.getObject("ordSumaryEmail");
	private By shippingDetailTabBtn = putility.getObject("shippingDetailTabBtn");
	private By continuBtn = putility.getObject("continuBtn");
	
	
	
	public boolean isOrderSummaryPageDisplayed() {
		try{
			pageObjManager.getCommonFunctionPage().waitForGivenTime(5);
			pageObjManager.getCommonFunctionPage().waitForElement(By.xpath("//iframe[@id='snap-midtrans']"));
			driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='snap-midtrans']")));
			return pageObjManager.getCommonFunctionPage().isElementDisplayed(orderSummaryPageHeader);
			
		}catch (Exception e) {
			System.out.println("exception  "+e);
		}
		return false;
	}
	
	public WebElement getOrdSumaryPageItemNameElement () {
		return pageObjManager.getCommonFunctionPage().returnElement(ordSumaryPageItemName);
	}
	
	public WebElement getOrdSumaryPageItemAmountElement () {
		return pageObjManager.getCommonFunctionPage().returnElement(ordSumaryPageItemAmount);
	}
	
	public WebElement getOrdSumaryShipingNameElement () {
		return pageObjManager.getCommonFunctionPage().returnElement(ordSumaryShipingName);
	}
	
	public WebElement getOrdSumaryAddressElement () {
		return pageObjManager.getCommonFunctionPage().returnElement(ordSumaryAddress);
	}
	
	public WebElement getOrdSumaryPhnNumberElement () {
		return pageObjManager.getCommonFunctionPage().returnElement(ordSumaryPhnNumber);
	}
	
	public WebElement getOrdSumaryEmailElement () {
		return pageObjManager.getCommonFunctionPage().returnElement(ordSumaryEmail);
	}
	
	public void clickShippingDetailTabBtn() {
		pageObjManager.getCommonFunctionPage().clickElement(shippingDetailTabBtn);;
	}
	
	public void clickContinuBtn() {
		pageObjManager.getCommonFunctionPage().clickUsingJavaScript(continuBtn);
	}
	
}