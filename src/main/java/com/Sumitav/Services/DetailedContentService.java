package com.Sumitav.Services;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.Sumitav.Entity.Content;
import com.Sumitav.Entity.DetailedContent;

public interface DetailedContentService {
	
	
	public DetailedContent  addDetailedContent(DetailedContent  detailedContent );
	
    public  DetailedContent  updateDetailedContent(DetailedContent  detailedContent, MultipartFile updatedImageFile,MultipartFile updatedAudioFile);
    
    public Set<DetailedContent > getDetailedContents();
    
    public  DetailedContent  getDetailedContent(Long  detailedContentId);
    
    public Set<DetailedContent > getDetailedContentOfContent(Content content);
    
    public void deleteDetailedContent(Long detailedConId);
    
    public  DetailedContent get(Long  detailedContentsId);

	 

}
