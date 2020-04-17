package com.kwitterbackend.post_service.model;

public class UpdateUserEvent {

    private String oldUsername;
    private String newUsername;

    public String getOldUsername() {
        return oldUsername;
    }

    public void setOldUsername(String oldUsername) {
        this.oldUsername = oldUsername;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    @Override
    public String toString() {
        return "UpdateUserEvent{" +
                "oldUsername='" + oldUsername + '\'' +
                ", newUsername='" + newUsername + '\'' +
                '}';
    }
}
