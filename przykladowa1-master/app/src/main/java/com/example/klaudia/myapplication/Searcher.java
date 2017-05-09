package com.example.klaudia.myapplication;

import android.os.AsyncTask;

import com.mashape.unirest.http.JsonNode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by Klaudia on 2017-04-24.
 */

public class Searcher
{
    ConnectionManager cm = new ConnectionManager();
    Recipe [] recipes;


    public String changeTextToRequest(String text)
    {
        String request = text.replace(' ', '+').replace(",", "%2C");
        return request;
    }


    public Recipe getRecipeById(String id)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/information?includeNutrition=false";
        JsonNode response = null;
        Recipe result = null;
        try
        {
            response = (JsonNode) new ConnectionManager().execute(request).get().getBody();
            result = new Recipe(response.getObject());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

       return result;
    }


    public JSONArray searchRecipes(String request, String jsonArrayName)
    {
        JsonNode response = null;
        try
        {
            response = (JsonNode) new ConnectionManager().execute(request).get().getBody();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        JSONArray array = null;

        try
        {
            if (jsonArrayName != "")
                array = response.getObject().getJSONArray(jsonArrayName);

            else
                array = response.getArray();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return array;
    }


    public Recipe [] tagsSearch (String text)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/random?limitLicense=false&number=10&tags=" + changeTextToRequest(text);
        try
        {
            JSONArray array = searchRecipes(request, "recipes");
            recipes = new Recipe[array.length()];

            for (int i = 0; i < array.length(); i++)
            {
                Recipe recipe = new Recipe(array.getJSONObject(i));
                recipes[i] = recipe;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return recipes;
    }



    public Recipe [] ingredientsSearch(String text)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?fillIngredients=false&ingredients=" + changeTextToRequest(text) +  "&limitLicense=false&number=10";
        try
        {
            JSONArray array = searchRecipes(request, "");
            recipes = new Recipe[array.length()];
            for (int i = 0; i < array.length(); i++)
            {
                Recipe recipe = getRecipeById(array.getJSONObject(i).getString("id"));
                recipes[i] = recipe;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return recipes;

    }




//    public Recipe idRecipeSearch(String id)
//    {
//        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id;
//        JSONArray recipe = searchRecipes(request, "");
//        return
//
//    }

    public String typeSearch(String text)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/serach?type=" + changeTextToRequest(text);
        return request;
    }


    public String dietSearch(String text)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/serach?diet=" + changeTextToRequest(text);
        return request;
    }


    public String cuisineSearch(String text)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/serach?cuisine=" + changeTextToRequest(text);
        return request;
    }

}
