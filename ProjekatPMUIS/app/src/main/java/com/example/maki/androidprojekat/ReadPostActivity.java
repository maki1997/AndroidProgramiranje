package com.example.maki.androidprojekat;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import model.Post;
import model.User;
import model.Comment;
import model.Tag;

public class ReadPostActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private DrawerLayout drawerLayout;
    private ListView listView;
    private String[] lista;
    private ActionBarDrawerToggle drawerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);

        Toast.makeText(this,"Welcome to ReadPostActivity",Toast.LENGTH_SHORT).show();
        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar3);
        setSupportActionBar(toolbar);
        lista=getResources().getStringArray(R.array.nav_drawer);
        listView = (ListView) findViewById(R.id.read_post_list);
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.dark);
        User user1 = new User(0,"maki",null,"maki3377","maki3377",null,null);
        Post post1 = new Post(0,"Naziv","Ovo je neki opis.",bitmap,user1,null,null,null,null,124,66);
        Comment comment1 = new Comment(0,"kom1","ovo je komentar broj 1",user1,null,post1,36,44);
        //((AppCompatTextView)findViewById(R.id.likes)).setText(post1.getLikes());
        ((TextView)findViewById(R.id.appCompatTextView2)).setText(post1.getTitle());
        ((TextView)findViewById(R.id.appCompatTextView)).setText(post1.getDescription());
        ((ImageView)findViewById(R.id.imageView1)).setImageBitmap(post1.getPhoto());
        ((TextView)findViewById(R.id.likes)).setText(String.valueOf(post1.getLikes()));
        ((TextView)findViewById(R.id.dislikes)).setText(String.valueOf(post1.getDislikes()));
        ((TextView)findViewById(R.id.appCompatTextView3)).setText(user1.getName());
        ((TextView)findViewById(R.id.komentar1)).setText(comment1.getDescription());
        ((TextView)findViewById(R.id.kom1likes)).setText(String.valueOf(comment1.getLikes()));
        ((TextView)findViewById(R.id.kom1dislikes)).setText(String.valueOf(comment1.getDislikes()));





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
                    startActivity(new Intent(view.getContext(), SettingsActivity.class));
                }
            }
        });

        drawerLayout =(DrawerLayout) findViewById(R.id.read_post_drawer_layout);
        drawerListener = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.openNavDrawer,R.string.closeNavDrawer){
            @Override
            public void onDrawerOpened(View drawerView) {
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
            startActivity(new Intent(this, SettingsActivity.class));
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


}
