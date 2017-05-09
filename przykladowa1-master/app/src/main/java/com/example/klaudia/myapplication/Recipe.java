package com.example.klaudia.myapplication;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Klaudia on 2017-04-09.
 */

public class Recipe
{
    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;
    private boolean dairyFree;
    private boolean veryHealthy;
    private boolean cheap;
    private boolean veryPopular;
    private boolean sustainable;
    private int weightWatcherSmartPoints;
    private String gaps;
    private boolean lowFodmap;
    private boolean ketogenic;
    private boolean whole30;
    private int servings;
    private int preparationMinutes;
    private int cookingMinutes;
    private String sourceUrl;
    private String spoonacularSourceUrl;
    private int aggregateLikes;
    private int spoonacularScore;
    private int healthScore;
    private String creditText;
    private String sourceName;
    private double pricePerServing;
    private ArrayList<Ingredient> extendedIngredients;

    private int id;
    private String title;
    private int readyInMinutes;
    private String image;
    private String imageType;
    private ArrayList<String> cuisines;
    private ArrayList<String> dishTypes;
    private String instructions;
    //private ArrayList<InstructionStep> analyzedInstructions;

    private JSONObject json;

    public Recipe(JSONObject json) throws JSONException
    {
        this.json = json;
        vegetarian = json.getBoolean("vegetarian");
        vegan = json.getBoolean("vegan");
        glutenFree = json.getBoolean("glutenFree");
        dairyFree = json.getBoolean("dairyFree");
        veryHealthy = json.getBoolean("veryHealthy");
        cheap = json.getBoolean("cheap");
        veryPopular = json.getBoolean("veryPopular");
        sustainable = json.getBoolean("sustainable");
        weightWatcherSmartPoints = json.getInt("weightWatcherSmartPoints");
        gaps = json.getString("gaps");
        lowFodmap = json.getBoolean("lowFodmap");
        ketogenic = json.getBoolean("ketogenic");
        whole30 = json.getBoolean("whole30");
        servings = json.getInt("servings");
        preparationMinutes = json.has("preparationMinutes") ? json.getInt("preparationMinutes") : 0;
        cookingMinutes = json.has("cookingMinutes") ? json.getInt("cookingMinutes") : 0;
        sourceUrl = json.getString("sourceUrl");
        spoonacularSourceUrl = json.getString("spoonacularSourceUrl");
        aggregateLikes = json.getInt("aggregateLikes");
        spoonacularScore = json.getInt("spoonacularScore");
        healthScore = json.getInt("healthScore");
        creditText = json.has("creditText") ? json.getString("creditText") : "";
        sourceName = json.has("sourceName") ? json.getString("sourceName") : "";
        pricePerServing = json.getDouble("pricePerServing");


        JSONArray ingredientsJSON = json.getJSONArray("extendedIngredients");
        extendedIngredients = new ArrayList<Ingredient>(ingredientsJSON.length());
        for (int i = 0; i < ingredientsJSON.length(); i++)
        {
            JSONObject ingredient = ingredientsJSON.getJSONObject(i);
            int id = ingredient.getInt("id");
            String aisle = ingredient.getString("aisle");
            String image = ingredient.getString("image");
            String name = ingredient.getString("name");
            double amount = ingredient.getDouble("amount");
            String unit = ingredient.getString("unit");
            String unitShort = ingredient.getString("unitShort");
            String unitLong = ingredient.getString("unitLong");
            String originalString = ingredient.getString("originalString");


            JSONArray metaTmp = ingredient.getJSONArray("metaInformation");
            ArrayList<String> metaInformation = new ArrayList<>(metaTmp.length());
            for(int j = 0; j < metaTmp.length(); j++) {
                metaInformation.add(metaTmp.getString(j));
            }

            Ingredient newIngredient = new Ingredient(id, aisle, image, name, amount, unit, unitShort, unitLong, originalString, metaInformation);
            extendedIngredients.add(newIngredient);
        }

        id = json.getInt("id");
        title = json.getString("title");
        readyInMinutes = json.getInt("readyInMinutes");
        image = json.getString("image");
        imageType = json.getString("imageType");

        JSONArray cuisinesTemp = json.getJSONArray("cuisines");
        cuisines = new ArrayList<>(cuisinesTemp.length());
        for(int i=0; i<cuisinesTemp.length(); i++) {
            cuisines.add(cuisinesTemp.getString(i));
        }

        JSONArray dishTypesTemp = json.getJSONArray("dishTypes");
        cuisines = new ArrayList<>(dishTypesTemp.length());
        for(int i=0; i<dishTypesTemp.length(); i++) {
            dishTypes.add(dishTypesTemp.getString(i));
        }

        instructions = json.getString("instructions");


    }

    public void show()
    {

    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public boolean isDairyFree() {
        return dairyFree;
    }

    public boolean isVeryHealthy() {
        return veryHealthy;
    }

    public boolean isCheap() {
        return cheap;
    }

    public boolean isVeryPopular() {
        return veryPopular;
    }

    public boolean isSustainable() {
        return sustainable;
    }

    public int getWeightWatcherSmartPoints() {
        return weightWatcherSmartPoints;
    }

    public String getGaps() {
        return gaps;
    }

    public boolean isLowFodmap() {
        return lowFodmap;
    }

    public boolean isKetogenic() {
        return ketogenic;
    }

    public boolean isWhole30() {
        return whole30;
    }

    public int getServings() {
        return servings;
    }

    public int getPreparationMinutes() {
        return preparationMinutes;
    }

    public int getCookingMinutes() {
        return cookingMinutes;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getSpoonacularSourceUrl() {
        return spoonacularSourceUrl;
    }

    public int getAggregateLikes() {
        return aggregateLikes;
    }

    public int getSpoonacularScore() {
        return spoonacularScore;
    }

    public int getHealthScore() {
        return healthScore;
    }

    public String getCreditText() {
        return creditText;
    }

    public String getSourceName() {
        return sourceName;
    }

    public double getPricePerServing() {
        return pricePerServing;
    }

    public ArrayList<Ingredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public String getImage() {
        return image;
    }

    public String getImageType() {
        return imageType;
    }

    public ArrayList<String> getCuisines() {
        return cuisines;
    }

    public ArrayList<String> getDishTypes() {
        return dishTypes;
    }

    public String getInstructions() {
        return instructions;
    }
}
