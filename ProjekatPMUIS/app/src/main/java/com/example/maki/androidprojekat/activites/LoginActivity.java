package com.example.maki.androidprojekat.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maki.androidprojekat.R;
import com.example.maki.androidprojekat.Database.DB_Helper;
import com.example.maki.androidprojekat.model.User;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText et;
    private DB_Helper helperDatabaseRead;
    private ArrayList<User> users = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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


    public void btnStartsPostsActivity(View view){
        et=(EditText)findViewById(R.id.username);
        String username=et.getText().toString();
        et=(EditText)findViewById(R.id.pass);
        String password=et.getText().toString();
        if(login(username,password) == true){
            Intent startPosts = new Intent(this,PostsActivity.class);
            startActivity(startPosts);
            finish();
        }else{
            Toast.makeText(this,"Wrong username and password combination!!!" ,Toast.LENGTH_LONG).show();

        }

    }
    public boolean login(String user,String pass){

        helperDatabaseRead = new DB_Helper();
        users = helperDatabaseRead.readUsers(this);
        for(User u:users){
            if (u.getUsername().equals(user))
                if (u.getPassword().equals(pass)) {
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("mPref",0);
                    SharedPreferences.Editor editor=pref.edit();
                    editor.putInt("id",u.getId());
                    editor.apply();
                    return true;
                }
        }
        return false;
    }


}
