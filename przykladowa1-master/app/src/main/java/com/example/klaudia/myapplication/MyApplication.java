package com.example.klaudia.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

/**
 * Created by Klaudia on 2017-05-13.
 */

public class MyApplication extends Application
{
    Searcher searcher = new Searcher();
    HashMap<String, String> preferences;

    public void onCreate()
    {
        super.onCreate();
    }
    public Searcher getSearcher()
    {
        return searcher;
    }


    public void getPreferencesFromFile()
    {
        try
        {
            String[] tmp = getApplicationContext().fileList();
            ObjectInputStream ois = new ObjectInputStream(openFileInput("Preferences"));
            preferences = (HashMap<String, String>) ois.readObject();
            ois.close();
        }
        catch (Exception e)
        {
            preferences = null;
        }
    }

    public HashMap<String, String> getPreferences()
    {
        getPreferencesFromFile();
        return preferences;
    }

    public void setPreferencesToSearcher()
    {
        searcher.setPreferences(preferences);
    }

}
