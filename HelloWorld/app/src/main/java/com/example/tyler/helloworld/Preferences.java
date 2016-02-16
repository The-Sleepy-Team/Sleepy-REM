package com.example.tyler.helloworld;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tyler on 2/15/2016.
 */
public class Preferences {
    public static final String MY_PREF = "MyPreferences";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Preferences(Context context){
        this.sharedPreferences = context.getSharedPreferences(MY_PREF, 0);
    }

    public void set(String key, String value){
        this.editor.putString(key, value);
        this.editor.commit();
    }

    public String get(String key){
        return this.sharedPreferences.getString(key,"AUTO");
    }

    public void clear(String key){
        this.editor.remove(key);
        this.editor.commit();
    }

    public void clear(){
        this.editor.clear();
        this.editor.commit();
    }
}
