package com.Sumitav.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Sumitav.Entity.Category;
import com.Sumitav.Entity.Content;

public interface ContentRepository extends JpaRepository <Content,Long>{
	
	public List<Content> findBycategory(Category category);

    public List<Content> findByActive(Boolean b);

    public List<Content> findByCategoryAndActive(Category c, Boolean b);

}
