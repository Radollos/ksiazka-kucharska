package com.example.admin.myrecipes;

/**
 * Created by Admin on 2017-05-09.
 */

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.androidopentutorials.sharedpreference.utils.SharedPreference;

public class SecondActivity extends SherlockActivity {

    // UI References
    private TextView textTxt;

    private String text;

    private SharedPreference sharedPreference;

    Activity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        sharedPreference = new SharedPreference();

        findViewsById();

        //Retrieve a value from SharedPreference
        text = sharedPreference.getValue(context);
        textTxt.setText(text);

    }

    private void findViewsById() {
        textTxt = (TextView) findViewById(R.id.txt_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}