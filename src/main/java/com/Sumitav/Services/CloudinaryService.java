package com.Sumitav.Services;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

	 String uploadImage(MultipartFile imageFile);
	 
	 void deleteImage(String publicId);
}
