package model;

import java.util.Date;

/**
 * Created by Maki on 3/26/2018.
 */

public class Comment {
    private int id;
    private String title;
    private String description;
    private User author;
    private Date date;
    private Post post;
    private int likes;
    private int dislikes;
    private Status status;
}
