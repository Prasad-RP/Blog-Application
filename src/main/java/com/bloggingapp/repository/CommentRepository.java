package com.bloggingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bloggingapp.entity.CommentMaster;

public interface CommentRepository extends JpaRepository<CommentMaster, Integer> {

}
