package com.test.gojek.managers;

import org.openqa.selenium.WebDriver;

import com.test.gojek.commons.CommonFunction;
import com.test.gojek.pages.HomePage;

public class PageObjectManager {

	private WebDriver driver;
	private CommonFunction comnFunction;
	private HomePage homePage;
	
	
	public PageObjectManager(WebDriver driver) {
		this.driver = driver;
	}
	
	public HomePage getHomePage() {
		return (homePage == null) ? homePage = new HomePage(driver) : homePage;
	}
	
	public CommonFunction getCommonFunctionPage() {
		return (comnFunction == null) ? comnFunction = new CommonFunction(driver) : comnFunction;
	}
}