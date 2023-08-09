package com.bloggingapp.repository;
	
import java.util.List;
	
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.bloggingapp.entity.CategoryMaster;
import com.bloggingapp.entity.PostMaster;
import com.bloggingapp.entity.UserMaster;

@Service
public interface PostRepository extends JpaRepository<PostMaster, Integer> {
	
	List<PostMaster> findByUser(UserMaster user);
	
	
	List<PostMaster> findByCategory(CategoryMaster category);
	
	List<PostMaster> findByTitleContaining(String title);
	
}	