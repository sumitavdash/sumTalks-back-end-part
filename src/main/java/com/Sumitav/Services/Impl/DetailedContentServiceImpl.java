package com.Sumitav.Services.Impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Sumitav.Entity.Content;
import com.Sumitav.Entity.DetailedContent;
import com.Sumitav.Repository.DetailedContentRepository;
import com.Sumitav.Services.CloudinaryService;
import com.Sumitav.Services.DetailedContentService;
import com.Sumitav.Services.LocalFileSystemService;

 
 
 
@Service
public class DetailedContentServiceImpl implements DetailedContentService {
	
	@Autowired
    private CloudinaryService cloudinaryService;

	@Autowired
    private DetailedContentRepository  detailedContentRepository;
	
	@Autowired
    private LocalFileSystemService localFileSystemService;
	
	@Autowired
    private Tika tika;

	
	@Value("${file.upload.directory}")
    private String fileUploadDirectory;

	@Override
    public DetailedContent addDetailedContent(DetailedContent detailedContent) {
        try {
            // Upload image to Cloudinary and get the URL
            String dc_imageUrl = cloudinaryService.uploadImage(detailedContent.getDc_imageFile());
            detailedContent.setDc_imageUrl(dc_imageUrl);

            // Save the DetailedContent object to the database
            DetailedContent savedDetailedContent = detailedContentRepository.save(detailedContent);

            // Save audio file to the local file system
//            if (savedDetailedContent.getDc_audioFile() != null && !savedDetailedContent.getDc_audioFile().isEmpty()) {
//                String audioFileName = savedDetailedContent.getDetailedConId() + "_audio.mp3";
//                Path audioFilePath = Paths.get(fileUploadDirectory, audioFileName);
//                localFileSystemService.saveFile(savedDetailedContent.getDc_audioFile(), audioFilePath);
//                savedDetailedContent.setDc_audioPath(audioFilePath.toString());
//                detailedContentRepository.save(savedDetailedContent);
//            }
//
//            return savedDetailedContent;
//        } catch (Exception e) {
//            // Handle exception
//            e.printStackTrace();
//            throw new RuntimeException("Failed to upload content");
//        }
//    }
         // Detect content type using Tika for audio file
            if (savedDetailedContent.getDc_audioFile() != null && !savedDetailedContent.getDc_audioFile().isEmpty()) {
                String audioContentType = detectContentType(savedDetailedContent.getDc_audioFile());
                if (!audioContentType.startsWith("audio/")) {
                    throw new RuntimeException("Invalid audio file format");
                }

                // Save audio file to the local file system
                String audioFileName = savedDetailedContent.getDetailedConId() + "_audio.mp3";
                Path audioFilePath = Paths.get(fileUploadDirectory, audioFileName);
                localFileSystemService.saveFile(savedDetailedContent.getDc_audioFile(), audioFilePath);
                savedDetailedContent.setDc_audioPath(audioFilePath.toString());
                detailedContentRepository.save(savedDetailedContent);
            }

            return savedDetailedContent;
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
            throw new RuntimeException("Failed to upload content");
        }
    }

	private String detectContentType(MultipartFile file) {
        try {
            return tika.detect(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to detect content type");
        }
    }

	@Override
	public DetailedContent updateDetailedContent(DetailedContent detailedContent, MultipartFile updatedImageFile, MultipartFile updatedAudioFile) {
	    // Get the existing detailed content from the database
	    Optional<DetailedContent> optionalDetailedContent = detailedContentRepository.findById(detailedContent.getDetailedConId());

	    if (optionalDetailedContent.isPresent()) {
	        DetailedContent existingDetailedContent = optionalDetailedContent.get();

	        // Update other fields
	        existingDetailedContent.setTitle(detailedContent.getTitle());
	        existingDetailedContent.setDescription(detailedContent.getDescription());
	        existingDetailedContent.setDc_link(detailedContent.getDc_link());

	        // Check if a new image file is provided
	        if (updatedImageFile != null) {
	            // Update the Cloudinary image
	            try {
	                String imageUrl = cloudinaryService.uploadImage(updatedImageFile);
	                existingDetailedContent.setDc_imageUrl(imageUrl);
	            } catch (Exception e) {
	                e.printStackTrace();
	                throw new RuntimeException("Failed to upload image to Cloudinary");
	            }
	        }

	        // Check if a new audio file is provided
//	        if (updatedAudioFile != null) {
//	            // Save audio file to the local file system
//	            try {
//	                String audioFileName = existingDetailedContent.getDetailedConId() + "_audio.mp3";
//	                Path audioFilePath = Paths.get(fileUploadDirectory).resolve(audioFileName);
//	                Files.copy(updatedAudioFile.getInputStream(), audioFilePath, StandardCopyOption.REPLACE_EXISTING);
//	                existingDetailedContent.setDc_audioPath(audioFilePath.toString());
//	            } catch (IOException e) {
//	                e.printStackTrace();
//	                throw new RuntimeException("Failed to upload audio file");
//	            }
//	        }
//
//	        // Save the updated detailed content to the database
//	        return detailedContentRepository.save(existingDetailedContent);
//	    } else {
//	        throw new RuntimeException("Detailed Content not found with ID: " + detailedContent.getDetailedConId());
//	    }
//	}
	     // Check if a new audio file is provided
            if (updatedAudioFile != null) {
                // Detect content type using Tika for audio file
                String audioContentType = detectContentType(updatedAudioFile);
                if (!audioContentType.startsWith("audio/")) {
                    throw new RuntimeException("Invalid audio file format");
                }

                // Save audio file to the local file system
                try {
                    String audioFileName = existingDetailedContent.getDetailedConId() + "_audio.mp3";
                    Path audioFilePath = Paths.get(fileUploadDirectory).resolve(audioFileName);
                    Files.copy(updatedAudioFile.getInputStream(), audioFilePath, StandardCopyOption.REPLACE_EXISTING);
                    existingDetailedContent.setDc_audioPath(audioFilePath.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Failed to upload audio file");
                }
            }

            // Save the updated detailed content to the database
            return detailedContentRepository.save(existingDetailedContent);
        } else {
            throw new RuntimeException("Detailed Content not found with ID: " + detailedContent.getDetailedConId());
        }
    }

	@Override
	public Set<DetailedContent> getDetailedContents() {
		// TODO Auto-generated method stub
		return  new HashSet<>(this. detailedContentRepository.findAll());
	}

	@Override
	public DetailedContent getDetailedContent(Long detailedContentId) {
		// TODO Auto-generated method stub
		return  this. detailedContentRepository.findById(detailedContentId ).get();
	}

	@Override
	public Set<DetailedContent> getDetailedContentOfContent(Content content) {
		// TODO Auto-generated method stub
		return this. detailedContentRepository.findByContent(content);
	}

	 
	 @Override
	    public void deleteDetailedContent(Long detailedConId) {
	        // Retrieve the content by ID
	        Optional<DetailedContent> optionalContent = detailedContentRepository.findById(detailedConId);

	        if (optionalContent.isPresent()) {
	            // Get the content from Optional
	        	DetailedContent detailedContent = optionalContent.get();

	            // Get the Cloudinary image URL from the content
	            String imageUrl = detailedContent.getDc_imageUrl();

	            // Check if the imageUrl is not null
	            if (imageUrl != null && !imageUrl.isEmpty()) {
	                // Extract the public ID from the Cloudinary URL (assuming the URL structure is predictable)
	                String publicId = extractPublicIdFromUrl(imageUrl);

	                // Delete the image from Cloudinary using the public ID
	                cloudinaryService.deleteImage(publicId);
	            }
	         // Delete the audio file from the local file system
	            String audioPath = detailedContent.getDc_audioPath();
	            if (audioPath != null && !audioPath.isEmpty()) {
	                try {
	                    Files.deleteIfExists(Paths.get(audioPath));
	                } catch (IOException e) {
	                    e.printStackTrace();
	                    throw new RuntimeException("Failed to delete audio file");
	                }
	            }

	            // Delete the content from the database
	            detailedContentRepository.deleteById(detailedConId);
	        }
	    }

	@Override
	public DetailedContent get(Long  detailedContentsId) {
		// TODO Auto-generated method stub
		return  this. detailedContentRepository.getOne(detailedContentsId);
	}
	
	 private String extractPublicIdFromUrl(String imageUrl) {
	        // Split the URL by "/"
	        String[] parts = imageUrl.split("/");

	        // The public ID is the last part before the file extension
	        String[] publicIdParts = parts[parts.length - 1].split("\\.");

	        // Return the extracted public ID
	        return publicIdParts[0];
	    }
	 

	 
}
