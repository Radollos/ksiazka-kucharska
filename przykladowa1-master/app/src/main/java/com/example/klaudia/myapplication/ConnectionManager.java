package com.example.klaudia.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;


/**
 * Created by Klaudia on 2017-04-24.
 */

//klasa sluzaca do laczenia sie z baza
public class ConnectionManager extends AsyncTask<String, Integer, HttpResponse<JsonNode>>
{
    Activity context;

    public ConnectionManager(Activity context)
    {
        this.context = context;
    }

    protected void onPreExecute()
    {

    }


    @Override
    protected HttpResponse<JsonNode> doInBackground(String... params)
    {
        final String myRequest = params[0];
        HttpResponse<JsonNode> response = null;
        try
        {
            response = Unirest.get(myRequest)
                    .header("X-Mashape-Key", "OXOqW68hHLmshp14m3QjfcQLuoqop1WP587jsnETvuhAoakUUI")
                    .header("Accept", "application/json")
                    .asJson();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(HttpResponse<JsonNode> response)
    {
   //     dialog.dismiss();
    }



}