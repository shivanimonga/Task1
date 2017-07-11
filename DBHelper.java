package com.example.shivani.shivani;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by shivani on 7/7/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static final String TABLE_NAME = "entry";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_URL = "url";
    public static final String COLUMN_NAME_THUMBNAIL = "thumbnailUrl";


    public static final String DATABASE_NAME = "DBReader.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_NAME_ID + " INTEGER ," +
                    COLUMN_NAME_TITLE + " TEXT," +
                    COLUMN_NAME_URL + " TEXT," +
                    COLUMN_NAME_THUMBNAIL + ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);

        Log.d("db created", "a");

    }

    public void addRecords(ArrayList<Users> userList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.d("userlist length", String.valueOf(userList.size()));


        for (int i = 0; i < userList.size(); i++) {
            values.put(COLUMN_NAME_ID, userList.get(i).getId());
            values.put(COLUMN_NAME_TITLE, userList.get(i).getTitle());
            values.put(COLUMN_NAME_URL, userList.get(i).getUrl());
            values.put(COLUMN_NAME_THUMBNAIL, userList.get(i).getthumburl());
            long newRowId = db.insert(TABLE_NAME, null, values);
            Log.d("row id", String.valueOf(newRowId));
        }

        //  Log.d("row id", String.valueOf(newRowId));
        db.close();
    }

    public ArrayList<Users> getRecords() {

        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        ArrayList<Users> userList = new ArrayList<>();
        int count = 0;
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Users user = new Users();
                user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID)));
                user.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TITLE)));
                user.setUrl(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_URL)));
                user.setThumbnailUrl(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_THUMBNAIL)));
                userList.add(user);
            }
        }
        return userList;
    }

    public void deleteItem(Users user) {

        Log.d("MySQLiteHelper", "in deleteItem");
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_ID + "=" + user.getId());
        } catch (Exception e) {
            Log.e("MySQLiteHelper", e.getMessage());
        }
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
