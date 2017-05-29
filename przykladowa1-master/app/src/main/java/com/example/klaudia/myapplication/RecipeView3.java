package com.example.klaudia.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeView3 extends Fragment
{
    Recipe recipe;
    ImageView imageRecipe;
    TextView name;
    TextView cookingTime;
    TextView preparationTime;
    TextView price;
    TextView servings;


    public RecipeView3()
    {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        recipe = ((RecipeView) getActivity()).myRecipe;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_recipe_view_3, container, false);
        imageRecipe = (ImageView) view.findViewById(R.id.image_recipe);
        name = (TextView) view.findViewById(R.id.name);
        cookingTime = (TextView) view.findViewById(R.id.cooking_time);
        preparationTime = (TextView) view.findViewById(R.id.preparation_time);
        price = (TextView) view.findViewById(R.id.price);
        servings = (TextView) view.findViewById(R.id.servings);

        Picasso.with(getContext()).load(recipe.getImage()).into(imageRecipe);
        name.setText("Name: " + recipe.getTitle());
        //TODO zamienic czas przygotowywania na godziny + minuty
        cookingTime.setText("Cooking time: " + recipe.getCookingMinutes() + " minutes");
        preparationTime.setText("Prepearation time: " + recipe.getPreparationMinutes() + " minutes");
        //TODO sprawdzic walute
        price.setText(String.valueOf("Price for serving: " + recipe.getPricePerServing() + "$"));

        servings.setText("Servings: " + String.valueOf(recipe.getServings()));

        return view;
    }



}
