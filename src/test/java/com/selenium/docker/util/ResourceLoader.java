package com.selenium.docker.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

//Utility to read json files from class paths and to create the instance of testdata class
//First we check classpath, if found it is used
//if not then we check file system (project root directory)
public class ResourceLoader {
    private static final Logger log = LoggerFactory.getLogger(ResourceLoader.class);

    //method will be giving the input stream when we read file
    //path can be classpath or file system
    public static InputStream getResource(String path) throws IOException {
        log.info("Reading resource from location: {}", path);

        //in below we're trying to read from class path
        //IOUtils for converting this stream to string
        InputStream stream = ResourceLoader.class.getClassLoader().getResourceAsStream(path);
        if(Objects.nonNull(stream)) {
            return stream;
        }
        //checking on the file system/outside
        return Files.newInputStream(Path.of(path));
    }

}
