package com.example.klaudia.myapplication;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by Radek on 2017-05-10.
 */

public class Fridge {

    private ArrayList<String> ingredients;

    public Fridge(){
        ingredients = new ArrayList<>();
    }


    public void addIngredient(String ingredient){
        if(!ingredients.contains(ingredient)) ingredients.add(ingredient);
    }

    public boolean removeIngredient(String ingredient) {
        return ingredients.remove(ingredient);
    }

//    public Recipe[] findRecipes(){
//        ListIterator<String> it = ingredients.listIterator();
//        String request = "";
//        while(it.hasNext())
//            request+=("," + it.next());
//        Searcher searcher = new Searcher();
//        return searcher.ingredientsSearch(request);
//    }
}
