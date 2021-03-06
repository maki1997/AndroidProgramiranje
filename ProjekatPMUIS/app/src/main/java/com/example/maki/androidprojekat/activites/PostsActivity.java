package com.example.maki.androidprojekat.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.example.maki.androidprojekat.Adapters.PostAdapter;
import com.example.maki.androidprojekat.R;
import com.example.maki.androidprojekat.Database.DatabaseHelper;
import com.example.maki.androidprojekat.Database.DB_Helper;
import com.example.maki.androidprojekat.model.Post;
import com.example.maki.androidprojekat.model.User;

@SuppressWarnings("deprecation")
public class PostsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private DrawerLayout drawerLayout;
    private ListView listView;
    private String[] lista;
    private ActionBarDrawerToggle drawerListener;
    private ArrayList<Post> posts = new ArrayList<Post>();
    private DB_Helper helperDatabaseRead;
    private DatabaseHelper mDbHelper;
    private TextView usernameND;
    private TextView nameND;
    int idUser = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        Toast.makeText(this, "Welcome to PostsActivity", Toast.LENGTH_SHORT).show();

        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("mPref",0);
        idUser = pref.getInt("id",-1);




        lista = getResources().getStringArray(R.array.nav_drawer);
        listView = (ListView) findViewById(R.id.list_view_posts);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (position == 0) {
                    startActivity(new Intent(view.getContext(), PostsActivity.class));
                }
                if (position == 1) {
                    startActivity(new Intent(view.getContext(), CreatePostActivity.class));
                }
                if (position == 2) {
                    startActivity(new Intent(view.getContext(), SettingsAcitivity.class));
                }
                if (position == 3) {
                    startActivity(new Intent(view.getContext(), LoginActivity.class));
                    finish();
                }
            }


        });


        drawerLayout = (DrawerLayout) findViewById(R.id.posts_drawer_layout);
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.openNavDrawer, R.string.closeNavDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                helperDatabaseRead = new DB_Helper();
                usernameND = (TextView) findViewById(R.id.usernameDrawer);
                nameND = (TextView) findViewById(R.id.nameDrawer);
                User u=null;
                for(User uu:helperDatabaseRead.readUsers(PostsActivity.this)){
                    if(uu.getId() == idUser){
                        u=uu;
                    }
                }
                usernameND.setText(u.getUsername());
                nameND.setText(u.getName());
                Toast.makeText(PostsActivity.this, "Drawer Opened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Toast.makeText(PostsActivity.this, "Drawer Closed", Toast.LENGTH_SHORT).show();
            }

        };
        drawerLayout.setDrawerListener(drawerListener);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        helperDatabaseRead = new DB_Helper();
        User u=null;
        for(User usser :helperDatabaseRead.readUsers(this)){
            if(usser.getId() == 1){
                u=usser;
            }
        }



        /*Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.dark);
        Bitmap bitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.skull);
        Bitmap bitmap2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.app);
        User user1 = new User(0,"maki",null,"maki3377","maki3377",null,null);
        Post post1 = new Post(0,"Naziv","Ovo je neki opis.",bitmap,user1, new Date(2000,6,4,16,24),null,null,null,177,7);
        Post post2 = new Post(0,"Naziv2","Ovo je neki opis.",bitmap1,user1, new Date(2001  ,8,11,19,2),null,null,null,155,6);
        Post post3 = new Post(0,"Naziv3","Ovo je neki opis.",bitmap2,user1, new Date(2006,6,4,10,13),null,null,null,100,5);
        Comment comment1 = new Comment(0,"kom1","ovo je komentar broj 1",user1,null,post1,36,44);
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);*/
        posts = helperDatabaseRead.readPosts(this);
        PostAdapter pAdapter = new PostAdapter(this, posts);
        ListView listView1 = findViewById(R.id.posts);
        listView1.setAdapter(pAdapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Post clicked = (Post) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(PostsActivity.this,ReadPostActivity.class);
                intent.putExtra("id",clicked.getId());
                startActivity(intent);
            }

        });






        PreferenceManager.setDefaultValues(this,R.xml.preferences,false);





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
        getMenuInflater().inflate(R.menu.nav_menu, menu);
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
        if(item.getItemId() == R.id.createPost){
            startActivity(new Intent(this, CreatePostActivity.class));
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
        PreferenceManager.setDefaultValues(this,R.xml.preferences,false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean postDate = sharedPref.getBoolean("sort_posts_date",false);
        boolean postPop = sharedPref.getBoolean("sort_posts_popularity",false);
        boolean postDateDesc = sharedPref.getBoolean("sort_posts_date_desc",false);
        boolean postPopDesc = sharedPref.getBoolean("sort_posts_popularity_desc",false);

        if(postDate == true) {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
        }
        if(postPop == true){
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    return Integer.valueOf(o1.getLikes()-o1.getDislikes()).compareTo(o2.getLikes()-o2.getDislikes());
                }
            });
        }

        if(postDateDesc == true) {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    return o2.getDate().compareTo(o1.getDate());
                }
            });
        }
        if(postPopDesc == true){
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    return Integer.valueOf(o2.getLikes()-o2.getDislikes()).compareTo(o1.getLikes()-o1.getDislikes());
                }
            });
        }





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
