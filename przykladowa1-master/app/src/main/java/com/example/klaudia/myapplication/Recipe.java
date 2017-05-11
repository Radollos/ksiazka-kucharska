package com.example.klaudia.myapplication;

import java.util.ArrayList;
import java.util.Iterator;

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
    private ArrayList<InstructionStep> analyzedInstructions;

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
        extendedIngredients = new ArrayList<>(ingredientsJSON.length());
        for (int i = 0; i < ingredientsJSON.length(); i++)
        {
            JSONObject ingredient = ingredientsJSON.getJSONObject(i);
            int id = ingredient.has("id") ? ingredient.getInt("id") : 0;
            String aisle = ingredient.has("aisle") ? ingredient.getString("aisle") : "";
            String image = ingredient.has("image") ? ingredient.getString("image") : "";
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

        JSONArray cuisinesTmp = json.getJSONArray("cuisines");
        cuisines = new ArrayList<>(cuisinesTmp.length());
        for(int i = 0; i < cuisinesTmp.length(); i++) {
            cuisines.add(cuisinesTmp.getString(i));
        }

        JSONArray dishTypesTmp = json.getJSONArray("dishTypes");
        dishTypes = new ArrayList<>(dishTypesTmp.length());
        for(int i = 0; i < dishTypesTmp.length(); i++) {
            dishTypes.add(dishTypesTmp.getString(i));
        }

        instructions = json.getString("instructions");
        JSONArray analyzedInstructionsTmp = json.getJSONArray("analyzedInstructions");
        if (analyzedInstructionsTmp.length() > 0)
        {
            JSONArray stepsTmp = analyzedInstructionsTmp.getJSONObject(0).getJSONArray("steps");
            analyzedInstructions = new ArrayList<>(stepsTmp.length());
            for (int i = 0; i < stepsTmp.length(); i++)
            {
                JSONObject step = stepsTmp.getJSONObject(i);

                JSONArray stepIngTmp = step.getJSONArray("ingredients");
                ArrayList<Ingredient> stepIngredients = new ArrayList<>(stepIngTmp.length());
                for (int j = 0; j < stepIngTmp.length(); j++)
                    stepIngredients.add(getIngredientById(stepIngTmp.getJSONObject(j).getInt("id")));

                JSONArray stepEqTmp = step.getJSONArray("equipment");
                ArrayList<Equipment> stepEquipment = new ArrayList<>(stepEqTmp.length());
                for (int j = 0; j < stepEqTmp.length(); j++)
                {
                    JSONObject eqTmp = stepEqTmp.getJSONObject(j);
                    stepEquipment.add(new Equipment(eqTmp.getInt("id"), eqTmp.getString("name"), eqTmp.getString("image")));
                }
                analyzedInstructions.add(new InstructionStep(step.getInt("number"), step.getString("step"), stepIngredients, stepEquipment));
            }
        }
    }

    public Ingredient getIngredientById(int id){
        Ingredient temp = null;
        Iterator<Ingredient> it = extendedIngredients.listIterator();
        while(it.hasNext()){
            temp = it.next();
            if(temp.getId() == id) return temp;
        }
        return null;
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

    public ArrayList<InstructionStep> getAnalyzedInstructions() {
        return analyzedInstructions;
    }

    public JSONObject getJson() {
        return json;
    }
}
