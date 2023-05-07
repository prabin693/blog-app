package com.blog.blogapp.services;

import com.blog.blogapp.payloads.CommentDto;

public interface CommentService  {

    CommentDto createComment(CommentDto commentDto, Integer postId);
    void deleteComment(Integer commentId);


}


