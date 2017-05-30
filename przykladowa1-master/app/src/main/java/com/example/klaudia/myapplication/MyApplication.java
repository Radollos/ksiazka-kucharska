package com.example.klaudia.myapplication;

import android.app.Application;
import android.content.Context;

import org.json.JSONArray;

/**
 * Created by Klaudia on 2017-05-13.
 */

public class MyApplication extends Application
{
    Searcher searcher = new Searcher();

    public void onCreate()
    {
        super.onCreate();
    }
    public Searcher getSearcher()
    {
        return searcher;
    }

}
