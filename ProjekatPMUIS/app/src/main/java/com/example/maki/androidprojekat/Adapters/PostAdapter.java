package com.example.maki.androidprojekat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maki.androidprojekat.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.example.maki.androidprojekat.model.Post;

/**
 * Created by Maki on 5/9/2018.
 */

public class PostAdapter extends ArrayAdapter<Post> {



    public PostAdapter(Context context, ArrayList<Post> posts){
        super(context,0,posts);
    }



    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        Post post = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.posts_list,viewGroup,false);
        }
        //ImageView image_view = view.findViewById(R.id.item_icon);
        TextView date_view = view.findViewById(R.id.date_post_list);
        TextView title_view = view.findViewById(R.id.title_post_list);
        String newDate = new SimpleDateFormat("dd.MM.yyyy").format(post.getDate());
        date_view.setText(newDate);
        title_view.setText(post.getTitle());
        //image_view.setImageBitmap(post.getPhoto());


        return view;
    }
}