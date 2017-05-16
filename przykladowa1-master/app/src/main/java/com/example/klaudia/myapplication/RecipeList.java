package com.example.klaudia.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class RecipeList extends AppCompatActivity
{
    TextView title;
    TextView vegetarian;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        final Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle = intent.getExtras();
        JSONObject recipe = null;
        title = (TextView) findViewById(R.id.title);
        vegetarian = (TextView) findViewById(R.id.vegetarian);

        try
        {
            recipe = new JSONObject(bundle.getString("jsonObject"));
            title.setText(recipe.getString("title"));
            vegetarian.setText(recipe.getString("vegetarian"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
