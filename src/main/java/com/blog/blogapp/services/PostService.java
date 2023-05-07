package com.blog.blogapp.services;

import com.blog.blogapp.entities.Post;
import com.blog.blogapp.payloads.PostDto;
import com.blog.blogapp.payloads.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService  {

 //create Post
 PostDto createPost(PostDto postDto, Integer categoryId, Integer userId);

 //update Post
 PostDto  updatePost(PostDto postDto, Integer postId);


 //delete Post
 void deletePost(Integer postId);

 //get all post
PostResponse getAllPost(Integer pageNo, Integer pageSize, String sortBy, String sortDir);


//get post by id
PostDto getPostById(Integer postId);

//get all post by category
List<PostDto> getPostByCategory(Integer categoryId);


//get all post by user
List<PostDto> getAllPostByUser(Integer userId);

//search posts
List<PostDto> searchPosts(String keyword);




}

