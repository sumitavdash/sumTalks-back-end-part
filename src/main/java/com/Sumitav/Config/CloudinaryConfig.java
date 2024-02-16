package com.Sumitav.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {

	    @Bean
	    public Cloudinary cloudinary() {
	        return new Cloudinary("cloudinary://736721492914346:FAHQF7_-yxAFn0semnTwFCR_9As@djtbatqkp");
	    }

}
