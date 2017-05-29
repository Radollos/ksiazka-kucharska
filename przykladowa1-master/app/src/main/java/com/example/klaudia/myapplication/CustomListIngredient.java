package com.example.klaudia.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Klaudia on 2017-05-11.
 */

//adapter wyswietlajacy obrazek + tytul dania
public class CustomListIngredient extends ArrayAdapter<String>
{
    private Activity context;
    private String [] titles;
    private List<Ingredient> ingredients;
    private DecimalFormat formatDouble;

    public CustomListIngredient(Activity context, ArrayList<Ingredient> ingredients, String[] titles)
    {

        super (context, R.layout.listview_row_ingredient, titles);
        this.context = context;
        this.ingredients = ingredients;
        this.titles = titles;

        formatDouble = new DecimalFormat("#.##");
    }


    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_row_ingredient, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        Ingredient ingredient = ingredients.get(position);

//        String num = "99.99";  // I assume that is `String` because entered by user and not yet converted to `double`
//        int i = num.lastIndexOf('.');
//        if(i != -1 && num.substring(i + 1).length() > 2) {
//            num.substring()
//        }
        // wyświetla liczbę jako 0,0 zamiast 0.0, co wg mnie wygląda lepiej, ale nie chce mi sie z tym pierdolic na razie
        txtTitle.setText(formatDouble.format(ingredient.getAmount()) + " " + ingredient.getUnit() + " " + ingredient.getName());
        Picasso.with(context)
                .load(ingredient.getImageURL())

                .into(imageView);
        return rowView;
    }


}
