package com.test.gojek.commons;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.By;

import com.test.gojek.constants.ApplicationConstants;


public class PropertyUtility implements ApplicationConstants{
	Properties prop = new Properties();	
	
	public PropertyUtility()
	{
		try{
			InputStream input =  null;
			input = new FileInputStream(OBJECT_REPO_FILEPATH);
			prop.load(input);
		}
		catch (IOException e) {
		}
				
	}
	
	/**
	 * Get selector paths from the property file
	 * 
	 * @param propertyKey
	 *            key to get the path
	 * @return selector path from property file
	 */
	public String getProperty(String propertyKey){
		String propertyValue;
		propertyValue=prop.getProperty(propertyKey);
		return propertyValue;
	}
	
	/**
	 * Get element selector By object for given path with substitutes
	 * 
	 * @param name
	 *            property key
	 * @param substitutes
	 *            values to be replaced in the selector path
	 * @return {@code By} type object of the given selector
	 */
	public By getObject(String name, String... substitutes)
	{
		By ret = null;
		
		String[] keyVal = getProperty(name).split("\\~");
		String key = keyVal[0].trim();
		String value = keyVal[1].trim();
		for (int index = 0; index < substitutes.length; index++) {
			value = value.replaceAll("<" + index + ">", substitutes[index]);
		}
		
		if(key.toLowerCase().equals("class")){
			ret = By.className(value);
		}else if(key.toLowerCase().equals("css")){
			ret = By.cssSelector(value);
		}else if(key.toLowerCase().equals("id")){
			ret = By.id(value);
		}else if(key.toLowerCase().equals("link")){
			ret = By.linkText(value);
		}else if(key.toLowerCase().equals("name")){
			ret = By.name(value);
		}else if(key.toLowerCase().equals("partiallink")){
			ret = By.partialLinkText(value);
		}else if(key.toLowerCase().equals("tagName")){
			ret = By.tagName(value);
		}else if(key.toLowerCase().equals("xpath")){
			ret = By.xpath(value);
		}
		
		return ret;
	}
}