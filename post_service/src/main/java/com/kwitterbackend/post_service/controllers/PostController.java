package com.kwitterbackend.post_service.controllers;

import com.kwitterbackend.post_service.model.NewPostDTO;
import com.kwitterbackend.post_service.model.Post;
import com.kwitterbackend.post_service.services.PostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = RestURIConstant.createPost, method = RequestMethod.POST)
    public @ResponseBody
    String createPost(@RequestBody NewPostDTO postDTO) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String username = (String) auth.getPrincipal();
        System.out.println("Post created by " + username);
        return postService.createPost(postDTO, username);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping( value = RestURIConstant.getPostByAuthor, method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Post> getPostByAuthor(@RequestParam("author") String author){
        return postService.getByAuthor(author);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = RestURIConstant.allPosts, method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Post> allPosts() {
        return postService.allPosts();
    }
}
