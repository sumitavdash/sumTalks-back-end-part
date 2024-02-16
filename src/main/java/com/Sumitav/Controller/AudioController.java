package com.Sumitav.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Sumitav.Services.AudioService;

@RestController
@RequestMapping("/audio")
@CrossOrigin("*")
public class AudioController {
	
	@Autowired
	public  AudioService audioService;
	
	 @GetMapping("/{detailedConId}")
	    public ResponseEntity<Resource> getAudioFile(@PathVariable Long detailedConId) {
	        Resource audioResource = audioService.getAudioFile(detailedConId);

	        if (audioResource != null) {
	            return ResponseEntity.ok()
	                .contentType(MediaType.APPLICATION_OCTET_STREAM)
	                .body(audioResource);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

}
