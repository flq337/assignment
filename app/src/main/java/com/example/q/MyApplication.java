package com.example.q;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    /**
     * hold the global context , handy for some code(e.g. SQLiteHelper) need use global context
     */
    private static MyApplication instance;

    public MyApplication() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }

}
