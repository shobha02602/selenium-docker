package com.selenium.docker.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.selenium.docker.tests.vendorportal.model.VendorPortalTestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    //library responsible for converting that stream(InputStream) to a java object
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T getTestData(String path, Class<T> type) {
      // this try with resource will automatically close the file for us
        try(InputStream stream = ResourceLoader.getResource(path)){
            //here we'll be using input stream , once out of thic block files will be closed

            return mapper.readValue(stream,type);
        }catch (Exception e){
            log.error("Unable to read test data file : {}", path, e);
        }
        return null;

    }
}
