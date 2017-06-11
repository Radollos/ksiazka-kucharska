package com.example.klaudia.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class Favourites extends AppCompatActivity {

    private String[] favouritesTitles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadFavouritesTitles();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
    }

    private void loadFavouritesTitles(){
        favouritesTitles = (getApplicationContext().fileList());
    }

    public void getFavourite(String title){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(title + ".data"));
            Recipe favourite = (Recipe)ois.readObject();
            ois.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error. Cannot load favourite recipe.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    // TODO: przniesc metode do onClicku przycisku w widoku przepisu
    public void addFavourite(Recipe recipe){

        int i = 0;
        String[] listOfFavourites = getApplicationContext().fileList();
        while(i<listOfFavourites.length){
            if(listOfFavourites[i] == recipe.getTitle()){
                Toast.makeText(this, "Recipe already in favourites", Toast.LENGTH_LONG);
                return;
            }
            i++;
        }

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(recipe.getTitle() + ".data"));
            oos.writeObject(recipe);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, recipe.getTitle() + "  added to Favourites", Toast.LENGTH_SHORT).show();
    }
}
