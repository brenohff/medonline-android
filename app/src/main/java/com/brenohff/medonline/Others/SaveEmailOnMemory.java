package com.brenohff.medonline.Others;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by breno on 31/05/2017.
 */

public class SaveEmailOnMemory {

    public static final String PREFS_NAME = "LoadedBefore";

    private String email;
    private String tipo_usuario;

    public SaveEmailOnMemory(String email, String tipo_usuario) {
        this.email = email;
        this.tipo_usuario = tipo_usuario;
    }

    //region GETTERS AND SETTERS

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }
    //endregion

    public void saveEmail(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString("email", email);
        editor.putString("tipo_usuario", tipo_usuario);

        editor.apply();
    }

    public static SaveEmailOnMemory loadEmail(Context context) {

        SharedPreferences userPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String e = userPrefs.getString("email", null);
        String tp_user = userPrefs.getString("tipo_usuario", null);

        if (e == null || tp_user == null) {
            return null;
        } else {
            return new SaveEmailOnMemory(e, tp_user);
        }
    }

    public static void removeEmail(Context context) {
        SharedPreferences userPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPrefs.edit();
        editor.remove("email");
        editor.remove("tipo_usuario");
        editor.apply();
    }

}
