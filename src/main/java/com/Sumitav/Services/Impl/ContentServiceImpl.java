package com.Sumitav.Services.Impl;

//import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Sumitav.Entity.Category;
import com.Sumitav.Entity.Content;
import com.Sumitav.Repository.ContentRepository;
import com.Sumitav.Services.CloudinaryService;
import com.Sumitav.Services.ContentService;
 
@Service
public class ContentServiceImpl implements ContentService{

	
	 
	@Autowired
    private CloudinaryService cloudinaryService;
	
	
	 @Autowired
	    private ContentRepository contentRepository;
	 
	 
	 @Override
	 public Content addContent(Content content) {
	     try {
	         String imageUrl = cloudinaryService.uploadImage(content.getConImageFile());
	         content.setConImage(imageUrl);
	         return contentRepository.save(content);
	     } catch (Exception e) {
	         e.printStackTrace();
	         throw new RuntimeException("Failed to upload image to Cloudinary");
	     }
	 }
	     
	    	@Override
	        public Content updateContent(Content content, MultipartFile updatedImageFile) {
	            // Get the existing content from the database
	            Optional<Content> optionalContent = contentRepository.findById(content.getConId());

	            if (optionalContent.isPresent()) {
	                Content existingContent = optionalContent.get();

	                // Update other fields
//	                existingContent.setTitle(content.getTitle());
//	                existingContent.setDescription(content.getDescription());
//	                existingContent.setLink_url(content.getLink_url());
            
	             // Update title if provided
	                if (content.getTitle() != null) {
	                    existingContent.setTitle(content.getTitle());
	                }

	                // Update description if provided
	                if (content.getDescription() != null) {
	                    existingContent.setDescription(content.getDescription());
	                }

	                // Update link_url if provided
	                if (content.getLink_url() != null) {
	                    existingContent.setLink_url(content.getLink_url());
	                }
	             // Check if a new image file is provided
	                if (updatedImageFile != null) {
	                    // Delete the existing image from Cloudinary (if URL is available)
	                    if (existingContent.getConImage() != null) {
	                        // Extract Cloudinary public ID from existing image URL
	                        String publicId = extractPublicIdFromUrl(existingContent.getConImage());
	                        if (publicId != null) {
	                            cloudinaryService.deleteImage(publicId); // Delete the existing image
	                        }
	                    }

	                 // Upload the new image to Cloudinary
	                  
	                    try {
	                        String imageUrl = cloudinaryService.uploadImage(updatedImageFile);
	                        existingContent.setConImage(imageUrl);
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                        throw new RuntimeException("Failed to upload image to Cloudinary");
	                    }
	                }

	                // Save the updated content to the database
	                return contentRepository.save(existingContent);
	            } else {
	                throw new RuntimeException("Content not found with ID: " + content.getConId());
	            }
	    	}
	    

	    @Override
	    public Set<Content> getContents() {
	        return new HashSet<>(this.contentRepository.findAll());
	    }

	    @Override
	    public Content getContent(Long conId) {
	        return  this.contentRepository.findById(conId).get();
	    }

	    @Override
	    public void deleteContent(Long conId) {
	        // Retrieve the content by ID
	        Optional<Content> optionalContent = contentRepository.findById(conId);

	        if (optionalContent.isPresent()) {
	            // Get the content from Optional
	            Content content = optionalContent.get();

	            // Get the Cloudinary image URL from the content
	            String imageUrl = content.getConImage();

	            // Check if the imageUrl is not null
	            if (imageUrl != null && !imageUrl.isEmpty()) {
	                // Extract the public ID from the Cloudinary URL (assuming the URL structure is predictable)
	                String publicId = extractPublicIdFromUrl(imageUrl);

	                // Delete the image from Cloudinary using the public ID
	                cloudinaryService.deleteImage(publicId);
	            }

	            // Delete the content from the database
	            contentRepository.deleteById(conId);
	        }
	    }


	    @Override
	    public List<Content> getContentsOfCategory(Category category){
	        return this.contentRepository.findBycategory(category);
	    }


	    //get Active Quizzes
	    @Override
	    public List<Content> getActiveContents() {
	        return this.contentRepository.findByActive(true);
	    }

	    @Override
	    public List<Content> getActiveContentsOfCategory(Category c) {
	        return  this.contentRepository.findByCategoryAndActive(c,true);
	    }
	    
	    
	    
	    
	    private String extractPublicIdFromUrl(String imageUrl) {
	        // Split the URL by "/"
	        String[] parts = imageUrl.split("/");

	        // The public ID is the last part before the file extension
	        String[] publicIdParts = parts[parts.length - 1].split("\\.");

	        // Return the extracted public ID
	        return publicIdParts[0];
	    }
   
	    
}
