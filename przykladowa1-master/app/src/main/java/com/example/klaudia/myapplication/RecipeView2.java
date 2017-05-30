package com.example.klaudia.myapplication;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Slawcio on 2017-05-27.
 */

public class RecipeView2 extends Fragment {


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Recipe myRecipe = null;
        View rootView = inflater.inflate(R.layout.fragment_recipe_view_2, container, false);
        RecipeView mainRecipeView = (RecipeView) getActivity();
        myRecipe = mainRecipeView.getMyRecipe();

        String[] stringList = new String[myRecipe.getAnalyzedInstructions().size()];
        for(int i = 0; i < myRecipe.getAnalyzedInstructions().size(); i++){
            stringList[i] = myRecipe.getAnalyzedInstructions().get(i).getNumber() + ". " + myRecipe.getAnalyzedInstructions().get(i).getStep();
        }
        ListView myList = (ListView) rootView.findViewById(R.id.instruction_list);
        //    ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, stringList);
        myList.setAdapter(new CustomListInstruction(getActivity(), stringList));
        //     myList.setAdapter(listViewAdapter);

        //     = inflater.inflate(R.layout.fragment_recipe_view, container, false);
        //    TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //    textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }

}