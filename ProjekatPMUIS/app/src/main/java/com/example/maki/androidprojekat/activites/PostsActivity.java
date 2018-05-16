package com.example.maki.androidprojekat.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import com.example.maki.androidprojekat.Adapters.PostAdapter;
import com.example.maki.androidprojekat.Database.ContentProvider;
import com.example.maki.androidprojekat.Database.MyDatabaseHelper;
import com.example.maki.androidprojekat.R;
import com.example.maki.androidprojekat.model.Comment;
import com.example.maki.androidprojekat.model.Post;
import com.example.maki.androidprojekat.model.User;
import com.example.maki.androidprojekat.util.Util;

@SuppressWarnings("deprecation")
public class PostsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,LoaderManager.LoaderCallbacks<Cursor> {

    private DrawerLayout drawerLayout;
    private ListView listView;
    private String[] lista;
    private ActionBarDrawerToggle drawerListener;
    private ArrayList<Post> posts = new ArrayList<Post>();
    private boolean spbd;
    private boolean spbp;
    private SimpleCursorAdapter dataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        Toast.makeText(this, "Welcome to PostsActivity", Toast.LENGTH_SHORT).show();

        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        Util.initDB(PostsActivity.this);

        /*displayListView();*/


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
            }


        });


        drawerLayout = (DrawerLayout) findViewById(R.id.posts_drawer_layout);
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.openNavDrawer, R.string.closeNavDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
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

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.dark);
        Bitmap bitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.skull);
        Bitmap bitmap2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.app);
        User user1 = new User(0,"maki",null,"maki3377","maki3377",null,null);
        Post post1 = new Post(0,"Naziv","Ovo je neki opis.",bitmap,user1, new Date(2000,6,4,16,24),null,null,null,177,7);
        Post post2 = new Post(0,"Naziv2","Ovo je neki opis.",bitmap1,user1, new Date(2001  ,8,11,19,2),null,null,null,155,6);
        Post post3 = new Post(0,"Naziv3","Ovo je neki opis.",bitmap2,user1, new Date(2006,6,4,10,13),null,null,null,100,5);
        Comment comment1 = new Comment(0,"kom1","ovo je komentar broj 1",user1,null,post1,36,44);
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        PostAdapter pAdapter = new PostAdapter(this, posts);
        ListView listView1 = findViewById(R.id.posts);
        listView1.setAdapter(pAdapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PostsActivity.this, ReadPostActivity.class);
                startActivity(intent);
            }

        });

        /*SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(this);
        spbd = spf.getBoolean("sortDate",true);
        spbp = spf.getBoolean("sortPop",true);
        if(spbd== true){
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post post, Post t1) {
                    return post.getDate().compareTo(t1.getDate()) ;
                }
            });
        }

        if(spbp == true){
            Collections.sort(posts, new Comparator< Post >() {
                @Override
                public int compare(Post post, Post t1) {
                    return   post.getPopularity() - t1.getPopularity();
                }


            });
        }*/



/*        String[] from = new String[] { MyDatabaseHelper.KEY_TITLE, MyDatabaseHelper.KEY_DATE };
        int[] to = new int[] {R.id.title_post_list, R.id.date_post_list};
        adapter = new SimpleCursorAdapter(this, R.layout.posts_list, null, from,
                to, 0);
        ListView listView1 = findViewById(R.id.posts);
        listView1.setAdapter(adapter);*/

        PreferenceManager.setDefaultValues(this,R.xml.preferences,false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean postDate = sharedPref.getBoolean("sort_posts_date",true);
        boolean postPop = sharedPref.getBoolean("sort_posts_popularity",false);
        boolean postDateDesc = sharedPref.getBoolean("sort_posts_date_desc",false);
        boolean postPopDesc = sharedPref.getBoolean("sort_posts_popularity_desc",false);
        boolean commentDate = sharedPref.getBoolean("sort_comments_date",false);
        boolean commentPop = sharedPref.getBoolean("sort_comments_popularity",false);
        boolean commentDateDesc = sharedPref.getBoolean("sort_comments_date_desc",false);
        boolean commentPopDesc = sharedPref.getBoolean("sort_comments_popularity_desc",false);




    }

/*    private void displayListView() {

        String[] from = new String[] { MyDatabaseHelper.KEY_TITLE, MyDatabaseHelper.KEY_DATE };
        int[] to = new int[] {R.id.title_post_list, R.id.date_post_list};
        dataAdapter = new SimpleCursorAdapter(this, R.layout.posts_list, null, from,
                to, 0);


        // get reference to the ListView
        ListView listView2 = (ListView) findViewById(R.id.posts);
        // Assign adapter to ListView
        listView2.setAdapter(dataAdapter);
        //Ensures a loader is initialized and active.
        getSupportLoaderManager().initLoader(0, null,this );


        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listview, View view, int position, long id) {

                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                String rowId =
                        cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.KEY_ROWID));

                Intent postEdit = new Intent(getBaseContext(), ReadPostActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("mode", "update");
                bundle.putString("rowId", rowId);
                postEdit.putExtras(bundle);
                startActivity(postEdit);

            }
        });*/

   /* }*/



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
                    return Integer.valueOf(o1.getPopularity()).compareTo(o2.getPopularity());
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
                    return Integer.valueOf(o2.getPopularity()).compareTo(o1.getPopularity());
                }
            });
        }

        /*PostAdapter pAdapter = new PostAdapter(this, posts);
        ListView listView1 = findViewById(R.id.posts);
        listView1.setAdapter(pAdapter);*/



    };

    protected void onPause(){
        super.onPause();
    };

    protected void onStop(){
        super.onStop();};

    protected void onDestroy(){
        super.onDestroy();
    };

/*    public void btnStartsCreatePostActivity(View view) {
        startActivity(new Intent(this, CreatePostActivity.class));
    }

    public void btnStartsReadPostActivity(View view) {
        startActivity(new Intent(this, ReadPostActivity.class));
    }

    public void btnStartsSettingsActivity(View view) {
        startActivity(new Intent(this, SettingsAcitivity.class));
    }*/

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] allColumns = { MyDatabaseHelper.KEY_ROWID,
                MyDatabaseHelper.KEY_TITLE, MyDatabaseHelper.KEY_DESCRIPTION, MyDatabaseHelper.KEY_DATE,MyDatabaseHelper.KEY_LOCATION };

        CursorLoader cursor = new CursorLoader(this, ContentProvider.CONTENT_URI_POST,
                allColumns, null, null, null);

        return cursor;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        dataAdapter.swapCursor(data);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        dataAdapter.swapCursor(null);
    }



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

    public void sortByDate(){
        Collections.sort(posts, new Comparator<Post>() {
            @Override
            public int compare(Post post, Post t1) {
                return post.getDate().compareTo(t1.getDate()) ;
            }
        });
    }

    public void sortPostsByPopularity(){

        Collections.sort(posts, new Comparator< Post >() {
            @Override
            public int compare(Post post, Post t1) {
                return   post.getPopularity() - t1.getPopularity();
            }


        });}


}
