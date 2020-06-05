package com.kwitterbackend.user_service.model;

public class DeleteUserEvent {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "DeleteUserEvent{" +
                "username='" + username + '\'' +
                '}';
    }
}
