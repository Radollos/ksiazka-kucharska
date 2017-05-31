package com.example.klaudia.myapplication;

/**
 * Created by Admin on 2017-05-09.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;


import java.lang.reflect.Type;

import java.util.HashSet;
import java.util.Set;

public class SharedPreference {

    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String PREFS_KEY = "AOP_PREFS_String";

   // Activity context = this;


    public SharedPreference() {
        super();
    }

//json objects
    // objects





    public void saveNote(Context context, String text) {

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Editor editor = settings.edit();
        // Get existing notes
        Set<String> notes = getNotes(context); // this.context
        // Add new note to existing notes
        notes.add(text);
        // Store notes to SharedPreferences
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            editor.putStringSet(PREFS_KEY, notes);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        }
    }

    public Set<String> getNotes(Context context) {

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Get notes
        Set<String> notes = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            notes = settings.getStringSet(PREFS_KEY, new HashSet<String>());
        }

        return notes;
    }

    // ---------------------------------------------------
    //                  OTHER  METHODS
    // ---------------------------------------------------

    public void save(Context context, String text) {
        SharedPreferences settings;
        Editor editor;

        settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.putString(PREFS_KEY, text);

        editor.commit();

    }



    public boolean saveArray(String[] array, String arrayName, Context mContext) {
        SharedPreferences settings = mContext.getSharedPreferences("preferencename", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(arrayName +"_size", array.length);
        for(int i=0;i<array.length;i++)
            editor.putString(arrayName + "_" + i, array[i]);
        return editor.commit();
    }


    public String getValue(Context context) {
        SharedPreferences settings;
        String text;

        settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_KEY, null);
        return text;
    }

    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        Editor editor;

        settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit(); //APPLY()
    }

    public void removeValue(Context context) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(PREFS_KEY);
        editor.commit();
    }
}