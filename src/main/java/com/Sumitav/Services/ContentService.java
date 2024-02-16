package com.Sumitav.Services;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.Sumitav.Entity.Category;
import com.Sumitav.Entity.Content;

public interface ContentService {
	
	
	public Content addContent(Content content);
	
    public Content updateContent(Content content,MultipartFile updatedImageFile);
    
    public Set<Content> getContents();
    
    public Content getContent(Long conId);
    
    public void deleteContent(Long conId);


     public List<Content>getContentsOfCategory(Category category);

     public List<Content> getActiveContents();
     
     public List<Content> getActiveContentsOfCategory(Category c);
     
   
      

}
