package com.kwitterbackend.post_service.services;

import com.kwitterbackend.post_service.model.NewPostDTO;
import com.kwitterbackend.post_service.model.Post;
import com.kwitterbackend.post_service.model.UpdateUserEvent;
import com.kwitterbackend.post_service.repositories.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public String createPost(NewPostDTO post, String author){

        Post newPost = new Post(post.getTitle(), post.getContent(), author );
        postRepository.save(newPost);
        return "post created";
    }

    public Iterable<Post> getByAuthor(String author){
        return postRepository.findPostByAuthor(author);
    }

    public Iterable<Post> allPosts (){
        return postRepository.findAll();
    }

}
