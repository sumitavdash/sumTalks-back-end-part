package com.Sumitav.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
 

 @Configuration
public class LocalFileSystemConfig {

	
	@Value("${file.upload.directory}")
    private String fileUploadDirectory;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(fileUploadDirectory));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create the upload directory");
        }
    }
}
