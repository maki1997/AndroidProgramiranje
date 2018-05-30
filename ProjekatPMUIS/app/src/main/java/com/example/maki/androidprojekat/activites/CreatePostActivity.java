package com.example.maki.androidprojekat.activites;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import java.util.List;
import java.util.Locale;


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
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double lon;
    private double lat;

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
                helperDatabaseRead = new HelperDatabaseRead();
                usernameND = (TextView) findViewById(R.id.usernameDrawer);
                nameND = (TextView) findViewById(R.id.nameDrawer);
                User u=null;
                for(User uu:helperDatabaseRead.loadUsersFromDatabase(CreatePostActivity.this)){
                    if(uu.getId() == idUser){
                        u=uu;
                    }
                }
                usernameND.setText(u.getUsername());
                nameND.setText(u.getName());
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



        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Log.d("Location:",location.toString());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
            return;
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lat=location.getLatitude();
            lon=location.getLongitude();
        }








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

        Post p =new Post(desc, title,null,u,getDateTime(),hereLocation(lat,lon),null,null,0,0);
        Log.e("title:",p.getTitle());


        helperDatabaseRead.insertPost(p,this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    public String hereLocation(double lat,double lon){
        String ourSity="";
        Geocoder geocoder=new Geocoder(CreatePostActivity.this, Locale.getDefault());
        List<Address> addressList = null;
        try {
            addressList=geocoder.getFromLocation(lat,lon,1);

        }catch (Exception e){
            e.printStackTrace();
        }
        if(addressList==null || addressList.size()==0){
            String s1=String.valueOf(lat);
            String s2=String.valueOf(lon);
            ourSity=s1 + "," + s2;
        }else{

            ourSity=addressList.get(0).getAddressLine(0);

        }
        return ourSity;


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
