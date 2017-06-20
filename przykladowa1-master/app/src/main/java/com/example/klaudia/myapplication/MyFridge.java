package com.example.klaudia.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ListIterator;
import java.util.Vector;

public class MyFridge extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener {

    private Vector<FridgeIngredient> ingredients;
    ListView recipeList;
    EditText newIngredientName;
    EditText newIngredientAmount;
    EditText newIngredientUnit;
    FridgeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ingredients = new Vector<>();
        loadRecipes();
        addIngredient("tomato",1,"l");
        addIngredient("spaghetti",1,"l");
        addIngredient("garlic",1,"l");
        addIngredient("cream",1,"l");

        setContentView(R.layout.activity_my_fridge);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recipeList = (ListView) findViewById(R.id.fridge_list);
        adapter = new FridgeListAdapter(this, ingredients);
        recipeList.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoticeDialog();
            }
        });

        newIngredientName = (EditText) findViewById(R.id.new_ingredient_name);
        newIngredientAmount = (EditText) findViewById(R.id.new_ingredient_amount);
        newIngredientUnit = (EditText) findViewById(R.id.new_ingredient_unit);

        Button addIngredientButton = (Button) findViewById(R.id.new_ingredient_button);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredient(newIngredientName.getText().toString(), Double.parseDouble(newIngredientAmount.getText().toString()), newIngredientUnit.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

        Button searchButton = (Button) findViewById(R.id.button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findRecipes();
            }
        });

    }

    private void loadRecipes(){
        //TODO: load recipes from memory

    }


    public void addIngredient(String ingredientName, double amount, String unit){
        FridgeIngredient tmp = containsIngredient(ingredientName);
        if(tmp == null) ingredients.add(new FridgeIngredient(ingredientName, amount, unit));
        else if(tmp.unit == unit) tmp.amount += amount;
    }

    public void removeIngredient(String ingredientName) {
        FridgeIngredient tmp = containsIngredient(ingredientName);
        if(tmp != null) ingredients.remove(tmp);
        return;
    }

    public void removeIngredient(int index) {
        if(index < ingredients.size())
            ingredients.remove(index);
        return;
    }

    public void findRecipes(){
        if(ingredients.isEmpty()){
            Toast.makeText(this, "Fridge is empty!", Toast.LENGTH_LONG).show();
            return;
        }
        ListIterator<FridgeIngredient> it = ingredients.listIterator();
        String request = it.next().ingredientName;
        while(it.hasNext())
            request+=(", " + it.next().ingredientName);
        Searcher searcher = ((MyApplication) this.getApplication()).getSearcher();
        searcher.ingredientsSearch(request);

        Intent i = new Intent(getApplicationContext(), RecipesListView.class);
        i.putExtra("tag", "MyFridge");
        startActivity(i);
    }

    public FridgeIngredient containsIngredient(String name) {
        ListIterator<FridgeIngredient> it = ingredients.listIterator();
        FridgeIngredient tmp;
        while (it.hasNext()) {
            tmp = it.next();
            if (tmp.ingredientName == name) return tmp;
        }

        return null;
    }

    public Vector<FridgeIngredient> getIngredients(){return ingredients;}



    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new NoticeDialogFragment();
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button

    }

    class FridgeIngredient{

        protected String ingredientName;
        protected double amount;
        protected  String unit;

        public FridgeIngredient(String ingredientName, double amount, String unit){
            this.ingredientName = ingredientName;
            this.amount = amount;
            this.unit = unit;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) { //powrót dla backbuttonu
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
