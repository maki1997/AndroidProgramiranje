package com.example.maki.androidprojekat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.maki.androidprojekat.R;

import java.util.ArrayList;

import com.example.maki.androidprojekat.model.Comment;
import com.example.maki.androidprojekat.model.Post;

/**
 * Created by Maki on 5/14/2018.
 */

public class CommentAdapter extends ArrayAdapter<Comment> {

    public CommentAdapter(Context context, ArrayList<Comment> comments){
        super(context,0,comments);
    }



    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        Comment comment = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.comments_list,viewGroup,false);
        }



        return view;
    }
}
