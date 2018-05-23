package com.example.maki.androidprojekat.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maki.androidprojekat.Adapters.CommentAdapter;
import com.example.maki.androidprojekat.R;
import com.example.maki.androidprojekat.Database.HelperDatabaseRead;
import com.example.maki.androidprojekat.model.Comment;
import com.example.maki.androidprojekat.model.Post;
import com.example.maki.androidprojekat.model.Tag;
import com.example.maki.androidprojekat.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReadPostActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private DrawerLayout drawerLayout;
    private ListView listView;
    private String[] lista;
    private String[] postovii;
    private ActionBarDrawerToggle drawerListener;
    private HelperDatabaseRead helperDatabaseRead;
    private ArrayList<Post> posts = new ArrayList<Post>();
    private Post post=null;
    private TextView textView;
    private TextView dateView;
    private int idUser;
    private int id;
    private TextView usernameND;
    private TextView nameND;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("mPref",0);
        idUser = pref.getInt("id",-1);

        Toast.makeText(this,"Welcome to ReadPostActivity",Toast.LENGTH_SHORT).show();
        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar3);
        setSupportActionBar(toolbar);
        lista=getResources().getStringArray(R.array.nav_drawer);
        postovii=getResources().getStringArray(R.array.posts);
        listView = (ListView) findViewById(R.id.read_post_list);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,lista));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(position == 0){
                    startActivity(new Intent(view.getContext(), PostsActivity.class));
                }
                if(position == 1){
                    startActivity(new Intent(view.getContext(), CreatePostActivity.class));
                }
                if(position == 2){
                    startActivity(new Intent(view.getContext(), SettingsAcitivity.class));
                }
                if (position == 3) {
                    startActivity(new Intent(view.getContext(), LoginActivity.class));
                    finish();
                }
            }
        });


        drawerLayout =(DrawerLayout) findViewById(R.id.read_post_drawer_layout);
        drawerListener = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.openNavDrawer,R.string.closeNavDrawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                usernameND = (TextView) findViewById(R.id.usernameDrawer);
                nameND = (TextView) findViewById(R.id.nameDrawer);
                User u=null;
                for(User uu:helperDatabaseRead.loadUsersFromDatabase(ReadPostActivity.this)){
                    if(uu.getId() == idUser){
                        u=uu;
                    }
                }
                usernameND.setText(u.getUsername());
                nameND.setText(u.getName());
                Toast.makeText(ReadPostActivity.this,"Drawer Opened",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Toast.makeText(ReadPostActivity.this,"Drawer Closed",Toast.LENGTH_SHORT).show();
            }

        };
        //noinspection deprecation
        drawerLayout.setDrawerListener(drawerListener);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Post p=null;
        Intent myIntent = getIntent();
        id= myIntent.getIntExtra("id",-1);
        helperDatabaseRead = new HelperDatabaseRead();
        posts=helperDatabaseRead.loadPostsFromDatabase(this);
        if(id != -1){
            for(Post pp: posts){
                if(pp.getId() == id){
                    post = pp;
                }
            }
        }
        textView=(TextView)findViewById(R.id.titleRP);
        textView.setText(post.getTitle());
        textView=(TextView)findViewById(R.id.authorRP);
        textView.setText(post.getAuthor().getName());
        textView=(TextView)findViewById(R.id.descRP);
        textView.setText(post.getDescription());
        textView=(TextView)findViewById(R.id.tagRP);
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
        textView=(TextView)findViewById(R.id.likes);
        String like= String.valueOf(post.getLikes());
        textView.setText(like);
        textView=(TextView)findViewById(R.id.dislikes);
        String disslike= String.valueOf(post.getDislikes());
        textView.setText(disslike);
        textView=(TextView)findViewById(R.id.date);
        String date = new SimpleDateFormat("dd.MM.yyyy").format(post.getDate());
        textView.setText(date);
        ArrayList<Comment> mComm=new ArrayList<Comment>();
        for(Comment c:helperDatabaseRead.loadCommentsFromDatabase(this)){
            if(c.getPost() == post.getId())
                mComm.add(c);
        }
        CommentAdapter commentAdapter=new CommentAdapter(this,mComm);
        ListView listView = findViewById(R.id.readpost_list_view_comment);
        listView.setAdapter(commentAdapter);


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu_rp, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerListener.onOptionsItemSelected(item))
        {
            return  true;
        }
        if(item.getItemId() == R.id.settings){
            startActivity(new Intent(this, SettingsAcitivity.class));
        }
        if(item.getItemId() == R.id.delete){
            Toast.makeText(this,"Deleted",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, PostsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onStart() {
        super.onStart();
    };

    protected void onRestart(){
        super.onRestart();
    };

    protected void onResume(){

        ArrayList<Comment> mComm=new ArrayList<Comment>();
        for(Comment c:helperDatabaseRead.loadCommentsFromDatabase(this)){
            if(c.getPost() == post.getId())
                mComm.add(c);
        }
        CommentAdapter commentAdapter=new CommentAdapter(this,mComm);
        ListView listView = findViewById(R.id.readpost_list_view_comment);
        listView.setAdapter(commentAdapter);
        helperDatabaseRead = new HelperDatabaseRead();

        Button b = (Button) findViewById(R.id.postComment);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView=(TextView)findViewById(R.id.titleComm);
                String title=textView.getText().toString();
                textView=(TextView)findViewById(R.id.comment);
                String desc=textView.getText().toString();
                dateView = (TextView) findViewById(R.id.dateComm);
                String date =dateView.getText().toString();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("mPref",0);
                idUser = pref.getInt("id",-1);
                User u=null;
                for(User uu:helperDatabaseRead.loadUsersFromDatabase(ReadPostActivity.this)){
                    if(uu.getId() == idUser){
                        u=uu;
                    }
                }
                Comment c= new Comment(title,desc,u,getDateTime(),id,0,0);
                helperDatabaseRead.insertComment(c,ReadPostActivity.this);

            }
        });

        super.onResume();
    };

    protected void onPause(){
        super.onPause();
    };

    protected void onStop(){
        super.onStop();};

    protected void onDestroy(){
        super.onDestroy();
    };




    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(this,lista[position] + " was selected",Toast.LENGTH_SHORT).show();
        selectItem(position);
    }

    private void selectItem(int position) {
        listView.setItemChecked(position,true);
    }

    public void setTitle(String title){
        getSupportActionBar().setTitle(title);

    }

    private Date getDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        String sDate= dateFormat.format(date);
        Toast.makeText(this,sDate,Toast.LENGTH_SHORT).show();
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
