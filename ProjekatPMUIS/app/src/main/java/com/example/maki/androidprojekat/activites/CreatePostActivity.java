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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maki.androidprojekat.R;
import com.example.maki.androidprojekat.Database.HelperDatabaseRead;
import com.example.maki.androidprojekat.model.Post;
import com.example.maki.androidprojekat.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@SuppressWarnings("ConstantConditions")

public class CreatePostActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private DrawerLayout drawerLayout;
    private ListView listView;
    private String[] lista;
    private ActionBarDrawerToggle drawerListener;
    int idUser = -1;
    private EditText textView;
    private HelperDatabaseRead helperDatabaseRead;
    private TextView usernameND;
    private TextView nameND;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("mPref",0);
        idUser = pref.getInt("id",-1);
        Toast.makeText(this,"Welcome to CreatePostActivity",Toast.LENGTH_SHORT).show();
        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar1);
        setSupportActionBar(toolbar);
        lista=getResources().getStringArray(R.array.nav_drawer);
        listView = (ListView) findViewById(R.id.list_create_post);
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



        drawerLayout =(DrawerLayout) findViewById(R.id.create_post_drawer_layout);
        drawerListener = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.openNavDrawer,R.string.closeNavDrawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                /*usernameND = (TextView) findViewById(R.id.usernameDrawer);
                nameND = (TextView) findViewById(R.id.nameDrawer);
                User u=null;
                for(User uu:helperDatabaseRead.loadUsersFromDatabase(CreatePostActivity.this)){
                    if(uu.getId() == idUser){
                        u=uu;
                    }
                }
                usernameND.setText(u.getUsername());
                nameND.setText(u.getName());*/
                Toast.makeText(CreatePostActivity.this,"Drawer Opened",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Toast.makeText(CreatePostActivity.this,"Drawer Closed",Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.nav_menu_cp, menu);
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
        if(item.getItemId() == R.id.yes){
            createPost();
            Toast.makeText(this,"Created",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, PostsActivity.class));
        }
        if(item.getItemId() == R.id.no){
            Toast.makeText(this,"Canceled",Toast.LENGTH_SHORT).show();
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

    public void createPost(){
        helperDatabaseRead = new HelperDatabaseRead();
        textView=(EditText) findViewById(R.id.titleCP);
        String title=textView.getText().toString();
        textView=(EditText)findViewById(R.id.descriptionCP);
        String desc=textView.getText().toString();
        textView=(EditText)findViewById(R.id.tagsCp);
        String tag=textView.getText().toString();
        User u=null;
        for(User uu:helperDatabaseRead.loadUsersFromDatabase(this)){
            if(uu.getId() == idUser){
                u=uu;
            }
        }
        Post p =new Post(title,desc,null,u,getDateTime(),"gaga",null,null,0,0);
        helperDatabaseRead.insertPost(p,this);
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
