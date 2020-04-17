package com.kwitterbackend.post_service.repositories;

import com.kwitterbackend.post_service.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    Post findPostById(Long id);
    Iterable<Post> findPostByAuthor(String author);
}
