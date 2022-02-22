package com.example.finalproduct;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class TypeProvider extends ContentProvider {

    SQLiteDatabase myDB;

    private static final String DB_NAME = "goods";
    private static final String DB_TABLE = "type";
    private static final int DB_VER = 1;

    public TypeProvider() {
    }

    public static final String AUTHORITY = "com.demo.contentprovider.provider";
    public static final String CONTENT_PATH = "";
    public static final Uri CONTENT_URI = Uri.parse("content://" +AUTHORITY+"/"+DB_TABLE);

    static int TYPE = 1;
    static int TYPE_ROW = 2;

    static UriMatcher myUri = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        //access the whole table
        myUri.addURI(AUTHORITY,"type",TYPE);
        //access specific row
        myUri.addURI(AUTHORITY,"type/#",TYPE_ROW);
    }

    private class MyDatabase extends SQLiteOpenHelper {

        public MyDatabase(Context ct){
            super(ct,DB_NAME,null,DB_VER);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            System.out.println("Type table was created");
            sqLiteDatabase.execSQL("create table if not exists " + DB_TABLE + " " +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

        @Override
        public void onOpen(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table if not exists " + DB_TABLE + " " +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL)");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        long row = myDB.insert(DB_TABLE,null,values);
        if(row > 0) {
            uri = ContentUris.withAppendedId(CONTENT_URI,row);
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return uri;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        MyDatabase myHelper = new MyDatabase(getContext());
        myDB = myHelper.getWritableDatabase();
        if(myDB != null){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteQueryBuilder myQuery = new SQLiteQueryBuilder();
        myQuery.setTables(DB_TABLE);

        Cursor cr = myQuery.query(myDB,null,null,null,null,null,"_id");
        cr.setNotificationUri((getContext().getContentResolver()),uri);
        return cr;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}