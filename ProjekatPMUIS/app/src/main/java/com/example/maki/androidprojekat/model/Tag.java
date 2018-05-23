package com.example.maki.androidprojekat.model;

import java.util.List;

/**
 * Created by Maki on 3/26/2018.
 */

public class Tag {
    private int id;
    private String name;
    private int post;

    public Tag(){
    }

    public Tag(int id, String name, int posts) {
        this.id = id;
        this.name = name;
        this.post = posts;
    }

    public Tag(String name,int post){

        this.name=name;
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosts() {
        return post;
    }

    public void setPosts(int post) {
        this.post = post;
    }


}
