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

public class DeviceProvider extends ContentProvider {
    SQLiteDatabase myDB;

    private static final String DB_NAME = "goods";
    private static final String DB_TABLE = "device";
    private static final int DB_VER = 1;

    public static final String AUTHORITY = "com.demo.device.provider";
    public static final String CONTENT_PATH = "";
    public static final Uri CONTENT_URI = Uri.parse("content://" +AUTHORITY+"/"+DB_TABLE);

    static int DEVICE = 1;
    static int DEVICE_ROW = 2;

    static UriMatcher myUri = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        //access the whole table
        myUri.addURI(AUTHORITY,"device",DEVICE);
        //access specific row
        myUri.addURI(AUTHORITY,"device/#",DEVICE_ROW);
    }

    public DeviceProvider() {
    }

    private class MyDatabase extends SQLiteOpenHelper {
        public MyDatabase(Context ct){
            super(ct,DB_NAME,null,DB_VER);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL("create table if not exists " + DB_TABLE + " " +
                        "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +
                        "quantity INTEGER NOT NULL, " +
                        "typeId INTEGER NOT NULL)");
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }


            //query with "type" foreign key
//            sqLiteDatabase.execSQL("create table if not exists "+DB_TABLE+"" +
//                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    "name TEXT NOT NULL, " +
//                    "quantity INTEGER NOT NULL, " +
//                    "FOREIGN KEY (typeId) REFERENCES type(id)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

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
        MyDatabase myHelper = new MyDatabase(getContext());
        // This line will trigger the onCreate above
        myDB = myHelper.getWritableDatabase();
        // Return the result
        System.out.println("--------------- " + myDB != null);
        if(myDB != null){
            return true;
        } else {
            return false;
        }
    }

    // This method must return a cursor obj, or an Exception if fails
    /* Cursor class is an instance using which you can invoke methods that execute SQLite statements,
      fetch data from the result sets of the queries */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder myQuery = new SQLiteQueryBuilder();
        myQuery.setTables(DB_TABLE);

        /* By creating this cursor, you can use Cursor implemented methods
        to access the content of the database in Activities */
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