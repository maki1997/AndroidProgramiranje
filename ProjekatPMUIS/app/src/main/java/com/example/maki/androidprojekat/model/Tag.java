package com.example.maki.androidprojekat.model;

import java.util.List;

/**
 * Created by Maki on 3/26/2018.
 */

public class Tag {
    private int id;
    private String name;
    private int post_id;

    public Tag(){
    }

    public Tag(int id, String name, int post_id) {
        this.id = id;
        this.name = name;
        this.post_id = post_id;
    }

    public Tag(String name,int post_id){

        this.name=name;
        this.post_id = post_id;
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

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post) {
        this.post_id = post;
    }


}
