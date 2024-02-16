package com.Sumitav.Repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Sumitav.Entity.Content;
import com.Sumitav.Entity.DetailedContent;

 

public interface DetailedContentRepository extends JpaRepository<DetailedContent , Long> {

	
	 Set<DetailedContent> findByContent(Content content);
}
