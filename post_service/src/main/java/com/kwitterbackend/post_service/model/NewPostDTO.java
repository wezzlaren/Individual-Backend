package com.kwitterbackend.post_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewPostDTO {

    @JsonProperty
    private String title;

    @JsonProperty
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NewPostDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
