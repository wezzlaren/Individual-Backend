package com.kwitterbackend.post_service.controllers;

import com.kwitterbackend.post_service.model.NewPostDTO;
import com.kwitterbackend.post_service.services.PostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/PostController/createpost")
    public @ResponseBody
    String createPost(@RequestBody NewPostDTO postDTO) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String username = (String) auth.getPrincipal();
        return postService.createPost(postDTO, username);
    }
}
