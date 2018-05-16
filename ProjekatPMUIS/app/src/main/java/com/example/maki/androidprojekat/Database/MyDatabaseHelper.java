package com.example.maki.androidprojekat.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Maki on 5/14/2018.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "androidApp";
    private static final int DATABASE_VERSION = 1;

    public static final String KEY_ROWID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DATE = "date";
    public static final String KEY_LOCATION = "location";

    public static final String POST_TABLE = "Post";

    private static final String DATABASE_CREATE = "create table "
            + POST_TABLE + "("
            + KEY_ROWID  + " integer primary key autoincrement , "
            + KEY_TITLE + " text, "
            + KEY_DESCRIPTION + " text, "
            + KEY_DATE + " text, "
            + KEY_LOCATION + " text "
            + ")";

    public MyDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + POST_TABLE);
        onCreate(db);

    }
}
