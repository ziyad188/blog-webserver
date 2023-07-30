package net.blog.springbootrestapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import net.blog.springbootrestapi.payload.CommentDto;
import net.blog.springbootrestapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@Tag(
        name = "CRUD REST APIs For Comment Resource"
)
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @Operation(
            summary = "Create Comment REST API",
            description = "Create Comment REST API is used to save comment into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 created"

    )
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,@Valid  @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);

    }

    @Operation(
            summary = "Get Comment By Post Id REST API",
            description = "Get Comment By Post Id REST API used to fetch all comment related to a particular post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 success"

    )
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId){
        return commentService.getCommentsByPostId(postId);
    }
    @Operation(
            summary = "Get Comment By Comment Id REST API",
            description = "Get Comment By Comment Id REST API used to fetch single comment from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 success"

    )
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "commentId") long commentId,@PathVariable(value = "postId")long postId){
        return new ResponseEntity<>(commentService.getCommentById(postId,commentId),HttpStatus.OK);

    }
    @Operation(
            summary = "Update Comment REST API",
            description = "Update Comment REST API used to update a particular comment from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 success"

    )
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "commentId")long commentId,
                                                    @PathVariable(value = "postId") long postId,
                                                    @Valid@RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.updateById(postId,commentId,commentDto),HttpStatus.OK);

    }
    @Operation(
            summary = "Delete Comment By Id REST API",
            description = "Delete Comment By Id REST API used to delete a particular comment from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 success"

    )
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "commentId")long commentId,
                                                @PathVariable(value = "postId")long postId){
        commentService.deleteById(postId,commentId);
        return new ResponseEntity<>("Comment deleted sucessfully",HttpStatus.OK);
    }
}
