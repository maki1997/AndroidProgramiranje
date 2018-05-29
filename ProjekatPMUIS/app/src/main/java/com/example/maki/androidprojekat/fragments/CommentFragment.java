package com.example.maki.androidprojekat.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
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
import com.example.maki.androidprojekat.Database.HelperDatabaseRead;
import com.example.maki.androidprojekat.R;
import com.example.maki.androidprojekat.activites.ReadPostActivity;
import com.example.maki.androidprojekat.model.Comment;
import com.example.maki.androidprojekat.model.Post;
import com.example.maki.androidprojekat.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Maki on 5/28/2018.
 */

public class CommentFragment extends Fragment {

    private HelperDatabaseRead helperDatabaseRead;
    private ArrayList<Post> posts = new ArrayList<Post>();
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
        helperDatabaseRead = new HelperDatabaseRead();
        posts = helperDatabaseRead.loadPostsFromDatabase(getActivity());
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
                Comment clickedObj = (Comment) adapterView.getItemAtPosition(i);
                comment = clickedObj;
            }
        });

        Button button = (Button) view.findViewById(R.id.postComment);
        textView2 = (TextView) view.findViewById(R.id.comment);
        textView1 = (TextView) view.findViewById(R.id.titleComm);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                helperDatabaseRead = new HelperDatabaseRead();
                String title = textView1.getText().toString();
                String desc = textView2.getText().toString();
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("mPref", 0);
                idUser = pref.getInt("id", -1);
                User u = null;
                for (User uu : helperDatabaseRead.loadUsersFromDatabase(getActivity())) {
                    if (uu.getId() == idUser) {
                        u = uu;
                    }
                }
                Comment c = new Comment(title, desc, u, getDateTime(), id, 0, 0);
                helperDatabaseRead.insertComment(c, getActivity());


            }
        });



        return view;




}


    public void likesAndDislikes(final View view){

        ImageButton button = (ImageButton) view.findViewById(R.id.commLike);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                switch (stanje){
                    case 0:
                        comment.setLikes(comment.getLikes()+1);
                        stanje=1;
                        helperDatabaseRead = new HelperDatabaseRead();

                        helperDatabaseRead.updateComment(comment,getActivity(),null,null);
                        textView=(TextView)view.findViewById(R.id.likeComm);
                        textView.setText(String.valueOf(comment.getLikes()));
                        break;
                    case 1:
                        comment.setLikes(comment.getLikes()-1);
                        stanje=0;
                        helperDatabaseRead = new HelperDatabaseRead();
                        helperDatabaseRead.updateComment(comment,getActivity(),null,null);
                        textView=(TextView)view.findViewById(R.id.likeComm);
                        textView.setText(String.valueOf(comment.getLikes()));
                        break;
                    case -1:
                        comment.setLikes(comment.getLikes()+1);
                        comment.setDislikes(comment.getDislikes()-1);
                        stanje=1;
                        helperDatabaseRead = new HelperDatabaseRead();
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
                        helperDatabaseRead = new HelperDatabaseRead();
                        helperDatabaseRead.updateComment(comment,getActivity(),null,null);

                        textView=(TextView)view.findViewById(R.id.dislkeComm);
                        textView.setText(String.valueOf(comment.getDislikes()));
                        break;
                    case 1:
                        comment.setLikes(comment.getLikes()-1);
                        comment.setDislikes(comment.getDislikes()+1);

                        stanje=-1;
                        helperDatabaseRead = new HelperDatabaseRead();
                        helperDatabaseRead.updateComment(comment,getActivity(),null,null);
                        textView=(TextView)view.findViewById(R.id.likeComm);
                        textView.setText(String.valueOf(comment.getLikes()));
                        textView=(TextView)view.findViewById(R.id.dislkeComm);
                        textView.setText(String.valueOf(comment.getDislikes()));
                        break;
                    case -1:

                        comment.setDislikes(comment.getDislikes()-1);

                        stanje=0;
                        helperDatabaseRead = new HelperDatabaseRead();
                        helperDatabaseRead.updateComment(comment,getActivity(),null,null);

                        textView=(TextView)view.findViewById(R.id.dislkeComm);
                        textView.setText(String.valueOf(comment.getDislikes()));
                        break;
                }

            }
        });


    }

    private Date getDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        String sDate= dateFormat.format(date);
        return  convertStringToDate(sDate);
    }
    public Date convertStringToDate(String dtStart){

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date date = format.parse(dtStart);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


}

