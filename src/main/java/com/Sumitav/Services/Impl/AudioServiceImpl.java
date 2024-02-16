package com.Sumitav.Services.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.Sumitav.Entity.DetailedContent;
import com.Sumitav.Services.AudioService;
import com.Sumitav.Services.DetailedContentService;

@Service
public class AudioServiceImpl implements AudioService{
   
	private static final Logger logger = LoggerFactory.getLogger(AudioServiceImpl.class);

	@Autowired
    private DetailedContentService detailedContentService;

	@Override
	public Resource getAudioFile(Long detailedConId) {
		// TODO Auto-generated method stub
		  
//		        DetailedContent detailedContent = detailedContentService.getDetailedContent(detailedConId);
//
//		        if (detailedContent != null && detailedContent.getDc_audioPath() != null) {
//		            Path audioFilePath = Paths.get(detailedContent.getDc_audioPath());
//
//		            try {
//		                Resource resource = new UrlResource(audioFilePath.toUri());
//
//		                if (resource.exists() && resource.isReadable()) {
//		                    return resource;
//		                }
//		            } catch (IOException e) {
//		                e.printStackTrace();
//		            }
//		        }
//
//		        return null;
//		    }
		DetailedContent detailedContent = detailedContentService.getDetailedContent(detailedConId);

        if (detailedContent != null && detailedContent.getDc_audioPath() != null) {
            Path audioFilePath = Paths.get(detailedContent.getDc_audioPath());

            try {
                Resource resource = new UrlResource(audioFilePath.toUri());

                if (resource.exists() && resource.isReadable()) {
                    return resource;
                } else {
                    logger.error("Audio resource does not exist or is not readable");
                }
            } catch (IOException e) {
                logger.error("Error while creating UrlResource", e);
            }
        } else {
            logger.error("DetailedContent or audio path is null");
        }

        return null;
	}
}


