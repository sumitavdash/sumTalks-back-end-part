package com.Sumitav.Services;

import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface LocalFileSystemService {

	 void saveFile(MultipartFile file, Path filePath);

	    Resource loadFile(String filename);

	    void deleteFile(Path filePath);
}
