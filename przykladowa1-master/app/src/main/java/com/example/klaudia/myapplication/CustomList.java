package com.example.klaudia.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Klaudia on 2017-05-11.
 */

//adapter wyswietlajacy obrazek + tytul dania
public class CustomList extends ArrayAdapter<String>
{
    private Activity context;
    private String [] titles;
    private Bitmap [] images;

    public CustomList(Activity context, String [] titles, Bitmap[] images)
    {
        super (context, R.layout.listview_row, titles);
        this.context = context;
        this.titles = titles;
        this.images = images;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_row, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(titles[position]);
        imageView.setImageBitmap(images[position]);
        return rowView;
    }


}
