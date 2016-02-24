package com.example.course.easylease;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.*;

/**
 * Created by heyub_000 on 2/22/2016.
 */
public class UserDB {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private ContentValues values;
    private static final String Table_Name="UserTable";

    public UserDB(Context context){
        dbHelper=new DatabaseHelper(context);
        db=dbHelper.getWritableDatabase();
    }
    /* return -1 if the insert execution isn't successful*/
    public long insert(String[] info){
        values=new ContentValues();
        values.put("User_name",info[0]);
        values.put("Password",info[1]);
        values.put("Email",info[2]);
        values.put("Phone_number",info[3]);
        return this.db.insert("User",null,values);
    }
    public void delete(){
        //TODO: Implement delete
    }
    /*Used to check login password*/
    public List<String> queryPassword(String User_name,String password){
        List<String> userInfo=new ArrayList<String>();
        this.db=dbHelper.getReadableDatabase();
        Cursor cursor=this.db.query(Table_Name,new String[]{"User_name","Password"},"User_name = '"+User_name+"' AND Password = '"+password+"'",
                null,null,null,null);
        while(cursor.moveToNext()){
            userInfo.add(cursor.getString(cursor.getColumnIndex("User_name")));
            userInfo.add(cursor.getString(cursor.getColumnIndex("Password")));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return userInfo;
    }
    /* To get a user's email and phone number*/
    public List<String> queryEmailPhoneNum(String User_name){
        List<String> userInfo=new ArrayList<String>();
        this.db=dbHelper.getReadableDatabase();

        Cursor cursor=this.db.query(Table_Name,new String[]{"User_name","Email","Phone_number"},"User_name = '"+User_name+"'",
                null,null,null,null);
        while(cursor.moveToNext()){
            userInfo.add(cursor.getString(cursor.getColumnIndex("User_name")));
            userInfo.add(cursor.getString(cursor.getColumnIndex("Email")));
            userInfo.add(cursor.getString(cursor.getColumnIndex("Phone_number")));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return userInfo;
    }
    /*Using a map to bind correspoding user information*/
    public void UpdateInfo(String username,Map<String,String> value){
        ContentValues value1;
        for(Map.Entry<String,String> entry: value.entrySet()){
            if(entry.getKey().equals("Email")||entry.getKey().equals("Phone_number")){
                value1=new ContentValues();
                value1.put(entry.getKey(),entry.getValue());
                this.db.update(Table_Name,value1,"User_name="+username,null);
            }
        }
    }

}
