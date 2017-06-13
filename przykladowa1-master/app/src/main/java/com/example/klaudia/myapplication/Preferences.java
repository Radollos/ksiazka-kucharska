package com.example.klaudia.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Preferences extends AppCompatActivity
{
    GridLayout gridLayout;
    Button save;
    EditText cuisine, diet, excludeIngredients, intolerances, minCalories, maxCalories,
            minCarbs, maxCarbs, minFat, maxFat, minProteins, maxProteins;
    HashMap<String, String> hashMapPreferences;
    Searcher searcher;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        save = (Button) findViewById(R.id.buttonPreferences);
        cuisine = (EditText) findViewById(R.id.cuisineET);
        diet = (EditText) findViewById(R.id.dietET);
        excludeIngredients = (EditText) findViewById(R.id.excludeIngredientsET);
        intolerances = (EditText) findViewById(R.id.intolerancesET);
        minCalories = (EditText) findViewById(R.id.minCaloriesET);
        maxCalories = (EditText) findViewById(R.id.maxCaloriesET);
        minCarbs = (EditText) findViewById(R.id.minCarbsET);
        maxCarbs = (EditText) findViewById(R.id.maxCarbsET);
        minFat = (EditText) findViewById(R.id.minFatET);
        maxFat = (EditText) findViewById(R.id.maxFatET);
        minProteins = (EditText) findViewById(R.id.minProteinsET);
        maxProteins = (EditText) findViewById(R.id.maxProteinsET);

        hashMapPreferences = new HashMap<String, String>();
        gridLayout = (GridLayout) findViewById(R.id.gridLayoutPreferences);
        searcher = ((MyApplication) this.getApplication()).getSearcher();

        checkPreferences();

        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                for (int i = 0; i < gridLayout.getChildCount(); i++)
                {
                    if ((gridLayout.getChildAt(i) instanceof EditText) && !((EditText) gridLayout.getChildAt(i)).getText().toString().matches(""))
                    {
                        String reversedKey = new StringBuffer(gridLayout.getChildAt(i).toString()).reverse().toString();
                        String key = new StringBuffer(reversedKey.substring(3, reversedKey.indexOf('/'))).reverse().toString();
                        hashMapPreferences.put(key, ((EditText) gridLayout.getChildAt(i)).getText().toString());
                    }

                }

                File file = new File(getApplicationContext().getFilesDir(), "Preferences");
                try
                {
                    file.createNewFile();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                try
                {
                    ObjectOutputStream oos = new ObjectOutputStream(openFileOutput("Preferences", Context.MODE_PRIVATE));
                    oos.writeObject(hashMapPreferences);
                    oos.close();
                    searcher.setPreferences(hashMapPreferences);
                    String[] tmp = getApplicationContext().fileList();
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
        });
    }


    public void checkPreferences()
    {
        HashMap<String, String> prefTmp = ((MyApplication) this.getApplication()).getPreferences();
        if (prefTmp != null)
        {
            String [] keys = prefTmp.keySet().toArray(new String [prefTmp.size()]);
            String [] values = prefTmp.values().toArray(new String[prefTmp.size()]);

            for (int i = 0; i < keys.length; i++)
            {
                String key = keys[i];
                String value = values[i];

                switch (key)
                {
                    case "cuisine" :
                        cuisine.setText(value);
                        break;

                    case "diet" :
                        diet.setText(value);
                        break;

                    case "excludeIngredients" :
                        excludeIngredients.setText(value);
                        break;

                    case "intolerances" :
                        intolerances.setText(value);
                        break;

                    case "minCalories" :
                        minCalories.setText(value);
                        break;

                    case "maxCalories" :
                        maxCalories.setText(value);
                        break;

                    case "minFat" :
                        minFat.setText(value);
                        break;

                    case "maxFat" :
                        maxFat.setText(value);
                        break;

                    case "minProteints" :
                        minProteins.setText(value);
                        break;

                    case "maxProteints" :
                        maxProteins.setText(value);
                        break;
                }
            }
        }
    }
}
