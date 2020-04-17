package com.kwitterbackend.user_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsernameDTO {

    @JsonProperty
    private String username;

    public UsernameDTO(String username) {
        this.username = username;
    }

    public UsernameDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
