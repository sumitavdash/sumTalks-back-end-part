package com.Sumitav.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.Sumitav.Entity.Category;
import com.Sumitav.Entity.Content;
 
import com.Sumitav.Services.ContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@RestController
@RequestMapping("/content")
@CrossOrigin("*")

public class ContentController {
    
	private static final Logger logger = LoggerFactory.getLogger(ContentController.class);

	 @Autowired
	    private ContentService contentService;

	 
	    //add content service
	 @PostMapping("/")
	 public ResponseEntity<?> addContent(@ModelAttribute Content content,
	                                     @RequestParam("conImageFile") MultipartFile conImageFile) {
	     try {
	         // Call the service method to add content with an image
	    	 content.setConImageFile(conImageFile);
	         contentService.addContent(content);

	         // If the method execution reaches here, it means content was added successfully
	         return new ResponseEntity<>(HttpStatus.CREATED);
	     }catch (MaxUploadSizeExceededException e) {
			 // Handle file size exceeded exception
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					 .body("File size exceeds the allowed limit. Please upload a smaller file.");
		 } catch (Exception e) {
	         e.printStackTrace();
	         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	     }
	 }
	    //update content
	 @PutMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	 public ResponseEntity<Content> updateContent(
	         @RequestBody Content content,
	         @RequestPart(value = "conImageFile", required = false) MultipartFile  conImageFile) {
	     //return ResponseEntity.ok(this.contentService.updateContent(content, updatedImageFile));
		 try {
	            logger.info("Updating content with ID: {}", content.getConId());
	            return ResponseEntity.ok(this.contentService.updateContent(content, conImageFile));
	        } catch (Exception e) {
	            logger.error("Error occurred while updating content: {}", e.getMessage());
	            e.printStackTrace();
	            throw new RuntimeException("Failed to update content: " + e.getMessage());
	        }
	    }
	 
	    //get content
	    @GetMapping("/")
	    public ResponseEntity<?> contents(){
	        return ResponseEntity.ok(this.contentService.getContents());
	    }
	    //get content by id

	    @GetMapping("/{conId}")
	    public Content content(@PathVariable("conId")Long conId){
	        return this.contentService.getContent(conId);
	    }

	    //delete content
	    @DeleteMapping("/{conId}")
	    public void delete(@PathVariable("conId")Long conId){
	        this.contentService.deleteContent(conId);
	    }

	    @GetMapping("/category/{cid}")
	    public List<Content> getContentsOfCategory(@PathVariable("cid") Long cid){
	        Category category= new Category();
	        category.setCid(cid);
	        return this.contentService.getContentsOfCategory(category);
	    }
	    //get active contents
	    @GetMapping("/active")
	    public List<Content> getActiveContents(){
	        return this.contentService.getActiveContents();
	    }

	    //get active contents of category
	    @GetMapping("/category/active/{cid}")
	    public List<Content> getActiveContents(@PathVariable("cid") Long cid){
	       Category category= new Category();
	       category.setCid(cid);
	        return this.contentService.getActiveContentsOfCategory(category);
	    }
}
