package com.example.klaudia.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;


/**
 * Created by Klaudia on 2017-04-24.
 */

public class Searcher
{
    private JSONArray recipesJSONArray;
    private LinkedHashMap<String, String> titleUrl;
    private HashMap<String, String> preferences;
    public Context context;
    public Activity activity;

    public void setContext(Context context)
    {
        this.context = context;
    }

    public void setActivity(Activity activity)
    {
        this.activity = activity;
    }

    public void setPreferences(HashMap<String, String> preferences)
    {
        this.preferences = preferences;
    }


    public String changePreferencesToQuery()
    {
        String query = "";
        if (preferences!=null)
        {
            Iterator it = preferences.entrySet().iterator();
            while (it.hasNext())
            {
                Map.Entry pair = (Map.Entry) it.next();
                Object key = pair.getKey();
                Object value = pair.getValue();
                //Kropka sluzy do oddzielenia pary (klucz, wartosc)
                query += key.toString() + "=" + value + ".";
            }

            if (query.length() > 0 && query.charAt(query.length()-1) == '.')
                query = query.substring(0, query.length() - 1);

            query = query.replace('.', '&');
        }
        return query == "" ? "" : changeTextToRequest(query) ;
    }


    public JSONObject getRecipeJSONObjectById(int id)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/information?includeNutrition=false";
        JsonNode response = null;
        JSONObject result = null;

        try
        {
            response = new ConnectionManager(activity).execute(request).get().getBody();
            result = response.getObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }


    public void tagsSearch(String text)
    {
//        Toast.makeText(context, "Please wait...", Toast.LENGTH_LONG).show();
        recipesJSONArray = null;
        String request = "";
        if (preferences == null)
        {
            request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/random?limitLicense=false&number=10&tags=" + changeTextToRequest(text);
            recipesJSONArray = getJsonArrayFromRequest(request, "recipes");
        }

        else
        {
            request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/searchComplex?limitLicense=false&number=10&offset=10&ranking=1&instructionsRequired=true&tags=" + changeTextToRequest(text) + "&" + changePreferencesToQuery();
            recipesJSONArray = getJsonArrayFromRequest(request, "results");
        }
        getRecipesTitleUrlFromArray();
    }


    public void ingredientsSearch(String text)
    {
        recipesJSONArray = null;
        String request = "";
        if (preferences == null)
        {
            request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?fillIngredients=false&ingredients="  + changeTextToRequest(text);
            recipesJSONArray = getJsonArrayFromRequest(request, "");
        }
        else
        {
            request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/searchComplex?limitLicense=false&number=10&offset=10&ranking=1&instructionsRequired=true&ingredients=" + changeTextToRequest(text) + "&" + changePreferencesToQuery();
            recipesJSONArray = getJsonArrayFromRequest(request, "results");
        }
        getRecipesTitleUrlFromArray();
    }

    public void similarRecipesSearch(int id)
    {
        recipesJSONArray = null;
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/similar?number=10";
        recipesJSONArray = getJsonArrayFromRequest(request, "");
        getRecipesTitleUrlFromArray();
    }



    public void complexSearch(HashMap<String, String> nameValue)
    {
        recipesJSONArray = null;
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
            response = new ConnectionManager(activity).execute(request).get().getBody();
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

}
