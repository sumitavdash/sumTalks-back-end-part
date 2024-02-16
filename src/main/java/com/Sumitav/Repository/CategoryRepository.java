package com.Sumitav.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Sumitav.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
