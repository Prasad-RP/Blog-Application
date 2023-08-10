package com.bloggingapp.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bloggingapp.dto.ApiResponse;
import com.bloggingapp.dto.CommentDto;
import com.bloggingapp.services.CommentServices;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Prasad Pansare
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/")
public class CommentApi {

	private static final Logger log = LoggerFactory.getLogger(CommentApi.class);

	@Autowired
	private CommentServices commentServices;

	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commetnt, @PathVariable Integer postId) {
		log.info("adding comment for post id:" + postId);
		CommentDto createComment = null;

		try {
			createComment = this.commentServices.ctrateComment(commetnt, postId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
		log.info("deleting comments id:" + commentId);
		try {
			this.commentServices.deleteComment(commentId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully...!", true),
				HttpStatus.OK);
	}

}
