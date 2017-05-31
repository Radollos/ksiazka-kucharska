package com.example.klaudia.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import org.json.JSONObject;
import java.util.LinkedHashMap;
import java.util.Map;


public class RecipesListView extends AppCompatActivity {

    ListView recipeList;

    String tag; //tu będzie przypisana kategoria
    String key = "tag"; //klucz dla kategorii wybranej w gridzie

    String[] titles;
    String[] urls;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list_view);
        getList();
        setToolbar();
        setListView();

    }

    private void getList()
    {
        LinkedHashMap<String, String> list = ((MyApplication) this.getApplication()).getSearcher().getHashMapURL();
        titles = new String[list.size()];
        urls = new String[list.size()];

        int j = 0;

        for (Map.Entry pair : list.entrySet())
        {
            titles[j] = pair.getKey().toString();
            urls[j] = pair.getValue().toString();
            j++;
        }
    }


    private void setToolbar(){ //ustawiam toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_list_view);
        tag = getIntent().getStringExtra(key);
        toolbar.setTitle(tag + " recipes");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) { //powrót dla backbuttonu
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void setListView(){ //ustawiam widok listy, odpalam adapter
        recipeList = (ListView) findViewById(R.id.recipes_list_view);
        //       CustomList adapter = new CustomList(this, titles, images);
        CustomList adapter = new CustomList(this, titles, urls);
        recipeList.setAdapter(adapter);
        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  //na razie wyświetlanie przepisu jako nowa aktywość do pracy nad widokiem, potem będziemy ogarniać fragmenty we fragmencie
                // fragment we fragmencie nie przejdzie, ale bez problemu to zadziała z aktywnością tak jak chcemy. Lista nie znika przy powrocie, przejścia są płynne i szybkie.
                Intent intent = new Intent(getApplicationContext(), RecipeView.class);
                JSONObject recipeJSONObject = ((MyApplication) getApplication()).getSearcher().getJSONObjectFromArray(position); //!!!wczytuje nie te jsony z listy co są na position
                intent.putExtra("recipe", recipeJSONObject.toString());
                startActivity(intent);
            }
        });

    }


}
