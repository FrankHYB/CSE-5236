package com.example.course.easylease;

import android.content.Context;

/**
 * Created by yubin on 4/9/16.
 */
public class SingletonContext {
    private static Context context = null;

    public static synchronized Context getInstance(Context mCt){
        if(context == null){
            context = mCt;
        }
        return context;
    }
}
