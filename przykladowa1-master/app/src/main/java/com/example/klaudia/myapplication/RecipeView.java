package com.example.klaudia.myapplication;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import org.json.JSONException;
import org.json.JSONObject;


public class RecipeView extends AppCompatActivity
{
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private String key = "recipe";
    protected Recipe myRecipe;

    JSONObject myJSON = null;
    String jsonReference = null;
    FragmentManager manager;
    FragmentTransaction transaction;
    PagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        jsonReference = getIntent().getStringExtra(key);
        try
        {
            myJSON = new JSONObject(jsonReference);
            myRecipe = new Recipe(myJSON);
        }
        catch (JSONException e)
        {
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Soon you will have it in 'Favorites'", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //tutaj ma powstać metoda dodająca przepis do ulubionych

            }
        });

        adapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    public Recipe getMyRecipe(){
        return myRecipe;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
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

    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch (position)
            {
                case 0:
                    return adapter.getItem(0);
                case 1:
                    return adapter.getItem(1);
                case 2:
                    return adapter.getItem(2);
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
                    return "Ingredients";
                case 1:
                    return "Instruction";
                case 2:
                    return "Description";
            }
            return null;
        }
    }
}
