package com.example.klaudia.myapplication;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.txt);

                ColorDrawable color = (ColorDrawable) view.getBackground();

                if((color.getColor()&0xffffff) == 0xffffff){
                    Toast.makeText(getActivity(), "Biały" , Toast.LENGTH_SHORT);
                    view.setBackgroundColor(Color.parseColor("#2f901d"));
                    textView.setTextColor(Color.parseColor("#ffffff"));
                }
                else
                {Toast.makeText(getActivity(), "Zielony" , Toast.LENGTH_SHORT);
                    view.setBackgroundColor(Color.parseColor("#ffffff"));
                    textView.setTextColor(Color.parseColor("#000000"));
                }
            }

        });

        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                TextView textView = (TextView) view.findViewById(R.id.txt);
//                float size = textView.getTextSize();
//                float toSize = 45;
//                float toSize2 = 55;
//                if(textView.getTextSize() == 45){
//                    textView.setTextSize(toSize2);
//                } else {
//                    textView.setTextSize(toSize);
//                }
                return true;
            }
        });
        //     myList.setAdapter(listViewAdapter);

        //     = inflater.inflate(R.layout.fragment_recipe_view, container, false);
        //    TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //    textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }

}