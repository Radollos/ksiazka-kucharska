package com.example.klaudia.myapplication;

/**
 * Created by Admin on 2017-05-09.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.klaudia.*;
//import sharedpreference.utils.SharedPreference;

public class SearcherActivity extends Activity {

    // References
    private EditText textEtxt;

    private Button saveButton;
    private Button activity2Button;

    private String text;
    private SharedPreference sharedPreference;
    Activity context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_recipes);

        sharedPreference = new SharedPreference();

        findViewsById();

        saveButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                text = textEtxt.getText().toString();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                    imm.hideSoftInputFromWindow(textEtxt.getWindowToken(), 0);
                }

                // save to ShPr
                sharedPreference.saveNote(context, text);
                Toast.makeText(context,
                        getResources().getString(R.string.saved),
                        Toast.LENGTH_LONG).show();


            }
        });

        activity2Button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findViewsById() {
        textEtxt = (EditText) findViewById(R.id.etxt_text);
        saveButton = (Button) findViewById(R.id.button_save);
        activity2Button = (Button) findViewById(R.id.button_second_activity);
    }

    // konfliktowe
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}