package com.example.klaudia.myapplication;

/**
 * Created by Admin on 2017-05-09.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.example.klaudia.*;

import java.util.ArrayList;

public class SecondActivity extends Activity {

    // UI References
    public TextView textTxt;
    public String text;
    public SharedPreference sharedPreference;
    Activity context = this;



    /*
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    isVegan (t/f)
    itp itd
   */

    // UNUSED METHOD
    // **********************
    public String getValue (String t)
    {
        sharedPreference = new SharedPreference();
        findViewsById();


        //get value form ShPr
        ArrayList<String> noteList = new ArrayList<String>(sharedPreference.getNotes(context));
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < noteList.size(); i++)
            stringBuilder.append("\n" + noteList.get(i));
        textTxt.setText(stringBuilder.toString());
        return t = textTxt.toString();
    }

    //******************************


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        sharedPreference = new SharedPreference();
        findViewsById();


        //get value form ShPr

        ArrayList<String> noteList = new ArrayList<String>(sharedPreference.getNotes(context));
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < noteList.size(); i++)
            stringBuilder.append("\n" + noteList.get(i));
        textTxt.setText(stringBuilder.toString());
        //String t = textTxt.toString();
       //getValue(t);
    }

    // prawie jak override
    private void findViewsById() {
        textTxt = (TextView) findViewById(R.id.txt_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
        //getSupportMenuInflater().inflate(R.menu.main, menu);
        //return true;
    }





    //opcjonalnie
    //&&&&&&&&&&&&&&&&&&&&&&
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // String stateSaved = savedInstanceState.getString(textTxt.toString());
        String stateSaved = savedInstanceState.getString(getValue(text));
        if (stateSaved==null){
            Toast.makeText(SecondActivity.this, "No favourites", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(SecondActivity.this, "No favourites", Toast.LENGTH_LONG).show();
        }
        // textTxt.setText(textTxt.toString());
        textTxt.setText(getValue(text));
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //String text = textTxt.getText().toString();
        String t = getValue(text);
        // optionally
        //savedInstanceState.putString("favourites", text);
        // Toast.makeText(SecondActivity.this, text, Toast.LENGTH_LONG).show();
        savedInstanceState.putString("favourites", t);
        Toast.makeText(SecondActivity.this, t, Toast.LENGTH_LONG).show();
    }
    //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&


}