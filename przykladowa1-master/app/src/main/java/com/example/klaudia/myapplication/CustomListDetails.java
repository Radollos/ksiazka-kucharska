package com.example.klaudia.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Klaudia on 2017-05-11.
 */

//adapter wyswietlajacy obrazek + tytul dania
public class CustomListDetails extends ArrayAdapter<String>
{
    private Activity context;
    private String [] titles;
    private Boolean[] isTrueOrFalse;

    public CustomListDetails(Activity context, String [] titles, Boolean[] isTrueOrFalse)
    {
        super (context, R.layout.listview_row, titles);
        this.context = context;
        this.titles = titles;
        this.isTrueOrFalse = isTrueOrFalse;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_row_ingredient, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(titles[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.getLayoutParams().height = 50;
        imageView.getLayoutParams().width = 50;
        Picasso.with(getContext()).load((isTrueOrFalse[position]) ? R.drawable.checked : R.drawable.rejected).into(imageView);


        return rowView;
    }


}
