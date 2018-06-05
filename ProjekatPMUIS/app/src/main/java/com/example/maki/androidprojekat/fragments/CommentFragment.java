package com.example.maki.androidprojekat.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.maki.androidprojekat.Adapters.CommentAdapter;
import com.example.maki.androidprojekat.Database.DB_Helper;
import com.example.maki.androidprojekat.R;
import com.example.maki.androidprojekat.model.Comment;
import com.example.maki.androidprojekat.model.Post;
import com.example.maki.androidprojekat.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Maki on 5/28/2018.
 */

public class CommentFragment extends Fragment {

    private DB_Helper helperDatabaseRead;
    private ArrayList<Post> posts = new ArrayList<Post>();
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private Post post = null;
    private int id;
    private int stanje = 0;
    private Comment comment;
    private TextView textView;
    private TextView textView1;
    private TextView textView2;
    private int idUser;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comment_fragment, container, false);
        Intent myIntent = getActivity().getIntent();
        id = myIntent.getIntExtra("id", -1);
        helperDatabaseRead = new DB_Helper();
        posts = helperDatabaseRead.readPosts(getActivity());
        if (id != -1) {
            for (Post pp : posts) {
                if (pp.getId() == id) {
                    post = pp;
                }
            }
        }

        CommentAdapter commentAdapter = new CommentAdapter(getActivity(), post.getComments());
        ListView listView = view.findViewById(R.id.readpost_list_view_comment);
        listView.setAdapter(commentAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Comment clickedObj = (Comment) adapterView.getItemAtPosition(i);
                comment = clickedObj;


            }
        });



        Button button = (Button) view.findViewById(R.id.postComment);
        textView2 = (TextView) view.findViewById(R.id.comment);
        textView1 = (TextView) view.findViewById(R.id.titleComm);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                helperDatabaseRead = new DB_Helper();
                String title = textView1.getText().toString();
                String desc = textView2.getText().toString();
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("mPref", 0);
                idUser = pref.getInt("id", -1);
                User u = null;
                for (User uu : helperDatabaseRead.readUsers(getActivity())) {
                    if (uu.getId() == idUser) {
                        u = uu;
                    }
                }
                Comment c = new Comment(title, desc, u, getDateTime(), id, 0, 0);
                helperDatabaseRead.addComment(c, getActivity());


            }
        });




        return view;




}

    @Override
    public void onResume() {
        super.onResume();
        PreferenceManager.setDefaultValues(getActivity(),R.xml.preferences,false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean commDate = sharedPref.getBoolean("sort_comments_date",true);
        boolean commPop = sharedPref.getBoolean("sort_comments_popularity",false);
        boolean commDateDesc = sharedPref.getBoolean("sort_comments_date_desc",false);
        boolean commPopDesc = sharedPref.getBoolean("sort_comments_popularity_desc",false);

        if(commDate == true) {
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
        }
        if(commPop == true){
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    return Integer.valueOf(o1.getLikes()-o1.getDislikes()).compareTo(o2.getLikes()-o2.getDislikes());
                }
            });
        }

        if(commDateDesc == true) {
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    return o2.getDate().compareTo(o1.getDate());
                }
            });
        }
        if(commPopDesc == true){
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    return Integer.valueOf(o2.getLikes()-o2.getDislikes()).compareTo(o1.getLikes()-o1.getDislikes());
                }
            });
        }
    }


    public void likesAndDislikes(final View view){

        ImageButton button = (ImageButton) view.findViewById(R.id.commLike);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                switch (stanje){
                    case 0:
                        comment.setLikes(comment.getLikes()+1);
                        stanje=1;
                        helperDatabaseRead = new DB_Helper();

                        helperDatabaseRead.updateComment(comment,getActivity(),null,null);
                        textView=(TextView)view.findViewById(R.id.likeComm);
                        textView.setText(String.valueOf(comment.getLikes()));
                        break;
                    case 1:
                        comment.setLikes(comment.getLikes()-1);
                        stanje=0;
                        helperDatabaseRead = new DB_Helper();
                        helperDatabaseRead.updateComment(comment,getActivity(),null,null);
                        textView=(TextView)view.findViewById(R.id.likeComm);
                        textView.setText(String.valueOf(comment.getLikes()));
                        break;
                    case -1:
                        comment.setLikes(comment.getLikes()+1);
                        comment.setDislikes(comment.getDislikes()-1);
                        stanje=1;
                        helperDatabaseRead = new DB_Helper();
                        helperDatabaseRead.updateComment(comment,getActivity(),null,null);
                        textView=(TextView)view.findViewById(R.id.likeComm);
                        textView.setText(String.valueOf(comment.getLikes()));
                        textView=(TextView)view.findViewById(R.id.dislkeComm);
                        textView.setText(String.valueOf(comment.getDislikes()));
                        break;
                }

            }
        });
        ImageButton button2 = (ImageButton) view.findViewById(R.id.commDislike);
        button2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                switch (stanje){
                    case 0:
                        comment.setDislikes(comment.getDislikes()+1);

                        stanje=-1;
                        helperDatabaseRead = new DB_Helper();
                        helperDatabaseRead.updateComment(comment,getActivity(),null,null);

                        textView=(TextView)view.findViewById(R.id.dislkeComm);
                        textView.setText(String.valueOf(comment.getDislikes()));
                        break;
                    case 1:
                        comment.setLikes(comment.getLikes()-1);
                        comment.setDislikes(comment.getDislikes()+1);

                        stanje=-1;
                        helperDatabaseRead = new DB_Helper();
                        helperDatabaseRead.updateComment(comment,getActivity(),null,null);
                        textView=(TextView)view.findViewById(R.id.likeComm);
                        textView.setText(String.valueOf(comment.getLikes()));
                        textView=(TextView)view.findViewById(R.id.dislkeComm);
                        textView.setText(String.valueOf(comment.getDislikes()));
                        break;
                    case -1:

                        comment.setDislikes(comment.getDislikes()-1);

                        stanje=0;
                        helperDatabaseRead = new DB_Helper();
                        helperDatabaseRead.updateComment(comment,getActivity(),null,null);

                        textView=(TextView)view.findViewById(R.id.dislkeComm);
                        textView.setText(String.valueOf(comment.getDislikes()));
                        break;
                }

            }
        });


    }

    private Date getDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = new Date();
        String sDate= dateFormat.format(date);
        return  convertStringToDate(sDate);
    }
    public Date convertStringToDate(String dtStart){

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        try {
            Date date = format.parse(dtStart);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


}

