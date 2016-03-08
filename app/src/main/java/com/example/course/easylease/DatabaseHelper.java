package com.example.course.easylease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by heyub_000 on 2/23/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String USER_TABLE_NAME = "UserTable";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER_NAME = "User_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PHONE_NUMBER = "Phone_number";

    public static final String DATABASE_NAME = "EasyleaseDB.db";
    public static final int DATABASE_VERSION = 1;

    private static final String USER_TABLE_CREATE
            = "CREATE TABLE " + USER_TABLE_NAME
            + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_NAME + " TEXT UNIQUE, "
            + COLUMN_EMAIL + " TEXT NOT NULL, "
            + COLUMN_PASSWORD + " TEXT, "
            + COLUMN_PHONE_NUMBER + " TEXT" + ");";

    private static DatabaseHelper dbHelper;

    //TODO: HOUSE TABLE
    //private static final String CREATE_HOUSES_TABLE="CREATE TABLE" +HOUSES+
    //Use singleton to ensure there is only one db
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(context);
            return dbHelper;
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
    }

}
