package com.Sumitav.Entity;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class DetailedContent {

	
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long  detailedConId;

     @Lob
    private String  description;
    
    @Column(length=2000)
    private String title;

    
 
    private String dc_link;
    
    private String dc_imageUrl;  // For Cloudinary

    private String dc_audioPath;  // For local file system

    @JsonIgnore
    @Transient
    private MultipartFile dc_imageFile;
    
    @JsonIgnore
    @Transient
    private MultipartFile dc_audioFile;
	 

     

    @ManyToOne(fetch = FetchType.EAGER)
    private Content content;
    
    
    public DetailedContent() {
		 
	}


	public Long getDetailedConId() {
		return detailedConId;
	}


	public void setDetailedConId(Long detailedConId) {
		 this.detailedConId= detailedConId;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDc_imageUrl() {
		return dc_imageUrl;
	}


	public void setDc_imageUrl(String dc_imageUrl) {
		this.dc_imageUrl = dc_imageUrl;
	}


	public String getDc_audioPath() {
		return dc_audioPath;
	}


	public void setDc_audioPath(String dc_audioPath) {
		this.dc_audioPath = dc_audioPath;
	}
	
	


	public String getDc_link() {
		return dc_link;
	}


	public void setDc_link(String dc_link) {
		this.dc_link = dc_link;
	}


	public MultipartFile getDc_imageFile() {
		return dc_imageFile;
	}


	public void setDc_imageFile(MultipartFile dc_imageFile) {
		this.dc_imageFile = dc_imageFile;
	}


	public MultipartFile getDc_audioFile() {
		return dc_audioFile;
	}


	public void setDc_audioFile(MultipartFile dc_audioFile) {
		this.dc_audioFile = dc_audioFile;
	}


	public Content getContent() {
		return content;
	}


	public void setContent(Content content) {
		this.content = content;
	}

    
    
   
}
