package net.blog.springbootrestapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.blog.springbootrestapi.payload.PostDto;
import net.blog.springbootrestapi.payload.PostDtoV2;
import net.blog.springbootrestapi.payload.PostResponse;
import net.blog.springbootrestapi.service.PostService;
import net.blog.springbootrestapi.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping()
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
    @PostMapping("/api/v1/posts")
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
    @GetMapping("/api/v1/posts")
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
    // for versioning using  parameter add paramenter to link like param here in get anotation
    // for versioning using header we can provide a property in get annotation same as above and mention header
    //same for contnet where we use property produce in get anotation
    @GetMapping("/api/v1/posts/{id}")
    public  ResponseEntity<PostDto> getByIdV1(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }
    //version 2 get included tag
    @GetMapping("/api/v2/posts/{id}")
    public  ResponseEntity<PostDtoV2> getByIdV2(@PathVariable(name = "id") long id){
        PostDto postDto = postService.getPostById(id);
        PostDtoV2 postDtoV2 = new PostDtoV2();
        postDtoV2.setId(postDto.getId());
        postDtoV2.setContent(postDto.getContent());
        postDtoV2.setTitle(postDto.getTitle());
        postDtoV2.setDescription(postDto.getDescription());
        postDtoV2.setComments(postDto.getComments());
        postDtoV2.setCategoryId(postDto.getCategoryId());
        List<String> tags = new ArrayList<>();
        tags.add("java");
        tags.add("Spring Boot");
        tags.add("AWS");
        postDtoV2.setTags(tags);
        return ResponseEntity.ok(postDtoV2);
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
    @PutMapping("/api/v1/posts/{id}")
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
    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        postService.deleteById(id);
        return new ResponseEntity<>("Post entity deleted sucessfully",HttpStatus.OK);
    }
    //get post by category
    @GetMapping("/api/v1/posts/category/{id}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("id") Long categoryId){
        List<PostDto> posts = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(posts);

    }
}
