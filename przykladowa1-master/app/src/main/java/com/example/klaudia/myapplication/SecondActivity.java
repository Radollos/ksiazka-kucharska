package com.example.klaudia.myapplication;

/**
 * Created by Admin on 2017-05-09.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import java.util.ArrayList;

public class SecondActivity extends Activity {

    // UI References
    private TextView textTxt;
    private String text;
    private SharedPreference sharedPreference;
    Activity context = this;


    /*
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    isVegan (t/f)
    itp itd
   */

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

        /*text += sharedPreference.getValue(context);
        textTxt.append(text);
        */
    }


    // ----------------------------------------------------------------


    // prawie jak override
    private void findViewsById() {
        textTxt = (TextView) findViewById(R.id.txt_text);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        String text = textTxt.getText().toString();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
        //getSupportMenuInflater().inflate(R.menu.main, menu);
        //return true;
    }
}