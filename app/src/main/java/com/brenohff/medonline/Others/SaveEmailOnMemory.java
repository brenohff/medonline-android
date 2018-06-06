package com.brenohff.medonline.Others;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by breno on 31/05/2017.
 */

public class SaveEmailOnMemory {

    public static final String PREFS_NAME = "LoadedBefore";

    private String email;

    public SaveEmailOnMemory(String email){
        this.email = email;
    }

    //GET STATUS

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void saveEmail(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString("email", email);

        editor.apply();
    }

    public static SaveEmailOnMemory loadEmail(Context context) {

        SharedPreferences userPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String status = userPrefs.getString("email", null);

        if (status == null){
            return null;
        } else {
            return new SaveEmailOnMemory(status);
        }
    }

    public static void removeEmail(Context context) {
        SharedPreferences userPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPrefs.edit();
        editor.remove("email");
        editor.apply();
    }

}
