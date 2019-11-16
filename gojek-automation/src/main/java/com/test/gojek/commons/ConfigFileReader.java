package com.test.gojek.commons;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.test.gojek.constants.ApplicationConstants;

/**
 * 
 * @author Swati
 * This class helps to write property variable using Singleton design.
 * 
 */
public class ConfigFileReader implements ApplicationConstants{	
	private Properties properties;
	private final String propertyFilePath= System.getProperty("user.dir")+"/src/main/java/com/test/gojek/configs/Configuration.properties";
	static ConfigFileReader  configFileReader;

	private ConfigFileReader() {
	}
	
	public static ConfigFileReader getConfigReader() {
		return (configFileReader == null) ? new ConfigFileReader() : configFileReader;
	}
	
	
	public Properties loadReader() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
				return properties;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}
		return properties;
	}
}