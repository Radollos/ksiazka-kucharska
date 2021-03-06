package com.example.klaudia.myapplication;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.LogRecord;

public class  MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private GridView gridview;
    Searcher searcher;
    SearchView searchView;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searcher = ((MyApplication) this.getApplication()).getSearcher();
 //       searcher.setContext(getApplicationContext());
 //       searcher.setActivity(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        createGridView();
        handleIntent(getIntent());

    }

    private void createGridView()
    {
        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id)
            {
                ProgressDialog dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("Chwila!");
                dialog.show();
//                final Handler h = new Handler();
//                Thread t = new Thread(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
//                        h.post(new Runnable()
//                        {
//                            @Override
//                            public void run()
//                            {
//                                Toast.makeText(MainActivity.this, "Please wait...", Toast.LENGTH_LONG).show();
//                            }
//                        });
//                    }
//                });
//                t.start();
//                try {
//                    t.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                int idChosen = ImageAdapter.getReferences(position);
                String tag = "";
                switch (idChosen)
                {
                    case R.drawable.appetizer:
                        searcher.tagsSearch("appetizer");
                        tag = "Appetizer";
                        break;
                    case R.drawable.favorites:
                        //tutaj przejście do ulubionych!
                        break;
                    case R.drawable.breakfast:
                        searcher.tagsSearch("breakfast");
                        tag = "Breakfast";
                        break;
                    case R.drawable.desserts:
                        tag = "Dessert";
                        searcher.tagsSearch("dessert");
                        break;
                    case R.drawable.fish:
                        tag = "Fish";
                        searcher.tagsSearch("fish");
                        break;
                    case R.drawable.meat:
                        tag = "Meat";
                        searcher.tagsSearch("meat");
                        break;
                    case R.drawable.soups:
                        tag = "Soup";
                        searcher.tagsSearch("soup");
                        break;
                    case R.drawable.vege:
                        tag = "Vege";
                        searcher.tagsSearch("vegetarian");
                        break;
                    case R.drawable.cakes:
                        tag = "Cake";
                        searcher.tagsSearch("cake");
                        break;
                    case R.drawable.drinks:
                        tag = "Drink";
                        searcher.tagsSearch("drink");
                        break;
                    case R.drawable.flour_dishes:
                        tag = "Flour dishes";
                        searcher.tagsSearch("flour");
                        break;
                    case R.drawable.glutenfree:
                        tag = "Gluten-free";
                        searcher.tagsSearch("gluten free");
                        break;
                    case R.drawable.italian:
                        tag = "Italian";
                        searcher.tagsSearch("italian");
                        break;
                    case R.drawable.japanese:
                        tag = "Japanese";
                        searcher.tagsSearch("japanese");
                        break;
                    case R.drawable.mexican:
                        tag = "Mexican";
                        searcher.tagsSearch("mexican");
                        break;
                    case R.drawable.salads:
                        tag = "Salad";
                        searcher.tagsSearch("salad");
                        break;
                    case R.drawable.thai:
                        tag = "Thai";
                        searcher.tagsSearch("thai");
                        break;
                    case R.drawable.vegan:
                        tag = "Vegan";
                        searcher.tagsSearch("vegan");
                        break;
                    //              case R.drawable.polish:
                    //                  searcher.tagsSearch_TitlesImages("polish"); //chyba brak polskiej kuchni ^^
                }
                Intent intent = new Intent(getApplicationContext(), RecipesListView.class);
                intent.putExtra("tag", tag);
                dialog.dismiss();
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed()
    {                                                                   // setting nav drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
//        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.fast_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            //nie dziala takie rozwiazenie do szukania, wtf
            case R.id.fast_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        Intent i;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_preferences)
        {
            i = new Intent(this, Preferences.class);
            startActivity(i);
        }
        else if (id == R.id.nav_gallery)
        {
            i = new Intent(this, MyFridge.class);
            startActivity(i);

        }
        else if (id == R.id.nav_slideshow)
        {
            i = new Intent(this, ComplexSearch.class);
            startActivity(i);

        }
        else if (id == R.id.nav_manage)
        {
            i = new Intent(this, Favourites.class);
            startActivity(i);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent)
    {
        if (Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searcher.tagsSearch(query);

            Intent intent2 = new Intent(getApplicationContext(), RecipesListView.class);
            intent2.putExtra("tag", "Found: ");
            startActivity(intent2);
        }
    }


    public void backToMainView(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(intent);
    }

//
//
//
//
//   private class ToastTask extends AsyncTask<Void, Void, Integer>
//   {
//
//        @Override
//        protected void onPreExecute()
//        {
//            Toast.makeText(MainActivity.this, "grrr", Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        protected Integer doInBackground(Void ... params)
//        {
//            return 5;
//        }
//
//    }
}
