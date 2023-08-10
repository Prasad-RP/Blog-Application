package com.bloggingapp.servicesimpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloggingapp.dto.CommentDto;
import com.bloggingapp.entity.CommentMaster;
import com.bloggingapp.entity.PostMaster;
import com.bloggingapp.exception.ResourceNotFoundException;
import com.bloggingapp.repository.CommentRepository;
import com.bloggingapp.repository.PostRepository;
import com.bloggingapp.services.CommentServices;

@Service
public class CommentServicesImpl implements CommentServices {

	@Autowired
	private CommentRepository commentRepo;

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto ctrateComment(CommentDto commentDto, Integer postId) {

		PostMaster post = null;
		CommentMaster comment = null;
		CommentMaster saveComment = null;

		try {
			post = this.postRepo.findById(postId)
					.orElseThrow(() -> new ResourceNotFoundException("Post", " id ", postId));

			comment = this.modelMapper.map(commentDto, CommentMaster.class);

			comment.setPost(post);

			saveComment = this.commentRepo.save(comment);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return this.modelMapper.map(saveComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		CommentMaster comment = null;
		try {
			comment = this.commentRepo.findById(commentId)
					.orElseThrow(() -> new ResourceNotFoundException("Comment", " id ", commentId));

		} catch (Exception e) {
			e.printStackTrace();
		}
		this.commentRepo.deleteById(commentId);

	}

}
