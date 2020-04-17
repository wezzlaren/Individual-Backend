package com.kwitterbackend.post_service.receiver;

import com.google.gson.Gson;
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
        System.out.println(result);

        Iterable<Post> posts = postRepository.findPostByAuthor(result.getOldUsername());
        for (Post post : posts) {
            post.setAuthor(result.getNewUsername());
            postRepository.save(post);
        }
        System.out.println("Username updated, author now called " + result.getNewUsername() );
    }

}
