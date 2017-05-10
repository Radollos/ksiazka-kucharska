package com.example.klaudia.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mashape.unirest.http.JsonNode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by Klaudia on 2017-04-24.
 */

public class Searcher
{
    ConnectionManager cm = new ConnectionManager();
    Recipe [] recipes;
    Context context;


    public Searcher(Context context)
    {
        this.context = context;
    }

    public Searcher() {}
    public String changeTextToRequest(String text)
    {
        String request = text.replace(' ', '+').replace(",", "%2C");
        return request;
    }


    public Recipe getRecipeById(String id)
    {
        try
        {
            int ID = Integer.getInteger(id);
        }
        catch (Exception e)
        {
            Toast.makeText(context, "ID musi byc liczba calkowita", Toast.LENGTH_LONG).show();
        }

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



    public JSONArray getJsonArrayFromRequest(String request, String jsonArrayName)
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
        JSONArray array = getJsonArrayFromRequest(request, "recipes");
        getRecipesFromArray(array);
        return recipes;
    }



    public Recipe [] ingredientsSearch(String text)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?fillIngredients=false&ingredients=" + changeTextToRequest(text) +  "&limitLicense=false&number=10";
        JSONArray array = getJsonArrayFromRequest(request, "");
        getRecipesFromArray(array);
        return recipes;
    }


    public Recipe [] similarRecipesSearch(String id)
    {
        try
        {
            int ID = Integer.getInteger(id);
        }
        catch (Exception e)
        {
            Toast.makeText(context, "ID musi byc liczba calkowita", Toast.LENGTH_LONG).show();
        }

        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/similar?number=10";
        JSONArray array = getJsonArrayFromRequest(request, "");
        getRecipesFromArray(array);
        return recipes;
    }


    public Recipe [] complexSearch(HashMap<String, String> nameValue)
    {
        //trzeba dopisac spradzenie poprawnosc typow danych
        boolean correct = true;
        for (Map.Entry pair : nameValue.entrySet())
            if (!checkTypeCorrectness(pair.getKey().toString(), pair.getValue().toString()))
            {
                Toast.makeText(context, "Niepoprawny format danych " + pair.getKey(), Toast.LENGTH_LONG).show();
                correct = false;
                break;
            }

        if (correct)
        {
            String result = "";
            Iterator it = nameValue.entrySet().iterator();
            while (it.hasNext())
            {
                Map.Entry pair = (Map.Entry) it.next();
                Object key = pair.getKey();
                Object value = pair.getValue();

                if (value != null)
                    result += key.toString().substring(2, key.toString().length()) + "=" + value + ".";
            }

            if (result.length() > 0 && result.charAt(result.length()-1) == '.')
                result = result.substring(0, result.length() - 1);

            result = result.replace('.', '&');
            String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/searchComplex?addRecipeInformation=false&number=10&" + changeTextToRequest(result);
            JSONArray array = getJsonArrayFromRequest(request, "results");
            getRecipesFromArray(array);
            return recipes;
        }
        return null;
    }



    public void getRecipesFromArray(JSONArray array)
    {
        try
        {
            recipes = new Recipe[array.length()];
            for (int i = 0; i < array.length(); i++)
            {
                Recipe recipe = getRecipeById(array.getJSONObject(i).getString("id"));
                recipes[i] = recipe;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }



    public boolean checkTypeCorrectness(String key, String value)
    {
        boolean correct = true;
        char type = key.toString().charAt(0);
        switch (type)
        {
            case 'b' :
                correct = isBoolean(value);
                break;

            case 'i' :
                correct = isInteger(value);
                break;

            case 'd' :
                correct = isDouble(value);
                break;

            case 's':
                correct = !(isBoolean(value) || isInteger(value) || isDouble(value));
                break;

            default:
               break;
        }
        return correct;
    }


    public boolean isBoolean(String value)
    {
       return (value.equals("true") || value.equals("false"));
    }


    public boolean isInteger(String value)
    {
        try
        {
            int tmp = Integer.parseInt(value);
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }


    public boolean isDouble(String value)
    {
        try
        {
            double tmp = Double.parseDouble(value);
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }
}
