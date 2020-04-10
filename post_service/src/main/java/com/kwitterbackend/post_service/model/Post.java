package com.kwitterbackend.post_service.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant dateCreated;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String content;


    public Post() {
    }


    public Post(Long userId, String content){
        this.userId = userId;
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    //TODO: maybe add username
//    @Column(nullable = false)
//    private String username;


}
