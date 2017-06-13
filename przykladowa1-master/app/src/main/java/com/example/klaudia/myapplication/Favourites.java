package com.example.klaudia.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Vector;

public class Favourites extends AppCompatActivity {

    private Vector<String> favouritesTitles;
    ListView favouritesTitlesList;
    FavouritesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        loadFavouritesTitles();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        favouritesTitlesList = (ListView) findViewById(R.id.favourite_list);
        adapter = new FavouritesListAdapter(this, favouritesTitles);
        favouritesTitlesList.setAdapter(adapter);
    }

    private void loadFavouritesTitles(){
        favouritesTitles = new Vector<String>();
        String[] tmp = getApplicationContext().fileList();
        for(int i=0; i<tmp.length; i++){
            favouritesTitles.add(tmp[i]);
        }
    }

    public void getFavourite(String title){
        try {
            ObjectInputStream ois = new ObjectInputStream(openFileInput(title));
            Recipe favourite = (Recipe)ois.readObject();
            ois.close();
            Intent intent = new Intent(getApplicationContext(), RecipeView.class);
            JSONObject recipeJSONObject = favourite.getJson();
            intent.putExtra("recipe", recipeJSONObject.toString());
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Error. Cannot load favourite recipe.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    public void deleteFavourite(String title){
        deleteFile(title);
        loadFavouritesTitles();
        adapter.setFavouritesTitles(favouritesTitles);
        Toast.makeText(this, title + " removed from Favourites", Toast.LENGTH_SHORT).show();
    }

}
