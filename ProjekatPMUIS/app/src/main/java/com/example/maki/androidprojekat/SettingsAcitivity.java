package com.example.maki.androidprojekat;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

/**
 * Created by Maki on 5/13/2018.
 */

public class SettingsAcitivity extends PreferenceActivity {
    private DrawerLayout mDrawerLayout;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this,R.xml.preferences,false);

        addPreferencesFromResource(R.xml.preferences);


        /*getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PrefsFragment()).commit();*/
    }

    @Override
    protected  void onRestart() {
        super.onRestart();
    }
    @Override
    protected  void onResume() {
        super.onResume();
    }
    @Override
    protected  void onPause() {
        super.onPause();
    }
    @Override
    protected  void onStop() {
        super.onStop();
    }
    @Override
    protected  void onDestroy() {
        super.onDestroy();
    }

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    /*    public static class PrefsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);
        }*/


}
