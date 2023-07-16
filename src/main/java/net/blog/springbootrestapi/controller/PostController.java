package net.blog.springbootrestapi.controller;

import jakarta.validation.Valid;
import net.blog.springbootrestapi.payload.PostDto;
import net.blog.springbootrestapi.payload.PostResponse;
import net.blog.springbootrestapi.service.PostService;
import net.blog.springbootrestapi.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }
    //create blog post
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    //get all blog
    @GetMapping
    public PostResponse getAllPost(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir
    ){
        return  postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }
    //get by id
    @GetMapping("/{id}")
    public  ResponseEntity<PostDto> getById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }
    //update by id
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updateById(@Valid@RequestBody PostDto postDto,@PathVariable(name = "id") long id){
        return new ResponseEntity<>(postService.updateById(id,postDto),HttpStatus.OK);
    }
// delete by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        postService.deleteById(id);
        return new ResponseEntity<>("Post entity deleted sucessfully",HttpStatus.OK);
    }
}
