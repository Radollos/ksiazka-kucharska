package com.example.klaudia.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.Vector;

/**
 * Created by Radek on 2017-05-29.
 */

public class FridgeListAdapter extends ArrayAdapter<String>
{
    Vector<MyFridge.FridgeIngredient> ingredients;
    private Activity context;

    public FridgeListAdapter(Activity context, Vector<MyFridge.FridgeIngredient> ingredients)
    {
        super(context, R.layout.fridge_list_row);
        this.ingredients = ingredients;
        this.context = context;
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.fridge_list_row, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.fridge_row_text);
        Button bDelete = (Button) rowView.findViewById(R.id.fridge_row_delete);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyFridge)context).removeIngredient(position);
                notifyDataSetChanged();
            }
        });
//        Button bEdit = (Button) rowView.findViewById(R.id.fridge_row_edit);
//        bEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO: edit button
//            }
//        });
        txtTitle.setText(ingredients.get(position).ingredientName + "  " + ingredients.get(position).amount + " " + ingredients.get(position).unit);
        return rowView;
    }
}
