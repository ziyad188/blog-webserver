package net.blog.springbootrestapi.service.impl;

import ch.qos.logback.classic.net.SMTPAppender;
import net.blog.springbootrestapi.entity.Category;
import net.blog.springbootrestapi.entity.Post;
import net.blog.springbootrestapi.exception.ResourceNotFoundException;
import net.blog.springbootrestapi.payload.PostDto;
import net.blog.springbootrestapi.payload.PostResponse;
import net.blog.springbootrestapi.repository.CategoryRepository;
import net.blog.springbootrestapi.repository.PostRepository;
import net.blog.springbootrestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
public class PostServiceImpl  implements PostService {
    private PostRepository postRepository;
    private ModelMapper maper;
    private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper,CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.maper = mapper;
    }

    //used to create the post
    @Override
    public PostDto createPost(PostDto postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Category","id", postDto.getCategoryId()));

        //we need to convert this dto to entity
        Post post = mapToEntity(postDto);
        post.setCategory(category);

        Post newPost = postRepository.save(post);

        //convert post entity to dto for sending back to controller
        PostDto postResponse = mapToDto(newPost);
        return postResponse;

    }

    //get all post
    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {
        //create a condition if sort is in asc or dsc
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//create pageble instance
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);
        //page object have content to get that
        List<Post> listsOfPosts = posts.getContent();

        List<PostDto> content =  listsOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        //here we are not only returning contnet we are returning other details to client for that we created another file on payload called PostResponse
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }
// get by id
    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id", id));
        return mapToDto(post);
    }
// update by id

    @Override
    public PostDto updateById(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post","id",id));
        Category category =categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Category","id", postDto.getCategoryId()));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);

    }
    //delete a blog


    @Override
    public void deleteById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);


    }

    //to implement the conversion in one method to refactor
    private PostDto mapToDto(Post post){
        PostDto postDto = maper.map(post,PostDto.class);
//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setContent(post.getContent());
//        postDto.setDescription(post.getDescription());
        return postDto;
    }
    // to implemnt the conversion of dto entity
    private Post mapToEntity(PostDto postDto){
        Post post = maper.map(postDto,Post.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

        return post;
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        return posts.stream().map((post)->mapToDto(post)).collect(Collectors.toList());
    }
}
