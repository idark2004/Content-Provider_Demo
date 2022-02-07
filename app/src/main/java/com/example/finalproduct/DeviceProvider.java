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
import android.text.TextUtils;

public class DeviceProvider extends ContentProvider {
    SQLiteDatabase myDB;

    // Information of the DB
    private static final String DB_NAME = "goods";
    private static final String DB_TABLE = "device";
    private static final int DB_VER = 1;
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_TYPEID = "typeId";


    /* Anatomy of an URI: content://authority/path/id
        + "content://": the start of an URI
        + "authority" : the symbolic name of the entire provider (should be the package name)
        + "path"      : the virtual directory within the provider that acts as an identifier of the requested data
        + "id"        : optional, specifies the primary key of a record being requested */
    public static final String AUTHORITY = "com.demo.device.provider";
    public static final String CONTENT_PATH = "";
    public static final Uri CONTENT_URI = Uri.parse("content://" +AUTHORITY+"/"+DB_TABLE);

    // These variables are for the declaration of the UriMatcher class var below
    static final int DEVICE = 1;
    static final int DEVICE_ROW = 2;

    /* Match the content URI when accessing the table inside the CP, thus provide
    the access of the CP */
    /* Q: Why the UriMatcher has to be static?
       A: The UriMatcher is not dependent on the class object to get initialized */
    static UriMatcher myUri = new UriMatcher(UriMatcher.NO_MATCH);

    /* These URIs are similar to APIs, by calling these you can perform multiple functions
       (build a query, insert/delete a row, update a value, etc.) */
    /* When adding a new URI, (authority, path, code) are needed
       Authority: String - the authority to match
       Path: String - the path to match
            "*": wild card for text
            "#": wild card for numbers
       Code: int - the code that is returned when a URI is matched against the given components, must be pos */
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
            System.out.println("Device table was created!");
            sqLiteDatabase.execSQL("create table if not exists " + DB_TABLE + " " +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "quantity INTEGER NOT NULL, " +
                    "typeId INTEGER NOT NULL)");

            //query with "type" foreign key
//            sqLiteDatabase.execSQL("create table if not exists "+DB_TABLE+"" +
//                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    "name TEXT NOT NULL, " +
//                    "quantity INTEGER NOT NULL, " +
//                    "FOREIGN KEY (typeId) REFERENCES type(id)");
        }

        @Override
        public void onOpen(SQLiteDatabase sqLiteDatabase) {
            System.out.println("Device table was created!");
            sqLiteDatabase.execSQL("create table if not exists " + DB_TABLE + " " +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "quantity INTEGER NOT NULL, " +
                    "typeId INTEGER NOT NULL)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("create table if not exists " + DB_TABLE + " " +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "quantity INTEGER NOT NULL, " +
                    "typeId INTEGER NOT NULL)");
        }
    }

    // Should be soft delete (update ACTIVE row from T to F)
    // For the sake of the demo, this method will remove an entire row
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        // GetPathSegments will return a List which is debunked from the uri
        // At position 1 is the id within the uri
        String id = uri.getPathSegments().get(1);
        /* update (Uri uri, ContentValues values, String selection, String[] selectionArgs
            + uri: contains id
            + values: set of column_name/value pairs to update
            + selection: optional filter
            + selectionArgs: may be null
        */
        count = myDB.delete(DB_TABLE,
                KEY_ID
                    + "="
                    + id
                    + (!TextUtils.isEmpty(selection) ? "AND ("
                        + selection + ")" : ""), selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


    // Depends on the URI, different type of MIME will be returned
    @Override
    public String getType(Uri uri) {
        switch (myUri.match(uri)) {
            case DEVICE:
                return DeviceContract.MULTIPLE_RECORDS_MIME_TYPE;
            case DEVICE_ROW:
                return DeviceContract.SINGLE_RECORD_MIME_TYPE;
            default:
                // Another way is to throw an exception (recommend)
                return null;
        }
    }

    // Insert a new row
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long row = myDB.insert(DB_TABLE,null, values);
        // Indicates that a new row has been added
        if (row > 0) {
            // Update the URI with the position number of the new row
            uri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return uri;
    }

    // Create a database
    @Override
    public boolean onCreate() {
        MyDatabase myHelper = new MyDatabase(getContext());
        // This line will trigger the onCreate above, create a new database based on the query
        myDB = myHelper.getWritableDatabase();
        // If a database is available (both newly added or already created), return true
        if(myDB != null){
            return true;
        } else {
            return false;
        }
    }

    // Returns data
    // This method must return a cursor obj, or an Exception if fails
    /* Cursor class is an instance using which you can invoke methods that execute SQLite statements,
      fetch data from the result sets of the queries */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // SQLiteQueryBuilder class obj: give the access to build the query
        SQLiteQueryBuilder myQuery = new SQLiteQueryBuilder();
        myQuery.setTables(DB_TABLE);

        if (myUri.match(uri) == DEVICE_ROW) {
            myQuery.appendWhere(KEY_ID + "=" + uri.getPathSegments().get(1));
        }
        /* By creating this cursor, you can use Cursor implemented methods
           to access the content of the database in Activities */
        Cursor cr = myQuery.query(myDB,null,null,null,null,null,"_id");
        cr.setNotificationUri((getContext().getContentResolver()),uri);
        return cr;
    }

    // Same ContentValues as insert
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
        // GetPathSegments will return a List which is debunked from the uri
        // At position 1 is the id within the uri
        String id = uri.getPathSegments().get(1);
        // Explanation in the delete method
        count = myDB.update(DB_TABLE, values,
                KEY_ID
                        + "="
                        + id
                        + (!TextUtils.isEmpty(selection) ? "AND ("
                        + selection + ")" : ""), selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}