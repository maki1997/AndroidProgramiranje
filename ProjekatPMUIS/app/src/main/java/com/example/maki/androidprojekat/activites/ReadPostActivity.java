package com.example.maki.androidprojekat.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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

import com.example.maki.androidprojekat.Adapters.SectionsPagerAdapter;
import com.example.maki.androidprojekat.Database.Contracts.PostContract;
import com.example.maki.androidprojekat.R;
import com.example.maki.androidprojekat.Database.DB_Helper;
import com.example.maki.androidprojekat.fragments.CommentFragment;
import com.example.maki.androidprojekat.fragments.PostFragment;
import com.example.maki.androidprojekat.model.Post;
import com.example.maki.androidprojekat.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReadPostActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private DrawerLayout drawerLayout;
    private ListView listView;
    private String[] lista;
    private String[] postovii;
    private ActionBarDrawerToggle drawerListener;
    private DB_Helper helperDatabaseRead;
    private ArrayList<Post> posts = new ArrayList<Post>();
    private Post post=null;
    private TextView textView;
    private TextView dateView;
    private int idUser;
    private int id;
    private TextView usernameND;
    private TextView nameND;
    private SectionsPagerAdapter mSPA;
    private ViewPager mViewPager;

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
                    finishAffinity();
                    startActivity(new Intent(view.getContext(), LoginActivity.class));

                }
            }
        });

        mSPA = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        final int[] ICONS = new int[]{
                R.drawable.post,
                R.drawable.comment
        };
        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);



        drawerLayout =(DrawerLayout) findViewById(R.id.read_post_drawer_layout);
        drawerListener = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.openNavDrawer,R.string.closeNavDrawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                helperDatabaseRead = new DB_Helper();
                usernameND = (TextView) findViewById(R.id.usernameDrawer);
                nameND = (TextView) findViewById(R.id.nameDrawer);
                User u=null;
                for(User uu:helperDatabaseRead.readUsers(ReadPostActivity.this)){
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

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PostFragment(), "");
        adapter.addFragment(new CommentFragment(), "");

        viewPager.setAdapter(adapter);
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
            Intent myIntent = getIntent();
            id= myIntent.getIntExtra("id",-1);
            helperDatabaseRead = new DB_Helper();
            posts=helperDatabaseRead.readPosts(this);
            if(id != -1) {
                for (Post pp : posts) {
                    if (pp.getId() == id) {
                        post = pp;
                    }
                }
            }
            Uri uri=Uri.withAppendedPath(PostContract.PostEntry.CONTENT_URI,String.valueOf(post.getId()));
            this.getContentResolver().delete(uri,null,null);
            Intent startPosts = new Intent(this,PostsActivity.class);
            startActivity(startPosts);
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
