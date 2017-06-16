package com.konect4.model;

/**
 * Created by Muhammad Zeeshan on 6/6/2017.
 */

public class MessageData {

    int user_id;
    String username;
    String date_time;
    String message;
    String status;

    public MessageData(int user_id, String username, String date_time, String message, String status) {
        this.user_id = user_id;
        this.username = username;
        this.date_time = date_time;
        this.message = message;
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Message{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", date_time='" + date_time + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
