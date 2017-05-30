package com.example.klaudia.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.mashape.unirest.http.JsonNode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by Klaudia on 2017-04-24.
 */

public class Searcher
{
    private JSONArray recipesJSONArray;
    private LinkedHashMap<String, String> titleUrl;
    private static final String TAG = MainActivity.class.getSimpleName();


    public JSONObject getRecipeJSONObjectById(int id)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/information?includeNutrition=false";
        JsonNode response = null;
        JSONObject result = null;

        try
        {
            response = new ConnectionManager().execute(request).get().getBody();
            result = response.getObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }


    public void tagsSearch_TitlesUrls(String text)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/random?limitLicense=false&number=10&tags=" + changeTextToRequest(text);
        recipesJSONArray = getJsonArrayFromRequest(request, "recipes");
        getRecipesTitleUrlFromArray();
    }


    public void ingredientsSearch_TitlesImages(String text)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?fillIngredients=false&ingredients=" + changeTextToRequest(text) +  "&limitLicense=false&number=10";
        recipesJSONArray = getJsonArrayFromRequest(request, "");
        getRecipesTitleUrlFromArray();
    }

    public void similarRecipesSearch_Titles(int id)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/similar?number=10";
        recipesJSONArray = getJsonArrayFromRequest(request, "");
        getRecipesTitleUrlFromArray();
    }


    public void complexSearch_Titles(HashMap<String, String> nameValue)
    {
        String userInput = "";
        Iterator it = nameValue.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry) it.next();
            Object key = pair.getKey();
            Object value = pair.getValue();

            //Kropka sluzy do oddzielenia pary (klucz, wartosc)
            userInput += key.toString() + "=" + value + ".";
        }

        if (userInput.length() > 0 && userInput.charAt(userInput.length()-1) == '.')
            userInput = userInput.substring(0, userInput.length() - 1);

        userInput = userInput.replace('.', '&');
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/searchComplex?limitLicense=false&number=10&offset=10&ranking=1&" + changeTextToRequest(userInput);
        recipesJSONArray = getJsonArrayFromRequest(request, "results");
        getRecipesTitleUrlFromArray();
    }


    //metoda, ktora laczy sie z baza i zwraca nam JSONArray, ktory zawiera liste przepisow zapisanych za pomoca JSONa
    public JSONArray getJsonArrayFromRequest(String request, String jsonArrayName)
    {
        JsonNode response = null;
        try
        {
            response = new ConnectionManager().execute(request).get().getBody();
            //w bazie tablice z przepisami sa roznie zapisane - nazywaja sie "results" albo "recipes" albo nie maja nazwy. Po to jest zmienna jsonArrayName, ktora oznacza jak
            //zapisana jest tabela w JSONie. Inaczej wyciaga sie dane z tabeli, ktora ma nazwe i ktora jej nie ma
            if (jsonArrayName != "")
                recipesJSONArray = response.getObject().getJSONArray(jsonArrayName);

            else
                recipesJSONArray = response.getArray();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return recipesJSONArray;
    }


    public JSONObject getJSONObjectFromArray(int index)
    {
        JSONObject result = null;
        try
        {
            if(recipesJSONArray.getJSONObject(index).has("vegan"))
                result = recipesJSONArray.getJSONObject(index);

            else
            {
                int recipceIndex = recipesJSONArray.getJSONObject(index).getInt("id");
                result = getRecipeJSONObjectById(recipceIndex);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public void getRecipesTitleUrlFromArray()
    {
        titleUrl = new LinkedHashMap<String, String>();
        try
        {
            for (int i = 0; i < recipesJSONArray.length(); i++)
            {
                JSONObject recipe = recipesJSONArray.getJSONObject(i);
                String title = recipe.getString("title");
                String url = recipe.getString("image");
                titleUrl.put(title, url);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public LinkedHashMap<String, String> getHashMapURL()
    {
        return titleUrl;
    }

    //metoda zamieniajaca tekst wprowadzony przez uzytkownika do editTextu w taki sposob, aby mozna bylo go dolaczyc do requesta
    public String changeTextToRequest(String text)
    {
        String request = text.replace(' ', '+').replace(",", "%2C");
        return request;
    }


    //funkcja sprawdzajaca czy wszyskie wartosci wpisane do hashmapy maja poprawny format
    public boolean checkMapCorrectness(HashMap<String, String> nameValue)
    {
        boolean correct = true;
        for (Map.Entry pair : nameValue.entrySet())
        {
            if (!checkTypeCorrectness(pair.getKey().toString(), pair.getValue().toString()))
            {
                correct = false;
                break;
            }
        }
        return correct;
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
