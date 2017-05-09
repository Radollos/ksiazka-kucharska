package com.example.admin.myrecipes;

/**
 * Created by Admin on 2017-05-09.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.androidopentutorials.sharedpreference.utils.SharedPreference;

public class SearcherActivity extends Activity {

    // UI References
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

                // Hides the soft keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(textEtxt.getWindowToken(), 0);

                // Save the text in SharedPreference
                sharedPreference.save(context, text);
                Toast.makeText(context,
                        getResources().getString(R.string.saved),
                        Toast.LENGTH_LONG).show();
            }
        });

        activity2Button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SecondActivity.class);
                // Start next activity
                startActivity(intent);
            }
        });
    }

    private void findViewsById() {
        textEtxt = (EditText) findViewById(R.id.etxt_text);
        saveButton = (Button) findViewById(R.id.button_save);
        activity2Button = (Button) findViewById(R.id.button_second_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}