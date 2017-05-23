package com.example.klaudia.myapplication;

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
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by Klaudia on 2017-04-24.
 */

public class Searcher
{
    private JSONArray recipesJSONArray;
    private HashMap<String, Bitmap> titleImage;
    private static final String TAG = MainActivity.class.getSimpleName();

    //wyszukiwanie przepisu po id
    public Recipe getRecipeById(int id)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/information?includeNutrition=false";
        JsonNode response = null;
        Recipe result = null;
        //wysylamy zapytanie do bazy za pomoca obiektu klasy ConnectionManager. Metoda execute(request).get().getBody() zwraca nam JsonNoda - wynik polaczenia z baza
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


    public JSONObject getRecipeJSONObjectById(int id)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/information?includeNutrition=false";
        JsonNode response = null;
        JSONObject result = null;

        try
        {
            response = (JsonNode) new ConnectionManager().execute(request).get().getBody();
            result = response.getObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    //wyszukiwanie przepisow po tagach
    public JSONArray tagsSearch (String text)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/random?limitLicense=false&number=10&tags=" + changeTextToRequest(text);
        recipesJSONArray = getJsonArrayFromRequest(request, "recipes");
        return recipesJSONArray;
    }

    public void tagsSearch_TitlesImages (String text)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/random?limitLicense=false&number=10&tags=" + changeTextToRequest(text);
        recipesJSONArray = getJsonArrayFromRequest(request, "recipes");
        getRecipesTitlesmagesFromArray(recipesJSONArray);
    }


    //wyszukiwanie przepisow po skladnikach
    public JSONArray ingredientsSearch(String text)
    {
        HashMap<String, ImageView> result = new HashMap<String, ImageView>();
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?fillIngredients=false&ingredients=" + changeTextToRequest(text) +  "&limitLicense=false&number=10";
        recipesJSONArray = getJsonArrayFromRequest(request, "");
        return recipesJSONArray;
    }

    public void ingredientsSearch_TitlesImages(String text)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?fillIngredients=false&ingredients=" + changeTextToRequest(text) +  "&limitLicense=false&number=10";
        recipesJSONArray = getJsonArrayFromRequest(request, "");
        getRecipesTitlesmagesFromArray(recipesJSONArray);
    }

    public void similarRecipesSearch_Titles(int id)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/similar?number=10";
        recipesJSONArray = getJsonArrayFromRequest(request, "");
        getRecipesTitlesmagesFromArray(recipesJSONArray);
    }


    //wysztkiwanie podobnych przepisow
    public JSONArray similarRecipesSearch(int id)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/similar?number=10";
        recipesJSONArray = getJsonArrayFromRequest(request, "");
        return recipesJSONArray;
    }




    //wyszukiwanie zaawansowane
    //nazwy kluczy musza miec konstrkucje : a_nazwa, gdzie a to pierwsza litera typu danych, a nazwa to IDENTYCZNA nazwa pola jak w bazie danych
    public JSONArray complexSearch(HashMap<String, String> nameValue)
    {
        String result = "";
        Iterator it = nameValue.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry) it.next();
            Object key = pair.getKey();
            Object value = pair.getValue();

            //nie zapisujemy do resulta 2 poczatkowych liter klucza, czyli litery oznaczej typ danej i '_'. Kropka sluzy do oddzielenia pary (klucz, wartosc)
            if (key.toString() != "s_query" && value != null)
                result += key.toString().substring(2, key.toString().length()) + "=" + value + ".";
        }

        if (result.length() > 0 && result.charAt(result.length()-1) == '.')
            result = result.substring(0, result.length() - 1);

        result = result.replace('.', '&');
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/searchComplex?limitLicense=false&number=10&offset=10&ranking=1&" + changeTextToRequest(result);
        recipesJSONArray = getJsonArrayFromRequest(request, "results");
        return recipesJSONArray;
    }


    public void complexSearch_Titles(HashMap<String, String> nameValue)
    {
        if (checkMapCorrectness(nameValue))
        {
            String userInput = "";
            Iterator it = nameValue.entrySet().iterator();
            while (it.hasNext())
            {
                Map.Entry pair = (Map.Entry) it.next();
                Object key = pair.getKey();
                Object value = pair.getValue();

                //nie zapisujemy do resulta 2 poczatkowych liter klucza, czyli litery oznaczej typ danej i '_'. Kropka sluzy do oddzielenia pary (klucz, wartosc)
                if (key.toString() != "s_query" && value != null)
                    userInput += key.toString().substring(2, key.toString().length()) + "=" + value + ".";
            }

            if (userInput.length() > 0 && userInput.charAt(userInput.length()-1) == '.')
                userInput = userInput.substring(0, userInput.length() - 1);

            userInput = userInput.replace('.', '&');
            String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/searchComplex?limitLicense=false&number=10&offset=10&ranking=1&" + changeTextToRequest(userInput);
            recipesJSONArray = getJsonArrayFromRequest(request, "results");
            getRecipesTitlesmagesFromArray(recipesJSONArray);
        }
    }


    //metoda, ktora laczy sie z baza i zwraca nam JSONArray, ktory zawiera liste przepisow zapisanych za pomoca JSONa
    public JSONArray getJsonArrayFromRequest(String request, String jsonArrayName)
    {
        JsonNode response = null;
        try
        {
            response = (JsonNode) new ConnectionManager().execute(request).get().getBody();
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


    //funkcja zamieniaja tablica JSONArray na tablice przepisow
    public Recipe [] getRecipesFromArray(JSONArray array)
    {
        Recipe [] recipes = null;
        try
        {
            recipes = new Recipe[array.length()];
            for (int i = 0; i < array.length(); i++)
            {
                Recipe recipe = getRecipeById(array.getJSONObject(i).getInt("id"));
                recipes[i] = recipe;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return recipes;
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


    public void getRecipesTitlesmagesFromArray(JSONArray array)
    {
        titleImage = new HashMap<String, Bitmap>();
        try
        {
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject recipe = array.getJSONObject(i);
                String title = recipe.getString("title");
                Bitmap image = new DownloadImageTask().execute(recipe.getString("image")).get();
                titleImage.put(title, image);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public HashMap<String, Bitmap> getHashMap()
    {
        return titleImage;
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
