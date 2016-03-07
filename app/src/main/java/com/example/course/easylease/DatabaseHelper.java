package com.example.course.easylease;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by heyub_000 on 2/23/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    private static DatabaseHelper dbHelper;
    private static final String DATABASE_NAME="EasyleaseDB";
    private static final int DATABASE_VERSION=1;
    private static final String USERS="UserTable";
    private static final String HOUSES="HouseTable";
    private static final String CREATE_USERS_TABLE="CREATE TABLE "+USERS+" ("+
                                                "User_name TEXT PRIMARY KEY, "+
                                                "Email TEXT, "+
                                                "Password TEXT, "+
                                                "Phone_number TEXT"+");";
    //TODO: HOUSE TABLE
    //private static final String CREATE_HOUSES_TABLE="CREATE TABLE" +HOUSES+
    //Use singleton to ensure there is only one db
    private DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    public static DatabaseHelper getInstance(Context context){
        if(dbHelper==null) {
            dbHelper = new DatabaseHelper(context);
            return dbHelper;
        }
        return dbHelper;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USERS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ CREATE_USERS_TABLE);
        onCreate(db);
    }

}
