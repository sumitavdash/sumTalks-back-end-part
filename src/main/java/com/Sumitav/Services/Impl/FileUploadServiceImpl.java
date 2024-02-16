package com.Sumitav.Services.Impl;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Sumitav.Config.FileNameSanitizer;
import com.Sumitav.Services.LocalFileSystemService;

@Service
public class FileUploadServiceImpl {

	@Autowired
    private LocalFileSystemService localFileSystemService;

    @Value("${file.upload.directory}")
    private String fileUploadDirectory;

    public void saveFile(MultipartFile file) {
        // Get the original file name
        String originalFileName = file.getOriginalFilename();

        // Sanitize the file name
        String sanitizedFileName = FileNameSanitizer.sanitizeFileName(originalFileName);

        // Build the file path
        Path filePath = Paths.get(fileUploadDirectory).resolve(sanitizedFileName);

        // Save the file to the specified path
        localFileSystemService.saveFile(file, filePath);
    }
}
