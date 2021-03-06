package com.example.maki.androidprojekat.model;

import android.graphics.Bitmap;
import android.location.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Maki on 3/26/2018.
 */

public class Post {
    private int id;
    private String title;
    private String description;
    private Bitmap photo;
    private User author;
    private Date date;
    private String location;
    private ArrayList<Tag> tags;
    private ArrayList<Comment> comments;
    private int likes;
    private int dislikes;

    public Post(){
    }


    public Post(String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }
    public Post(int id, String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public Post(int id, String title, String description, Bitmap photo,
                User author, Date date, String location, ArrayList<Tag> tags, ArrayList<Comment> comments, int likes, int dislikes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.photo = photo;
        this.author = author;
        this.date = date;
        this.location = location;
        this.tags = tags;
        this.comments = comments;
        this.likes = likes;
        this.dislikes = dislikes;
    }
    public Post( String title, String description, Bitmap photo,
                 User author, Date date, String location, ArrayList<Tag> tags, ArrayList<Comment> comments, int likes, int dislikes) {

        this.title = title;
        this.description = description;
        this.photo = photo;
        this.author = author;
        this.date = date;
        this.location = location;
        this.tags = tags;
        this.comments = comments;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
}