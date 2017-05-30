package com.example.klaudia.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;



//adapter wyswietlajacy obrazek + tytul dania
public class CustomList extends ArrayAdapter<String>
{
    private Activity context;
    private final String [] urls;
    private final String [] titles;


    public CustomList(Activity context, String [] titles, String[] urls)
    {
        super (context, R.layout.listview_row, titles);
        this.context = context;
        this.titles = titles;
        this.urls = urls;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_row, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(titles[position]);
        Picasso.with(context).load(urls[position]).into(imageView);
        return rowView;
    }



}
