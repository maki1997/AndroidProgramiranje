package com.example.maki.androidprojekat;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

@SuppressWarnings("ConstantConditions")

public class CreatePostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
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
}
