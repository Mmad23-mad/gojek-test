package com.test.gojek.managers;

import org.openqa.selenium.WebDriver;

import com.test.gojek.commons.CommonFunction;
import com.test.gojek.pages.CreditCardInfoPage;
import com.test.gojek.pages.HomePage;
import com.test.gojek.pages.OrderSummaryPage;
import com.test.gojek.pages.PaymentPage;
import com.test.gojek.pages.ShoppingCartPage;

public class PageObjectManager {

	private WebDriver driver;
	private CommonFunction comnFunction;
	private HomePage homePage;
	private ShoppingCartPage cartPage;
	private OrderSummaryPage summaryPage;
	private PaymentPage paymentPage;
	private CreditCardInfoPage creditCardPage;
	
	
	public PageObjectManager(WebDriver driver) {
		this.driver = driver;
	}
	
	public HomePage getHomePage() {
		return (homePage == null) ? homePage = new HomePage(driver) : homePage;
	}
	
	public CommonFunction getCommonFunctionPage() {
		return (comnFunction == null) ? comnFunction = new CommonFunction(driver) : comnFunction;
	}
	
	public ShoppingCartPage getShopCartPg() {
		return (cartPage == null) ? cartPage = new ShoppingCartPage(driver) : cartPage;
	}
	
	public OrderSummaryPage getOrdrSummaryPg() {
		return (summaryPage == null) ? summaryPage = new OrderSummaryPage(driver) : summaryPage;
	}
	
	public PaymentPage getPaymentPg() {
		return (paymentPage == null) ? paymentPage = new PaymentPage(driver) : paymentPage;
	}
	
	public CreditCardInfoPage getCreditCardPg() {
		return (creditCardPage == null) ? creditCardPage = new CreditCardInfoPage(driver) : creditCardPage;
	}
}