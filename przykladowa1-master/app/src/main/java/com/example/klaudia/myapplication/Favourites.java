package com.example.klaudia.myapplication;

/**
 * Created by Admin on 2017-05-31.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;


public class Favourites extends Activity {

    // UI References
    Activity context = this;

/*TODO create query to make JSon object REcipe


 */
 //Recipe r =new Recipe ("");

    public static void set(Recipe id, Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "object_prefs", 0);
        complexPreferences.putObject("object_value", id);
        complexPreferences.commit();
    }

    public static Recipe get(Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "object_prefs", 0);
        Recipe id = complexPreferences.getObject("object_value", Recipe.class);
        return id;
    }

    public static void clear( Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "object_prefs", 0);
        complexPreferences.clearObject();
        complexPreferences.commit();
    }



}