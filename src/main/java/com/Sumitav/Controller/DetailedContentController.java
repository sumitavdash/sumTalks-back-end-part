package com.Sumitav.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.Sumitav.Entity.Content;
import com.Sumitav.Entity.DetailedContent;
import com.Sumitav.Services.ContentService;
import com.Sumitav.Services.DetailedContentService;

@RestController
@RequestMapping("/detailedcontent")
public class DetailedContentController {

	
	 @Autowired
	    private  DetailedContentService service;

	    @Autowired
	    private ContentService contentService;

	    //add  detailed content
	    @PostMapping("/")
	    public ResponseEntity<?> addDetailedContent(@ModelAttribute DetailedContent detailedContent,
	                                                                      @RequestParam("dc_imageFile") MultipartFile dc_imageFile,
	                                                                      @RequestParam("dc_audioFile") MultipartFile dc_audioFile) {
	        try {
	            // Call the service method to add detailed content with an image
	            DetailedContent savedDetailedContent =  service.addDetailedContent(detailedContent);

	            return new ResponseEntity<>(savedDetailedContent, HttpStatus.CREATED);
	        }catch(MaxUploadSizeExceededException e) {
				 // Handle file size exceeded exception
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						 .body("File size exceeds the allowed limit. Please upload a smaller file.");
			 }catch(Exception e) {
	            e.printStackTrace();
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	    //update  detailed content
	    @PutMapping("/")
	    public ResponseEntity<DetailedContent> update(
	    		             @RequestBody  DetailedContent detailedContent,
	    		             @RequestPart(value = "updatedImageFile", required = false) MultipartFile updatedImageFile,
	    		             @RequestPart(value = "updatedAudioFile", required = false) MultipartFile updatedAudioFile){
	        return  ResponseEntity.ok(this.service.updateDetailedContent(detailedContent,updatedImageFile,updatedAudioFile));
	    }

	    //get all  detailed content of any  content
	    @GetMapping("/content/{conId}")
	    public ResponseEntity<?> getDetailedContentOfContent(@PathVariable("conId") Long  conId){

 
	         Content  content = this. contentService.getContent(conId);
	        Set<DetailedContent>  detailedContents = content.getDetailedContent();
	        List<DetailedContent> list= new ArrayList(detailedContents);
	         
	        return ResponseEntity.ok(list);
	    }

	    @GetMapping("/content/all/{conId}")
	    public ResponseEntity<?> getDetailedContentOfContentAdmin(@PathVariable("conId") Long  conId){

	        Content  content=new Content();
	        content.setConId(conId);
	        Set<DetailedContent>  detailedContentOfContent = this.service.getDetailedContentOfContent(content);
	        return ResponseEntity.ok( detailedContentOfContent);


//	        return ResponseEntity.ok(list);
	    }

	    //get  single detailed content 
	    @GetMapping("/{detailedConId}")
	    public  DetailedContent get(@PathVariable("detailedConId") Long  detailedConId){
	        return this.service.getDetailedContent(detailedConId);
	    }
	    //delete  detailedContent
	    @DeleteMapping("/{detailedConId}")
	    public void delete(@PathVariable("detailedConId") Long  detailedConId){
	        this.service.deleteDetailedContent(detailedConId);
	    }


 

}
