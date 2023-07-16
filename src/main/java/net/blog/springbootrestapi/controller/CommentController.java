package net.blog.springbootrestapi.controller;

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
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,@Valid  @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);

    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId){
        return commentService.getCommentsByPostId(postId);
    }
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "commentId") long commentId,@PathVariable(value = "postId")long postId){
        return new ResponseEntity<>(commentService.getCommentById(postId,commentId),HttpStatus.OK);

    }
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "commentId")long commentId,
                                                    @PathVariable(value = "postId") long postId,
                                                    @Valid@RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.updateById(postId,commentId,commentDto),HttpStatus.OK);

    }
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "commentId")long commentId,
                                                @PathVariable(value = "postId")long postId){
        commentService.deleteById(postId,commentId);
        return new ResponseEntity<>("Comment deleted sucessfully",HttpStatus.OK);
    }
}
