package com.example.maki.androidprojekat.util;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.maki.androidprojekat.Database.ContentProvider;
import com.example.maki.androidprojekat.Database.MyDatabaseHelper;

/**
 * Created by Maki on 5/15/2018.
 */

public class Util {
    public static void initDB(Activity activity) {
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(activity);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        {
            ContentValues entry = new ContentValues();
            entry.put(MyDatabaseHelper.KEY_TITLE, "title 12");
            entry.put(MyDatabaseHelper.KEY_DESCRIPTION, "opis 1");
            entry.put(MyDatabaseHelper.KEY_DATE, "21.12.2012");
            entry.put(MyDatabaseHelper.KEY_LOCATION, "Smederevo");

            activity.getContentResolver().insert(ContentProvider.CONTENT_URI_POST, entry);

            entry = new ContentValues();
            entry.put(MyDatabaseHelper.KEY_TITLE, "title");
            entry.put(MyDatabaseHelper.KEY_DESCRIPTION, "opis 2");
            entry.put(MyDatabaseHelper.KEY_DATE, "12.12.2012");
            entry.put(MyDatabaseHelper.KEY_LOCATION, "Smederevo");

            activity.getContentResolver().insert(ContentProvider.CONTENT_URI_POST, entry);
        }

        db.close();
    }

}
