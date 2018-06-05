package com.example.maki.androidprojekat.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.maki.androidprojekat.Database.DB_Helper;
import com.example.maki.androidprojekat.R;
import com.example.maki.androidprojekat.model.Comment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CommentAdapter extends ArrayAdapter<Comment> {
    ArrayList<Comment> mComm = new ArrayList<>();
    Context mContext = null;
    private int stanje = 0;
    private Comment comment;
    private TextView textView;
    private DB_Helper helperDatabaseRead;
    public CommentAdapter(Context context, ArrayList<Comment> comments){
        super(context,0,comments);
        this.mContext= context;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        comment = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.comment_layout,viewGroup,false);
        }

        TextView author_view = view.findViewById(R.id.authorComm);
        TextView desc_view = view.findViewById(R.id.desc_comm);
        TextView like_view = view.findViewById(R.id.likeComm);
        TextView dislike_view = view.findViewById(R.id.dislkeComm);
        TextView date_view=view.findViewById(R.id.commentsDate);
        author_view.setText(comment.getAuthor().getName());
        desc_view.setText(comment.getDescription());
        String sl=String.valueOf(comment.getLikes());
        like_view.setText(sl);
        String dis=String.valueOf(comment.getDislikes());
        dislike_view.setText(dis);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String sDate= dateFormat.format(comment.getDate());
        date_view.setText(sDate);
        likesAndDislikes(view);

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
                        helperDatabaseRead = new DB_Helper();

                        helperDatabaseRead.updateComment(comment, (Activity) mContext,null,null);
                        textView=(TextView)view.findViewById(R.id.likeComm);
                        textView.setText(String.valueOf(comment.getLikes()));
                        break;
                    case 1:
                        comment.setLikes(comment.getLikes()-1);
                        stanje=0;
                        helperDatabaseRead = new DB_Helper();
                        helperDatabaseRead.updateComment(comment,(Activity) mContext,null,null);
                        textView=(TextView)view.findViewById(R.id.likeComm);
                        textView.setText(String.valueOf(comment.getLikes()));
                        break;
                    case -1:
                        comment.setLikes(comment.getLikes()+1);
                        comment.setDislikes(comment.getDislikes()-1);
                        stanje=1;
                        helperDatabaseRead = new DB_Helper();
                        helperDatabaseRead.updateComment(comment,(Activity) mContext,null,null);
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
                        helperDatabaseRead.updateComment(comment,(Activity) mContext,null,null);

                        textView=(TextView)view.findViewById(R.id.dislkeComm);
                        textView.setText(String.valueOf(comment.getDislikes()));
                        break;
                    case 1:
                        comment.setLikes(comment.getLikes()-1);
                        comment.setDislikes(comment.getDislikes()+1);
                        stanje=-1;
                        helperDatabaseRead = new DB_Helper();
                        helperDatabaseRead.updateComment(comment,(Activity) mContext,null,null);
                        textView=(TextView)view.findViewById(R.id.likeComm);
                        textView.setText(String.valueOf(comment.getLikes()));
                        textView=(TextView)view.findViewById(R.id.dislkeComm);
                        textView.setText(String.valueOf(comment.getDislikes()));
                        break;
                    case -1:

                        comment.setDislikes(comment.getDislikes()-1);
                        stanje=0;
                        helperDatabaseRead = new DB_Helper();
                        helperDatabaseRead.updateComment(comment,(Activity) mContext,null,null);

                        textView=(TextView)view.findViewById(R.id.dislkeComm);
                        textView.setText(String.valueOf(comment.getDislikes()));
                        break;
                }

            }
        });


    }
}