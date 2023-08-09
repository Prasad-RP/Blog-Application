package com.bloggingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bloggingapp.entity.CategoryMaster;

public interface CategoryRepository extends JpaRepository<CategoryMaster, Integer>{

}
