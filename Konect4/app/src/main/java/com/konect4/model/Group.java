package com.konect4.model;

/**
 * Created by Muhammad Zeeshan on 6/2/2017.
 */

public class Group {


    String name;
    String image_url;

    public Group(String name, String image_url) {
        this.name = name;
        this.image_url = image_url;
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

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}

