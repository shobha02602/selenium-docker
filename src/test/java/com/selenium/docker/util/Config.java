package com.selenium.docker.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Logger log = LoggerFactory.getLogger(Config.class);
    private static final String DEFAULT_PROPERTIES = "config/default.properties";
    private static Properties properties;


    public static void initialize() {

        //load default properties
        properties=loadProperties();



        //check for any override
        for(String key: properties.stringPropertyNames()){
            //for every property name check
            if(System.getProperties().containsKey(key)) {
                properties.setProperty(key, System.getProperty(key));
            }
        }

        //print for debugging
        log.info("Test properties");
        for(String key: properties.stringPropertyNames()) {
            log.info("{}={}", key, properties.getProperty(key));
        }
    }

    public static String get(String key) {
        //for accessing the information
        return properties.getProperty(key);
    }

    private static Properties loadProperties() {
        //creating java properties object
        Properties properties = new Properties();
        try(InputStream stream = ResourceLoader.getResource(DEFAULT_PROPERTIES)) {
            properties.load(stream);

        }catch(Exception e) {
            log.error("Unable to read property file {}", DEFAULT_PROPERTIES, e);
        }
        return properties;
    }
}
