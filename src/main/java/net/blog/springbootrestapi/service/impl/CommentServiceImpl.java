package net.blog.springbootrestapi.service.impl;

import net.blog.springbootrestapi.entity.Comment;
import net.blog.springbootrestapi.entity.Post;
import net.blog.springbootrestapi.exception.BlogApiException;
import net.blog.springbootrestapi.exception.ResourceNotFoundException;
import net.blog.springbootrestapi.payload.CommentDto;
import net.blog.springbootrestapi.payload.PostDto;
import net.blog.springbootrestapi.repository.CommentRepository;
import net.blog.springbootrestapi.repository.PostRepository;
import net.blog.springbootrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }
//create comment
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        //retrive post by using id
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","id",postId));
        //set post to comment entity
        comment.setPost(post);
        //save comment entity
        Comment newComment = commentRepository.save(comment);
        return mapToDto(newComment);
    }
    public List<CommentDto> getCommentsByPostId(long postId){
        List<Comment> comments = commentRepository.findByPostId(postId);
        //convert list of comment to comment dto
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    //comment by comment id


    @Override
    public CommentDto getCommentById(long postId,long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment doesnot belog to post");
        }
        return mapToDto(comment);

    }

    @Override
    public CommentDto updateById(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment doesnot belong to post");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updateComment = commentRepository.save(comment);
        return mapToDto(updateComment);
    }

    @Override
    public void deleteById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment doesnot belong to post");
        }
        commentRepository.deleteById(commentId);

    }

    //comment entity to  dto
    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = mapper.map(comment,CommentDto.class);
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());

        return  commentDto;
    }
    //commentdto to entity
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = mapper.map(commentDto,Comment.class);
//        Comment comment = new Comment();
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }
}
