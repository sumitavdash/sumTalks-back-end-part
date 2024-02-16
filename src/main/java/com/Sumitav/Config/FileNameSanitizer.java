package com.Sumitav.Config;

import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.UUID;

public class FileNameSanitizer {
   
	 private static final String ILLEGAL_CHARACTERS_REGEX = "[^a-zA-Z0-9.-]";

	    public static String sanitizeFileName(String originalFileName) {
	        // Remove illegal characters
	        String sanitizedFileName = originalFileName.replaceAll(ILLEGAL_CHARACTERS_REGEX, "_");

	        // Generate a unique name (you may use your own logic)
	        String uniqueFileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())
	                + "_" + sanitizedFileName;
	        return uniqueFileName;
	    }
}
