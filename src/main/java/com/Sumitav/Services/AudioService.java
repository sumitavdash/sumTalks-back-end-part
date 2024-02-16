package com.Sumitav.Services;

import org.springframework.core.io.Resource;

public interface AudioService {
	
	Resource getAudioFile(Long detailedConId);

}
