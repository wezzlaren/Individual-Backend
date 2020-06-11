package com.kwitterbackend.post_service.receiver;

import com.google.gson.Gson;
import com.kwitterbackend.post_service.model.DeleteUserEvent;
import com.kwitterbackend.post_service.model.Post;
import com.kwitterbackend.post_service.model.UpdateUserEvent;
import com.kwitterbackend.post_service.repositories.PostRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EventReceiver {

    private final PostRepository postRepository;

    public EventReceiver(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @RabbitListener(queues = "${kwitter.rabbitmq.queue}")
    public void receive(String event) {
        System.out.println("received the event!"+ event);
        Gson gson  = new Gson();
        UpdateUserEvent result = gson.fromJson(event, UpdateUserEvent.class);
        Iterable<Post> posts = postRepository.findPostByAuthor(result.getOldUsername());
        for (Post post : posts) {
            post.setAuthor(result.getNewUsername());
            postRepository.save(post);
        }
        System.out.println("Username updated, author now called " + result.getNewUsername() );
    }

    @RabbitListener(queues = "${kwitter.rabbitmq.queuedelete}")
    public void receiveDelete(String event) {
        System.out.println("this user deleted his account:"+ event);
        Gson gson  = new Gson();
        DeleteUserEvent result = gson.fromJson(event, DeleteUserEvent.class);
        Iterable<Post> posts = postRepository.findPostByAuthor(result.getUsername());
        for (Post post : posts) {
            postRepository.delete(post);
            System.out.println("All posts from deleted account" + result.getUsername() + " removed" );
        }
    }



}
