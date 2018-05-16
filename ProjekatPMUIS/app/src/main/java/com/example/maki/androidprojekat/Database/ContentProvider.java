package com.example.maki.androidprojekat.Database;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by Maki on 5/14/2018.
 */

public class ContentProvider extends android.content.ContentProvider {

    /*private MyDatabaseHelper dbHelper;

    private static final int ALL_POSTS = 1;
    private static final int SINGLE_POST = 2;

    private static final String AUTHORITY = "com.example.maki.androidprojekat.Database.ContentProvider";

    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/posts");

    private static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"posts",ALL_POSTS);
        uriMatcher.addURI(AUTHORITY,"posts/#",SINGLE_POST);
    }






    @Override
    public boolean onCreate() {
        dbHelper = new MyDatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(PostDb.SQLITE_TABLE);

        switch(uriMatcher.match(uri)){
            case ALL_POSTS:
                break;
            case SINGLE_POST:
                String id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(PostDb.KEY_ROWID + "=" + id);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case ALL_POSTS:
                return "vnd.android.cursor.dir/com.example.maki.androidprojekat.Database.ContentProvider.posts";
            case SINGLE_POST:
                return "vnd.android.cursor.item/com.example.maki.androidprojekat.Database.ContentProvider.posts";
            default:
                throw new IllegalArgumentException("Unsupported URI " +uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)){
            case ALL_POSTS:
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " +uri);

        }
        long id = db.insert(PostDb.SQLITE_TABLE, null, values);
        getContext().getContentResolver().notifyChange(uri,null);
        return Uri.parse(CONTENT_URI + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALL_POSTS:
                break;
            case SINGLE_POST:
                String id = uri.getPathSegments().get(1);
                selection = PostDb.KEY_ROWID + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int deleteCount = db.delete(PostDb.SQLITE_TABLE, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return deleteCount;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALL_POSTS:
                break;
            case SINGLE_POST:
                String id = uri.getPathSegments().get(1);
                selection = PostDb.KEY_ROWID + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int updateCount = db.update(PostDb.SQLITE_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }

}*/


    private MyDatabaseHelper database;

    private static final int POST = 10;
    private static final int POST_ID = 20;

    private static final String AUTHORITY = "com.example.maki.androidprojekat";

    private static final String POST_PATH = "post";

    public static final Uri CONTENT_URI_POST = Uri.parse("content://" + AUTHORITY + "/" + POST_PATH);

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, POST_PATH, POST);
        sURIMatcher.addURI(AUTHORITY, POST_PATH + "/#", POST_ID);
    }

    @Override
    public boolean onCreate() {
        database = new MyDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exist
        //checkColumns(projection);
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case POST_ID:
                // Adding the ID to the original query
                queryBuilder.appendWhere(MyDatabaseHelper.KEY_ROWID + "="
                        + uri.getLastPathSegment());
                //$FALL-THROUGH$
            case POST:
                // Set the table
                queryBuilder.setTables(MyDatabaseHelper.POST_TABLE);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri retVal = null;
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case POST:
                id = sqlDB.insert(MyDatabaseHelper.POST_TABLE, null, values);
                retVal = Uri.parse(POST_PATH + "/" + id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return retVal;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        int rowsDeleted = 0;
        switch (uriType) {
            case POST:
                rowsDeleted = sqlDB.delete(MyDatabaseHelper.POST_TABLE,
                        selection,
                        selectionArgs);
                break;
            case POST_ID:
                String idCinema = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(MyDatabaseHelper.POST_TABLE,
                            MyDatabaseHelper.KEY_ROWID + "=" + idCinema,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(MyDatabaseHelper.POST_TABLE,
                            MyDatabaseHelper.KEY_ROWID + "=" + idCinema
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        int rowsUpdated = 0;
        switch (uriType) {
            case POST:
                rowsUpdated = sqlDB.update(MyDatabaseHelper.POST_TABLE,
                        values,
                        selection,
                        selectionArgs);
                break;
            case POST_ID:
                String idPost = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(MyDatabaseHelper.POST_TABLE,
                            values,
                            MyDatabaseHelper.KEY_ROWID + "=" + idPost,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(MyDatabaseHelper.POST_TABLE,
                            values,
                            MyDatabaseHelper.KEY_ROWID + "=" + idPost
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}

