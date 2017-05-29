package com.example.klaudia.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Klaudia on 2017-05-11.
 */

//adapter wyswietlajacy obrazek + tytul dania
public class CustomListInstruction extends ArrayAdapter<String>
{
    private Activity context;
    private String [] titles;
    private List<InstructionStep> ingredients;

    public CustomListInstruction(Activity context, String[] titles)
    {
        super (context, R.layout.listview_row_instructions, titles);
        this.context = context;
        this.titles = titles;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_row_instructions, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        txtTitle.setText(titles[position]);

        return rowView;
    }


}
