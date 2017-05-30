package com.example.klaudia.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Klaudia on 2017-04-24.
 */

//klasa sluzaca do laczenia sie z baza
public class ConnectionManager extends AsyncTask<String, Void, HttpResponse<JsonNode>>
{
    JsonNode jsonResponse;
    public ConnectionManager()
    {
        jsonResponse = null;
    }


    @Override
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


    protected void onPostExecute(HttpResponse<JsonNode> response)
    {
        try
        {
            jsonResponse = response.getBody();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }








}
