package net.blog.springbootrestapi.service;

import net.blog.springbootrestapi.entity.Comment;
import net.blog.springbootrestapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);
    CommentDto getCommentById(long postId,long commentId);
    CommentDto updateById(long postId,long commentId,CommentDto commentDto);
    void deleteById(long postId,long commentId);
}
