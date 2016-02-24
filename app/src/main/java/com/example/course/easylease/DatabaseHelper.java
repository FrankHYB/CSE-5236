package com.example.course.easylease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by heyub_000 on 2/23/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME="EasyleaseDB";
    private static final int DATABASE_VERSION=1;
    private static final String USERS="UserTable";
    private static final String HOUSES="HouseTable";
    private static final String CREATE_USERS_TABLE="CREATE TABLE "+USERS+"("+
                                                "User_name VARCHAR(255) PRIMARY KEY "+
                                                "Email VARCHAR(30) UNIQUE "+
                                                "Password VARCHAR(30) "+
                                                "Phone_number VARCHAR(14) "+")";
    //TODO: HOUSE TABLE
    //private static final String CREATE_HOUSES_TABLE="CREATE TABLE" +HOUSES+
    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USERS_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ CREATE_USERS_TABLE);
        onCreate(db);
    }

}
