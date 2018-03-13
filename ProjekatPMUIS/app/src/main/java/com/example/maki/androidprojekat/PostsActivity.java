package com.example.maki.androidprojekat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PostsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
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

    public void btnStartsCreatePostActivity(View view) {
        startActivity(new Intent(this, CreatePostActivity.class));
    }

    public void btnStartsReadPostActivity(View view) {
        startActivity(new Intent(this, ReadPostActivity.class));
    }

    public void btnStartsSettingsActivity(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}
