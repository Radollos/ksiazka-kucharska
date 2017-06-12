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
 * Created by Radek on 2017-06-11.
 */

public class FavouritesListAdapter extends ArrayAdapter<String> {

    Vector<String> favouritesTitles;
    private Activity context;

    public FavouritesListAdapter(Activity context, Vector<String> favouritesTitles)
    {
        super(context, R.layout.favourite_list_row);
        this.favouritesTitles = favouritesTitles;
        this.context = context;
    }

    public void setFavouritesTitles(Vector<String> newFavouriteTitles){
        favouritesTitles = newFavouriteTitles;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return favouritesTitles.size();
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.favourite_list_row, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.favourite_row_text);
        Button bDelete = (Button) rowView.findViewById(R.id.favourite_row_delete);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Favourites)context).deleteFavourite(favouritesTitles.get(position));
            }
        });
        txtTitle.setText(favouritesTitles.get(position));
        return rowView;
    }
}
