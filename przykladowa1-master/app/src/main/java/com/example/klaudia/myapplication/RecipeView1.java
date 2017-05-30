package com.example.klaudia.myapplication;

import android.support.v4.app.Fragment;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slawcio on 2017-05-27.
 */

public class RecipeView1 extends android.support.v4.app.Fragment {

    private Recipe myRecipe = null;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_view_1, container, false);
        RecipeView mainRecipeView = (RecipeView) getActivity();
        myRecipe = mainRecipeView.getMyRecipe();

        String[] stringList = new String[myRecipe.getExtendedIngredients().size()];
        for(int i = 0; i < myRecipe.getExtendedIngredients().size(); i++){
         //   Ingredient ingredient = myRecipe.getExtendedIngredients().get(i);
            stringList[i] = myRecipe.getExtendedIngredients().get(i).getName();
        }
        ListView myList = (ListView) rootView.findViewById(R.id.ingredients_list);
    //    ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, stringList);
        myList.setAdapter(new CustomListIngredient(getActivity(), myRecipe.getExtendedIngredients(), stringList));
   //     myList.setAdapter(listViewAdapter);

        //     = inflater.inflate(R.layout.fragment_recipe_view, container, false);
        //    TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //    textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }

}
