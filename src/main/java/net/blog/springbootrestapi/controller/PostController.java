package net.blog.springbootrestapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.blog.springbootrestapi.payload.PostDto;
import net.blog.springbootrestapi.payload.PostResponse;
import net.blog.springbootrestapi.service.PostService;
import net.blog.springbootrestapi.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
//used give description for the apis
@Tag(
        name = "CRUD REST APIs For Post Resource"
)

public class PostController {
    private PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }
    //create blog post

    // used to specify information in swagger
    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 created"

    )
    //define security auth for swagger specified api
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    //only admin user can access folowing method
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    //get all blog
    @Operation(
            summary = "Get All Post REST API",
            description = "Get All Post REST API used to fetch all post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 success"

    )
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
    @Operation(
            summary = "Get Post By Id REST API",
            description = "Get Post By Id REST API used to get single post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 success"

    )
    @GetMapping("/{id}")
    public  ResponseEntity<PostDto> getById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }
    //update by id
    @Operation(
            summary = "Update Post REST API",
            description = "Update Post REST API used to update a particular post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 success"

    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updateById(@Valid@RequestBody PostDto postDto,@PathVariable(name = "id") long id){
        return new ResponseEntity<>(postService.updateById(id,postDto),HttpStatus.OK);
    }
// delete by id
@Operation(
        summary = "Delete Post By Id REST API",
        description = "Delete Post By Id REST API used to delete a particular post from the database"
)
@ApiResponse(
        responseCode = "200",
        description = "Http Status 200 success"

)
@SecurityRequirement(
        name = "Bear Authentication"
)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        postService.deleteById(id);
        return new ResponseEntity<>("Post entity deleted sucessfully",HttpStatus.OK);
    }
    //get post by category
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("id") Long categoryId){
        List<PostDto> posts = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(posts);

    }
}
