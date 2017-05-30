package com.example.klaudia.myapplication;

import android.media.Image;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.klaudia.myapplication.R.layout.linearlayout_row_details;
import static com.example.klaudia.myapplication.R.layout.listview_row_ingredient;

/**
 * Created by Slawcio on 2017-05-27.
 */

public class RecipeView3 extends Fragment {

    private ViewGroup fragmentLayout;
    private Boolean[] detailsBoolean;
    private String[] details;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Recipe myRecipe = null;
        RecipeView mainRecipeView = (RecipeView) getActivity();
        myRecipe = mainRecipeView.getMyRecipe();



        View rootView = inflater.inflate(R.layout.fragment_recipe_view_3, container, false);

        fragmentLayout = (ViewGroup)  rootView.findViewById(R.id.fragment_3);

        ImageView imageRecipe = (ImageView) rootView.findViewById(R.id.image_recipe);
        Picasso.with(getActivity()).load(myRecipe.getImage()).into(imageRecipe);

        TextView name = (TextView) rootView.findViewById(R.id.name);
        TextView preparation = (TextView) rootView.findViewById(R.id.preparation_time);
        TextView cooking = (TextView) rootView.findViewById(R.id.cooking_time);
        TextView price = (TextView) rootView.findViewById(R.id.price);
        TextView servings = (TextView) rootView.findViewById(R.id.servings);

        name.setText("Name: " + myRecipe.getTitle());
        preparation.setText("Preparation: " + myRecipe.getReadyInMinutes() + " min");        cooking.setText("Cooking: " + myRecipe.getCookingMinutes() + " min");
        servings.setText("Price per servings: " + myRecipe.getPricePerServing() + "$"); //zamieniona treść cooking z servings, bo nie chciało mi się przemieszczać textview w xmlu.

        price.setText("Servings: " + myRecipe.getServings());

        details = new String[]{
                "Vegetarian",
                "Vegan",
                "Gluten-free",
                "Dairy-free",
                "Very healty",
                "Cheap",
                "Sustainable",
                "Low fodmap",
                "Ketogenic"
        };

       detailsBoolean = new Boolean[]{
               myRecipe.isVegetarian(),
               myRecipe.isVegan(),
               myRecipe.isGlutenFree(),
               myRecipe.isDairyFree(),
               myRecipe.isVeryHealthy(),
               myRecipe.isCheap(),
               myRecipe.isSustainable(),
               myRecipe.isLowFodmap(),
               myRecipe.isKetogenic()
       };



//        ListView listDetails = (ListView) rootView.findViewById(R.id.detail_list);
//        listDetails.setAdapter(new CustomListDetails(getActivity(), details, detailsBoolean));

        for(int i = 0; i < details.length; i++){
            addLayout(i);
        }
        return rootView;
    }

    private void addLayout(int position) {
        View row = LayoutInflater.from(getActivity()).inflate(linearlayout_row_details, fragmentLayout, false);


        TextView textView = (TextView) row.findViewById(R.id.txt);
        ImageView imageView = (ImageView) row.findViewById(R.id.img);
        textView.setText(details[position]);
        Picasso.with(getContext()).load(
                (detailsBoolean[position]) ? R.drawable.checked : R.drawable.rejected
        ).
                resize(100, 100).
                centerCrop().
                into(imageView);




        fragmentLayout.addView(row);
    }

}