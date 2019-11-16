package com.test.gojek.commons;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public class CommonFunction {
	private WebDriver driver;
	private static final Logger LOGGER = Logger.getLogger(CommonFunction.class.getName());
	
	public CommonFunction (WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * This method will click any element or button on page if it is enabled
	 * @param selector Locator used to locate UI element
	 * 
	 */
	public void clickElement(By selector) {
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElement ele = driver.findElement(selector);
			if (isElementDisplayed(ele) && ele.isEnabled()) {
				ele.click();
				LOGGER.info("Element " + selector + "  clicked");
			}
		} catch (NoSuchElementException e) {
			LOGGER.info("Element NOT present - " + selector);
			Assert.fail("Elelemnt " + selector + " could not be clicked because - " + e);
		} catch (StaleElementReferenceException e1) {
			LOGGER.info("Element  " + selector + "   NOT present");
			Assert.fail("Elelemnt " + selector + " could not be clicked because - " + e1);
		}
	}
	
	/**
	 * 
	 * @param ele Locator used to locate UI Web Element 
	 * @return Return boolean value as true if  element is found in page
	 */
	public boolean isElementDisplayed(WebElement ele) {
		try {
			return ele.isDisplayed();
		} catch (NoSuchElementException e) {
			LOGGER.info("Element NOT present - " + ele);
			Assert.fail("Elelemnt " + ele + " could not be clicked because - " + e);
		} catch (StaleElementReferenceException e1) {
			LOGGER.info("Element  " + ele + "   NOT present");
			Assert.fail("Elelemnt " + ele + " could not be clicked because - " + e1);
		}
		return false;
	}

	
	/**
	 * 
	 * @param ele Locator used to locate UI Web Element 
	 * @return Return boolean value as true if  element is found in page
	 */
	public boolean isElementDisplayed(By selector) {
		try {
			return driver.findElement(selector).isDisplayed();
		} catch (NoSuchElementException e) {
			LOGGER.info("Element NOT present - " + selector);
			Assert.fail("Elelemnt " + selector + " could not be clicked because - " + e);
		} catch (StaleElementReferenceException e1) {
			LOGGER.info("Element  " + selector + "   NOT present");
			Assert.fail("Elelemnt " + selector + " could not be clicked because - " + e1);
		}
		return false;
	}

	
	/**
	 * This method will set value in any text box located by locator method
	 * @param selector Locator used to locate UI element
	 * @param val Value that needs to be set in test field
	 */
	public void setValue(By selector, String val) {
		try {
			WebElement ele = driver.findElement(selector);
			LOGGER.info("Element  size" + ele.getSize());
			ele.clear();
			ele.sendKeys(val);
		} catch (NoSuchElementException e) {
			LOGGER.info("Element NOT present - " + selector);
			Assert.fail("Value could not be set in element - " + selector + "   because - " + e);
		} catch (StaleElementReferenceException e1) {
			LOGGER.info("Element NOT present");
			Assert.fail("Value could not be set in element - " + selector + "  because - " + e1);
		} catch (Exception e2) {
			LOGGER.info("Value could not be set because of error:  " + e2);
			Assert.fail("Value could not be set in element - " + selector + "  because - " + e2);
		}
	}
	
	
	/**
	 * 
	 * @param selector Locator used to locate UI element
	 * @return Return string text of located Web Element
	 */
	public String getText(By selector) {
		return driver.findElement(selector).getText().trim();
	}
	
	/**
	 * This method waits for 1 seconds
	 */
	public void waitForGivenTime() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
		}
	}
	
	/**
	 * This method waits for 1 * 1000 milliseconds
	 */
	public void waitForGivenTime(int waitInMillis) {
		try {
			Thread.sleep(waitInMillis * 1000);
		} catch (InterruptedException e1) {
		}
	}
	
	
	public String getAtrributeValue(By selector, String attributeName) {
		String attributValue = null;
		try {
			WebElement ele = driver.findElement(selector);
			attributValue = ele.getAttribute(attributeName);
		} catch (NoSuchElementException e) {
			LOGGER.info(selector + " - Element not found");
		} catch (Exception e) {
			LOGGER.info("Exception : " + e);
		}
		return attributValue;
	}
	
	
	public boolean checkElementPresence(By selector) {
		try {
			driver.findElement(selector);
			LOGGER.info("Element present - " + selector);
			return true;
		} catch (NoSuchElementException e) {
			LOGGER.info("Element NOT present - " + selector);
			return false;
		} catch (StaleElementReferenceException e1) {
			LOGGER.info("Element NOT present");
			return false;
		}
	}
	
	
	public void waitForElement(By selector) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.presenceOfElementLocated(selector));
		} catch (NoSuchElementException e) {
			LOGGER.info("Element NOT present - " + selector);
		} catch (StaleElementReferenceException e1) {
			LOGGER.info("Element NOT present");
		}
	}
	
	public WebElement returnElement (By selector) {
		try {
			LOGGER.info("Element present - " + selector);
			return driver.findElement(selector);
		} catch (NoSuchElementException e) {
			LOGGER.info("Element NOT present - " + selector);
		} catch (StaleElementReferenceException e1) {
			LOGGER.info("Element NOT present");
		}
		return null;
	}
	
	public void clickUsingJavaScript (By selector) {
		//Creating the JavascriptExecutor interface object by Type casting		
        JavascriptExecutor js = (JavascriptExecutor)driver;		
        WebElement button = returnElement(selector);		
        //Perform Click on LOGIN button using JavascriptExecutor		
        js.executeScript("arguments[0].click();", button);
        LOGGER.info("Successfully clicked "+selector+"  button");
	}
	
	public void setValueUsingJavaScript (By selector, String attributeName, String value) {
        JavascriptExecutor js = (JavascriptExecutor)driver;		
        js.executeScript("document.getElementById('PaRes').value='"+value+"'");
	}

	public void switchToDefaultFrame() {
		driver.switchTo().defaultContent();
	}

}
