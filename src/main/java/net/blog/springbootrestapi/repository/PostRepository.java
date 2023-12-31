package net.blog.springbootrestapi.repository;

import net.blog.springbootrestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//by doing we create a post repository for the post entity * also jpa reository contains all crud operations and pagination sorting etc

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByCategoryId(Long categoryId);


}
