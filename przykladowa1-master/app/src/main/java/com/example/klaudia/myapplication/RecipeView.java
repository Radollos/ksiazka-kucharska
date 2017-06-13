package com.example.klaudia.myapplication;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class RecipeView extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private String key = "recipe";
    protected Recipe myRecipe;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        view = (View) findViewById(R.id.main_content) ;
        JSONObject myJSON = null;
        String jsonReference = null;

        jsonReference = getIntent().getStringExtra(key);
        try {
            myJSON = new JSONObject(jsonReference);
            myRecipe = new Recipe(myJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(myRecipe.getTitle());

        setSupportActionBar(toolbar);

        //toolbar.setTitle(getIntent().getStringExtra("title"));
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    public Recipe getMyRecipe(){
        return myRecipe;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:{
                finish();
                return true;
            }
            case R.id.favorite_item:
            {

                int i = 0;
                String[] listOfFavourites = getApplicationContext().fileList();
                while(i<listOfFavourites.length){
                    if(listOfFavourites[i].equals(myRecipe.getTitle())){
                        Toast.makeText(this, "Recipe already in favourites", Toast.LENGTH_LONG).show();
                        return true;
                    }
                    i++;
                }

                File file = new File(getApplicationContext().getFilesDir(), myRecipe.getTitle());
                try {
                   file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(openFileOutput(myRecipe.getTitle(), Context.MODE_PRIVATE));
                    oos.writeObject(myRecipe);
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, myRecipe.getTitle() + "  added to Favourites", Toast.LENGTH_SHORT).show();

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

//    private String upperCase(String toUpperCase){ //duza literka
//        return toUpperCase.substring(0, 1).toUpperCase() + toUpperCase.substring(1);
//    }

   //usunieto statyczny placeholder - gowno straszne, nie pozdrawiam
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    RecipeView3 tab1 = new RecipeView3();
                    return tab1;
                case 1:
                    RecipeView1 tab2 = new RecipeView1();
                    return tab2;
                case 2:
                    RecipeView2 tab3 = new RecipeView2();
                    return tab3;
                default:
                    return null;

            }

        }


        private void cos(){
        }
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                {return "Description";}
                case 1:
                    return "Ingredients";
                case 2:
                {return "Instruction" +
                        "";

                }
            }
            return null;
        }
    }
}
