package com.example.admin.myrecipes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Vector;

public class MyRecipe extends AppCompatActivity {

//resources
    private static MyRecipe instance;
    RadioGroup rg = (RadioGroup) findViewById(R.id.rg);

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         MyRecipe myRecipe = new MyRecipe();
        setContentView(R.layout.activity_my_recipe);

    }


    public void makeRadioButtons(String name)
    {
        //toastowe resourcy
        Context context = getApplicationContext();
        CharSequence text = "Dodano!";
        int duration = Toast.LENGTH_SHORT;
        //radiobutton
        RadioButton rb = new RadioButton(this);
        rb.setText(name);
        rg.addView(rb);
        //tolst
        Toast.makeText(context, text, duration).show();
    }

// rest of methods of self user



}
