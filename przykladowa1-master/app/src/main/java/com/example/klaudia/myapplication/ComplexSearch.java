package com.example.klaudia.myapplication;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


import com.appyvet.rangebar.RangeBar;

import org.w3c.dom.Text;

import java.util.HashMap;

public class ComplexSearch extends AppCompatActivity
{
    GridLayout gridLayout;
    Button search;
    EditText query;
    EditText cuisine, diet, type, excludeIngredients, includeIngredients, intolerances, minCalories, maxCalories,
            minCarbs, maxCarbs, minFat, maxFat, minProteins, maxProteins;
    Searcher searcher;
    HashMap<String, String> hashMapsearch;


    RangeBar calories;
    RangeBar carbs;
    RangeBar fat;
    RangeBar proteins;

    int tabCalories[] = {150, 1500};
    int tabCarbs[] = {5, 100};
    int tabFat[] = {5, 100};
    int tabProteins[] = {5, 100};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complex_search);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_complex_search);
        toolbar.setTitle("Advanced search");
        setSupportActionBar(toolbar);

      //  toolbar.setTitle("Advanced search");
    //    toolbar.setLogo(R.drawable.ic_search_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        search = (Button) findViewById(R.id.buttonComplexSearch);
        cuisine = (EditText) findViewById(R.id.cuisineET);
        query = (EditText) findViewById(R.id.queryET);
        diet = (EditText) findViewById(R.id.dietET);
        type = (EditText) findViewById(R.id.typeET);
        excludeIngredients = (EditText) findViewById(R.id.excludeIngredientsET);
        includeIngredients = (EditText) findViewById(R.id.includeIngredientsET);
        intolerances = (EditText) findViewById(R.id.intolerancesET);
//        minCalories = (EditText) findViewById(R.id.minCaloriesET);
//        maxCalories = (EditText) findViewById(R.id.maxCaloriesET);
//        minCarbs = (EditText) findViewById(R.id.minCarbsET);
//        maxCarbs = (EditText) findViewById(R.id.maxCarbsET);
//        minFat = (EditText) findViewById(R.id.minFatET);
//        maxFat = (EditText) findViewById(R.id.maxFatET);
//        minProteins = (EditText) findViewById(R.id.minProteinsET);
//        maxProteins = (EditText) findViewById(R.id.maxProteinsET);





        hashMapsearch = new HashMap<String, String>();
        gridLayout = (GridLayout) findViewById(R.id.gridLayoutComplexSearch);

        calories = (RangeBar) findViewById(R.id.rangebarCal);
        carbs = (RangeBar) findViewById(R.id.rangebarCarbs);
        fat = (RangeBar) findViewById(R.id.rangebarFat);
        proteins = (RangeBar) findViewById(R.id.rangebarProteins);
        final TextView tvCalories = (TextView) findViewById(R.id.Calories);
        final TextView tvCarbs = (TextView) findViewById(R.id.Carbs);
        final TextView tvFat = (TextView) findViewById(R.id.Fat);
        final TextView tvProteins = (TextView) findViewById(R.id.Proteins);


        calories.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
                    tvCalories.setText("Calories: " + leftPinValue + " - " + rightPinValue + " kcal");
            }
        });

        carbs.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
                tvCarbs.setText("Carbs: " + leftPinValue + " - " + rightPinValue + " grams");

            }
        });

        fat.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
                tvFat.setText("Fat: " + leftPinValue + " - " + rightPinValue + " grams");

            }
        });

        proteins.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
                tvProteins.setText("Proteins: " + leftPinValue + " - " + rightPinValue + " grams");

            }
        });



        searcher = ((MyApplication) this.getApplication()).getSearcher();
        search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (query.getText().toString().length() == 0)
                {
                    query.requestFocus();
                    query.setError("Tags are required!");
                }


                else
                {
                    for (int i = 0; i < gridLayout.getChildCount(); i++)
                    {
                        if ((gridLayout.getChildAt(i) instanceof EditText) && !((EditText) gridLayout.getChildAt(i)).getText().toString().matches(""))
                        {


                            String reversedKey = new StringBuffer(gridLayout.getChildAt(i).toString()).reverse().toString();
                            String key = new StringBuffer(reversedKey.substring(3, reversedKey.indexOf('/'))).reverse().toString();
                            hashMapsearch.put(key, ((EditText) gridLayout.getChildAt(i)).getText().toString());
                        }

                    }

                    int calMin = Integer.parseInt(calories.getLeftPinValue()); //TO DO: WRZUCIC TYPA DO HASHMAPY - z tego co widzę, to mapa w stringach, wiec mozna olac parsowanie
                    int calMax = Integer.parseInt(calories.getRightPinValue());
                    int carbMin = Integer.parseInt(carbs.getLeftPinValue());
                    int carbMax = Integer.parseInt(carbs.getRightPinValue());
                    int fatMin = Integer.parseInt(fat.getLeftPinValue());
                    int fatMax = Integer.parseInt(fat.getRightPinValue());
                    int proMin = Integer.parseInt(proteins.getLeftPinValue());
                    int proMax = Integer.parseInt(proteins.getRightPinValue());

                    searcher.complexSearch(hashMapsearch);
                    Intent intent = new Intent(getApplicationContext(), RecipesListView.class);
                    intent.putExtra("tag", "Found: ");
                    startActivity(intent);
                }


            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:{
                finish();
                return true;}
        }

        return super.onOptionsItemSelected(item);

    }
}
