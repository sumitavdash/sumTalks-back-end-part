package com.Sumitav.Services.Impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Sumitav.Config.FileNameSanitizer;
import com.Sumitav.Services.LocalFileSystemService;
 

@Service
public class LocalFileSystemServiceImpl implements LocalFileSystemService {

	  @Value("${file.upload.directory}")
	    private String fileUploadDirectory;

	    @Override
	    public void saveFile(MultipartFile file, Path filePath) {
	        try {
	            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
	        } catch (IOException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Failed to save file to the local file system");
	        }
	    }

	    @Override
	    public Resource loadFile(String filename) {
	        try {
	        	 String sanitizedFileName = FileNameSanitizer.sanitizeFileName(filename);
	            Path file = Paths.get(fileUploadDirectory).resolve(filename);
	            Resource resource = new UrlResource(file.toUri());

	            if (resource.exists() || resource.isReadable()) {
	                return resource;
	            } else {
	                throw new RuntimeException("Could not read the file");
	            }
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Error loading the file");
	        }
	    }

	    @Override
	    public void deleteFile(Path filePath) {
	        try {
	            Files.deleteIfExists(filePath);
	        } catch (IOException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Failed to delete file from the local file system");
	        }
	    }

}
