package com.brenohff.medonline.Memory;

import android.app.Application;
import android.content.Context;

public class MOApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();

        MOApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MOApplication.context;
    }
}
