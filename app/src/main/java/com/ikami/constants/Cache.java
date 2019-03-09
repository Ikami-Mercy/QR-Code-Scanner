package com.ikami.constants;

import android.content.Context;
import android.content.SharedPreferences;

import com.ikami.pojo.User;

public class Cache {
    public static void save(Context ctx, String MY_PREFS_NAME, String username){
        SharedPreferences.Editor editor = ctx.getSharedPreferences(MY_PREFS_NAME, ctx.MODE_PRIVATE).edit();
        editor.putString("username", username);
        editor.apply();
    }

    public static String getSessionUser(Context ctx,String MY_PREFS_NAME,String key){
        SharedPreferences prefs = ctx.getSharedPreferences(MY_PREFS_NAME, ctx.MODE_PRIVATE);
        String restoredText = prefs.getString(key, "");
        String username = "";
        if (restoredText != null) {
            username = prefs.getString(key, "No name defined");//"No name defined" is the default value.
        }
        return username;
    }
}
