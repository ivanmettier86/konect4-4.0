package com.konect4.model;

/**
 * Created by Muhammad Zeeshan on 6/2/2017.
 */

public class Friends {

    String friend_id;
    String name;
    String image_url;
    String distance;
    boolean is_in_contacts;
    boolean is_friend;


    public Friends(String friend_id, String name, String image_url, String distance, boolean is_in_contacts, boolean is_friend) {
        this.friend_id = friend_id;
        this.name = name;
        this.image_url = image_url;
        this.distance = distance;
        this.is_in_contacts = is_in_contacts;
        this.is_friend = is_friend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public boolean is_in_contacts() {
        return is_in_contacts;
    }

    public void setIs_in_contacts(boolean is_in_contacts) {
        this.is_in_contacts = is_in_contacts;
    }

    public boolean is_friend() {
        return is_friend;
    }

    public void setIs_friend(boolean is_friend) {
        this.is_friend = is_friend;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }

    @Override
    public String toString() {
        return "Friends{" +
                "friend_id='" + friend_id + '\'' +
                ", name='" + name + '\'' +
                ", image_url='" + image_url + '\'' +
                ", distance='" + distance + '\'' +
                ", is_in_contacts=" + is_in_contacts +
                ", is_friend=" + is_friend +
                '}';
    }
}
