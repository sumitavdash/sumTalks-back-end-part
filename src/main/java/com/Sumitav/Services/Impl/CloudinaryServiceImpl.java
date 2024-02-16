package com.Sumitav.Services.Impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Sumitav.Services.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryServiceImpl implements CloudinaryService{

	
	
	
	 @Autowired
	    private Cloudinary cloudinary;

	@Override
	public String uploadImage(MultipartFile imageFile) {
		// TODO Auto-generated method stub
		try {
            var uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("url");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload image to Cloudinary");
        }
	}

	 @Override
	    public void deleteImage(String publicId) {
	        try {
	            // Construct options for the destroy method
	            // For example, you might want to add some options like invalidate: true
	            Map<String, Object> options = ObjectUtils.asMap("invalidate", true);

	            // Call the Cloudinary destroy method to delete the image
	            cloudinary.uploader().destroy(publicId, options);
	        } catch (IOException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Failed to delete image from Cloudinary");
	        }
	    }

}
