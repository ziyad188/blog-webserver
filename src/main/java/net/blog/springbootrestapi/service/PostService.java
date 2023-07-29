package net.blog.springbootrestapi.service;

import net.blog.springbootrestapi.payload.PostDto;
import net.blog.springbootrestapi.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(Long id);
    PostDto updateById(Long id,PostDto postDto);
    void deleteById(Long id);
    List<PostDto> getPostsByCategory(Long categoryId);


}
