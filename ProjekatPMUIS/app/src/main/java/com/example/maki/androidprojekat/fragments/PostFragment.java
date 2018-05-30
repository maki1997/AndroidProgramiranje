package com.example.maki.androidprojekat.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maki.androidprojekat.Database.HelperDatabaseRead;
import com.example.maki.androidprojekat.R;
import com.example.maki.androidprojekat.activites.ReadPostActivity;
import com.example.maki.androidprojekat.model.Post;
import com.example.maki.androidprojekat.model.Tag;
import com.example.maki.androidprojekat.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Maki on 5/28/2018.
 */

public class PostFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ActionBarDrawerToggle toggle;
    private HelperDatabaseRead helperDatabaseRead;
    private ArrayList<Post> posts = new ArrayList<Post>();
    private Post post=null;
    private TextView textView;
    private int idUser;
    private int id;
    private int stanje = 0;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.post_fragment, container, false);
        init(view);
        likeAndDislike(view);

        return view;
    }

    public void init(View view){
        Intent myIntent = getActivity().getIntent();
        id= myIntent.getIntExtra("id",-1);
        ImageButton llike = (ImageButton) view.findViewById(R.id.imgLike);
        llike.setBackgroundResource(R.drawable.like);
        ImageButton dislike = (ImageButton) view.findViewById(R.id.imgDislike);
        dislike.setBackgroundResource(R.drawable.dislike);

        helperDatabaseRead = new HelperDatabaseRead();
        posts=helperDatabaseRead.loadPostsFromDatabase(getActivity());
        if(id != -1){
            for(Post pp: posts){
                if(pp.getId() == id){
                    post = pp;
                }
            }
        }

        textView=(TextView)view.findViewById(R.id.authorRP);
        textView.setText(post.getAuthor().getName());
        textView=(TextView)view.findViewById(R.id.titleRP);
        textView.setText(post.getTitle());
        textView=(TextView)view.findViewById(R.id.locRP);
        textView.setText(post.getLocation());
        textView=(TextView)view.findViewById(R.id.descRP);
        textView.setText(post.getDescription());
        textView=(TextView)view.findViewById(R.id.tagRP);
        List<Tag> tags=post.getTags();
        String tag="";
        int size= -1;
        if(post.getTags() == null){
            size=0;
        }else{
            size=post.getTags().size();
        }
        for(int i =0 ;i< size;i++){
            tag=tag+ ", "+tags.get(i).getName();
            textView.setText(tag);
        }
        textView=(TextView)view.findViewById(R.id.likes);
        String like= String.valueOf(post.getLikes());
        textView.setText(like);
        textView=(TextView)view.findViewById(R.id.dislikes);
        String disslike= String.valueOf(post.getDislikes());
        textView.setText(disslike);
        textView=(TextView)view.findViewById(R.id.date);
        String date = new SimpleDateFormat("dd.MM.yyyy").format(post.getDate());
        textView.setText(date);
    }


    public void likeAndDislike(final View view){
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("mPref",0);
        idUser = pref.getInt("id",-1);
        User u=null;
        for(User uu:helperDatabaseRead.loadUsersFromDatabase(getActivity())){
            if(uu.getId() == idUser){
                u=uu;
            }
        }
        if(post.getAuthor().getId() != u.getId()){
            ImageButton button = (ImageButton) view.findViewById(R.id.imgLike);
            button.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    switch (stanje){
                        case 0:
                            post.setLikes(post.getLikes()+1);
                            ImageButton imb = (ImageButton) view.findViewById(R.id.imgLike);
                            imb.setBackgroundResource(R.drawable.liked);
                            stanje=1;
                            helperDatabaseRead = new HelperDatabaseRead();
                            helperDatabaseRead.updatePost(post,getActivity(),null,null);
                            textView=(TextView)view.findViewById(R.id.likes);
                            textView.setText(String.valueOf(post.getLikes()));
                            break;
                        case 1:
                            post.setLikes(post.getLikes()-1);
                            ImageButton imbd = (ImageButton) view.findViewById(R.id.imgLike);
                            imbd.setBackgroundResource(R.drawable.like);
                            stanje=0;
                            helperDatabaseRead = new HelperDatabaseRead();
                            helperDatabaseRead.updatePost(post,getActivity(),null,null);
                            textView=(TextView)view.findViewById(R.id.likes);
                            textView.setText(String.valueOf(post.getLikes()));
                            break;
                        case -1:
                            post.setLikes(post.getLikes()+1);
                            post.setDislikes(post.getDislikes()-1);
                            stanje=1;
                            ImageButton imbds = (ImageButton) view.findViewById(R.id.imgDislike);
                            imbds.setBackgroundResource(R.drawable.dislike);
                            ImageButton imbds1 = (ImageButton) view.findViewById(R.id.imgLike);
                            imbds1.setBackgroundResource(R.drawable.liked);
                            helperDatabaseRead = new HelperDatabaseRead();
                            helperDatabaseRead.updatePost(post,getActivity(),null,null);
                            textView=(TextView)view.findViewById(R.id.likes);
                            textView.setText(String.valueOf(post.getLikes()));
                            textView=(TextView)view.findViewById(R.id.dislikes);
                            textView.setText(String.valueOf(post.getDislikes()));
                            break;
                    }

                }
            });
            ImageButton button2 = (ImageButton) view.findViewById(R.id.imgDislike);
            button2.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    switch (stanje){
                        case 0:
                            post.setDislikes(post.getDislikes()+1);
                            stanje=-1;
                            ImageButton imbd = (ImageButton) view.findViewById(R.id.imgDislike);
                            imbd.setBackgroundResource(R.drawable.disliked);
                            helperDatabaseRead = new HelperDatabaseRead();
                            helperDatabaseRead.updatePost(post,getActivity(),null,null);

                            textView=(TextView)view.findViewById(R.id.dislikes);
                            textView.setText(String.valueOf(post.getDislikes()));
                            break;
                        case 1:
                            post.setLikes(post.getLikes()-1);
                            post.setDislikes(post.getDislikes()+1);
                            ImageButton imgbd = (ImageButton) view.findViewById(R.id.imgDislike);
                            imgbd.setBackgroundResource(R.drawable.disliked);
                            ImageButton sss = (ImageButton) view.findViewById(R.id.imgLike);
                            sss.setBackgroundResource(R.drawable.like);
                            stanje=-1;
                            helperDatabaseRead = new HelperDatabaseRead();
                            helperDatabaseRead.updatePost(post,getActivity(),null,null);
                            textView=(TextView)view.findViewById(R.id.likes);
                            textView.setText(String.valueOf(post.getLikes()));
                            textView=(TextView)view.findViewById(R.id.dislikes);
                            textView.setText(String.valueOf(post.getDislikes()));
                            break;
                        case -1:

                            post.setDislikes(post.getDislikes()-1);
                            ImageButton imbds = (ImageButton) view.findViewById(R.id.imgDislike);
                            imbds.setBackgroundResource(R.drawable.dislike);
                            stanje=0;
                            helperDatabaseRead = new HelperDatabaseRead();
                            helperDatabaseRead.updatePost(post,getActivity(),null,null);

                            textView=(TextView)view.findViewById(R.id.dislikes);
                            textView.setText(String.valueOf(post.getDislikes()));
                            break;
                    }

                }
            });

        }else{
            Toast.makeText(getActivity(), "You cant like your post!", Toast.LENGTH_SHORT).show();
        }
    }
}

