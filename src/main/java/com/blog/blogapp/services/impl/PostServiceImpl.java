package com.blog.blogapp.services.impl;


import com.blog.blogapp.entities.Category;
import com.blog.blogapp.entities.Post;
import com.blog.blogapp.entities.User;
import com.blog.blogapp.exceptions.ResourceNotFoundException;
import com.blog.blogapp.payloads.PostDto;
import com.blog.blogapp.payloads.PostResponse;
import com.blog.blogapp.repositories.CategoryRepo;
import com.blog.blogapp.repositories.PostRepo;
import com.blog.blogapp.repositories.UserRepo;
import com.blog.blogapp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public  class PostServiceImpl  implements PostService {


    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;


    
    public PostDto createPost(PostDto postDto, Integer categoryId, Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found", "User Id", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found", "Category Id", categoryId));
        Post post = modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost = this.postRepo.save(post);
        return this.modelMapper.map(newPost, PostDto.class);


    }


    
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found", "Post Id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found", "Post Id", postId));
        this.postRepo.delete(post);

    }

    
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = null;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else if (sortDir.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        }
        Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        Page<Post> pagePost = this.postRepo.findAll(p);
        List<Post> allPosts = pagePost.getContent();
        List<PostDto> postDto = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)
        ).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDto);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());


        return postResponse;
    }

    
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found", "Post Id", postId));
        PostDto postDto = this.modelMapper.map(post, PostDto.class);
        return postDto;
    }

    
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found", "Category Id", categoryId));
        List<Post> posts = this.postRepo.findByCategory(cat);
        List<PostDto> postDto = posts.stream().map(post -> this.modelMapper.map(posts, PostDto.class)).collect(Collectors.toList());
        return postDto;
    }


    
    public List<PostDto> getAllPostByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found", "User Id", userId));
        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDto> postDto = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDto;
    }

    
    public List<PostDto> searchPosts(String keyword) {
        List<Post> post = this.postRepo.findByTitle("%"+keyword+"%");
        List<PostDto> postDto = post.stream().map(p -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDto;
    }
}
