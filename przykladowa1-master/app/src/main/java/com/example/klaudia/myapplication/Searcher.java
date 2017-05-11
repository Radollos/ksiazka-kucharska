package com.example.klaudia.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
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
    //potrzebne do utworzenie Toasta
    Context context;


    public Searcher () {};
    public Searcher(Context context)
    {
        this.context = context;
    }


    //wyszukiwanie przepisu po id
    public Recipe getRecipeById(String id)
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


    //wyszukiwanie przepisow po tagach
    public Recipe [] tagsSearch (String text)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/random?limitLicense=false&number=10&tags=" + changeTextToRequest(text);
        JSONArray array = getJsonArrayFromRequest(request, "recipes");
        if (array == null)
            Toast.makeText(context, "Brak wynikow dla podanych danych.", Toast.LENGTH_LONG).show();

        return getRecipesFromArray(array);
    }

    public HashMap<String, Bitmap> tagsSearch_TitlesImages (String text)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/random?limitLicense=false&number=10&tags=" + changeTextToRequest(text);
        JSONArray array = getJsonArrayFromRequest(request, "recipes");
        if (array == null)
            Toast.makeText(context, "Brak wynikow dla podanych danych.", Toast.LENGTH_LONG).show();

        return getRecipesTitlesmagesFromArray(array);
    }


    //wyszukiwanie przepisow po skladnikach
    public Recipe [] ingredientsSearch(String text)
    {
        HashMap<String, ImageView> result = new HashMap<String, ImageView>();
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?fillIngredients=false&ingredients=" + changeTextToRequest(text) +  "&limitLicense=false&number=10";
        JSONArray array = getJsonArrayFromRequest(request, "");
        if (array == null)
            Toast.makeText(context, "Brak wynikow dla podanych danych.", Toast.LENGTH_LONG).show();

        return getRecipesFromArray(array);
    }

    public HashMap<String, Bitmap> ingredientsSearch_TitlesImages(String text)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?fillIngredients=false&ingredients=" + changeTextToRequest(text) +  "&limitLicense=false&number=10";
        JSONArray array = getJsonArrayFromRequest(request, "");
        if (array == null)
            Toast.makeText(context, "Brak wynikow dla podanych danych.", Toast.LENGTH_LONG).show();

        return getRecipesTitlesmagesFromArray(array);
    }


    //wysztkiwanie podobnych przepisow
    public Recipe [] similarRecipesSearch(int id)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/similar?number=10";
        JSONArray array = getJsonArrayFromRequest(request, "");
        if (array == null)
            Toast.makeText(context, "Brak wynikow dla podanych danych.", Toast.LENGTH_LONG).show();

        return getRecipesFromArray(array);
    }

    public HashMap<String, Bitmap> similarRecipesSearch_Titles(int id)
    {
        String request = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/similar?number=10";
        JSONArray array = getJsonArrayFromRequest(request, "");
        if (array == null)
            Toast.makeText(context, "Brak wynikow dla podanych danych.", Toast.LENGTH_LONG).show();

        return getRecipesTitlesmagesFromArray(array);
    }


    //wyszukiwanie zaawansowane
    //nazwy kluczy musza miec konstrkucje : a_nazwa, gdzie a to pierwsza litera typu danych, a nazwa to IDENTYCZNA nazwa pola jak w bazie danych
    public Recipe [] complexSearch(HashMap<String, String> nameValue)
    {
        if (checkMapCorrectness(nameValue))
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
            JSONArray array = getJsonArrayFromRequest(request, "results");
            if (array == null)
                Toast.makeText(context, "Brak wynikow dla podanych danych.", Toast.LENGTH_LONG).show();

            return getRecipesFromArray(array);
        }
        else
            return null;
    }

    public HashMap<String, Bitmap> complexSearch_Titles(HashMap<String, String> nameValue)
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
            JSONArray array = getJsonArrayFromRequest(request, "results");
            if (array == null)
                Toast.makeText(context, "Brak wynikow dla podanych danych.", Toast.LENGTH_LONG).show();

            return getRecipesTitlesmagesFromArray(array);
        }
        else
            return null;
    }


    //metoda, ktora laczy sie z baza i zwraca nam JSONArray, ktory zawiera liste przepisow zapisanych za pomoca JSONa
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
            //w bazie tablice z przepisami sa roznie zapisane - nazywaja sie "results" albo "recipes" albo nie maja nazwy. Po to jest zmienna jsonArrayName, ktora oznacza jak
            //zapisana jest tabela w JSONie. Inaczej wyciaga sie dane z tabeli, ktora ma nazwe i ktora jej nie ma
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


    //funkcja zamieniaja tablica JSONArray na tablice przepisow
    public Recipe [] getRecipesFromArray(JSONArray array)
    {
        Recipe [] recipes = null;
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
        return recipes;
    }


    public HashMap<String, Bitmap> getRecipesTitlesmagesFromArray(JSONArray array)
    {
        HashMap<String, Bitmap> result = new HashMap<String, Bitmap>();
        try
        {
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject recipe = array.getJSONObject(i);
                String title = recipe.getString("title");
        //        ImageView image = new ImageView(context);
                Bitmap tmp = new DownloadImageTask(context).execute(recipe.getString("image")).get();
                Bitmap scaled = Bitmap.createScaledBitmap(tmp, 50, 50, true);
        //        image.setImageBitmap(scaled);
        //        image.setBackgroundColor(Color.BLACK);
                result.put(title, scaled);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
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
                Toast.makeText(context, "Niepoprawny format danych " + pair.getKey().toString().substring(2, pair.getKey().toString().length()), Toast.LENGTH_LONG).show();
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
