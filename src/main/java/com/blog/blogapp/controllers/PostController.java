package com.blog.blogapp.controllers;

import com.blog.blogapp.config.AppConstants;

import com.blog.blogapp.payloads.ApiResponse;
import com.blog.blogapp.payloads.PostDto;
import com.blog.blogapp.payloads.PostResponse;
import com.blog.blogapp.repositories.CategoryRepo;
import com.blog.blogapp.repositories.PostRepo;
import com.blog.blogapp.repositories.UserRepo;
import com.blog.blogapp.services.FileService;
import com.blog.blogapp.services.PostService;
import com.blog.blogapp.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/")
@CrossOrigin
@AllArgsConstructor
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;



    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable Integer categoryId,
                                              @PathVariable Integer userId,
                                              @RequestParam(value = "file", required = false) MultipartFile file) {

        PostDto createPost = this.postService.createPost(postDto, categoryId, userId);
        return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
    }

//    @PostMapping("/user/{userId}/category/{categoryId}/posts")
//    public ResponseEntity<String> createPost(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("postDto") String postDtoJson,
//            @RequestParam("categoryId") Integer categoryId,
//            @RequestParam("userId") Integer userId) {
//
//        User user = this.userRepo.findById(userId)
//                .orElseThrow(() ->new ResourceNotFoundException ("User not found", "User Id", userId));
//
//        Category category = this.categoryRepo.findById(categoryId)
//                .orElseThrow(() -> new ResourceNotFoundException("Category not found", "Category Id", categoryId));
//
//        // Handle the file upload logic here
//        if (!file.isEmpty()) {
//            try {
//                // Specify the directory where you want to save the file
//                String uploadDir = "/path/to/upload/directory/";
//
//                // Get the original filename
//                String fileName = file.getOriginalFilename();
//
//                // Save the file to the specified directory
//                file.transferTo(new File(uploadDir + fileName));
//
//                PostDto.setImageName(fileName);
//            } catch (IOException e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
//            }
//            catch (Exception e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
//            }
//        }
//
//
//        Post post = new Post();
//        post.setAddedDate(new Date());
//        post.setUser(user);
//        post.setCategory(category);
//
//        Post newPost = this.postRepo.save(post);
//        return ResponseEntity.status(HttpStatus.OK).body("Post created successfully!");
//    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getAllPostByUser(@PathVariable Integer userId) {
        List<PostDto> posts = this.postService.getAllPostByUser(userId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }


    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
        List<PostDto> posts = this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir

    ) {
        PostResponse postsResponse = this.postService.getAllPost(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<PostResponse>(postsResponse, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        PostDto post = this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(post, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ApiResponse(true, "Post Deleted Successfully");

    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
                                              @PathVariable Integer postId) {
        PostDto updatePost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
    }

    //search
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPosts(
            @PathVariable("Keywords") String keyword) {
        List<PostDto> result = this.postService.searchPosts(keyword);
        return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
    }

    //post image upload
    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(

            @RequestParam("image") MultipartFile image,
            @PathVariable Integer postId
    ) throws IOException {
        PostDto postDto =this.postService.getPostById(postId);
      String filename =  this.fileService.uploadImage(path,image);
      postDto.setImageName(filename);
       PostDto updatePost = this.postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
    }

    //get post image
    @GetMapping(value = "/posts/image/{imageName}" ,produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageNames") String imageName,
            HttpServletResponse response
    ) throws IOException {
        InputStream resource = this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }

}



