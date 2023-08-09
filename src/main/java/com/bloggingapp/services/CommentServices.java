package com.bloggingapp.services;

import com.bloggingapp.dto.CommentDto;

public interface CommentServices {
	CommentDto ctrateComment(CommentDto commentDto,Integer postId);
	void deleteComment(Integer commentId);

}
