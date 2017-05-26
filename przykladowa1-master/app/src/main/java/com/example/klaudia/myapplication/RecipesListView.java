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
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RecipesListView extends AppCompatActivity {

    ListView recipeList;

    String tag; //tu będzie przypisana kategoria
    String key = "tag"; //klucz dla kategorii wybranej w gridzie

    Bitmap []images;
    String[] titles;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list_view);
        getList();

//        setCategory();
        setToolbar();
        setListView();

    }

    private void getList()
    {
        HashMap<String, Bitmap> list = ((MyApplication) this.getApplication()).getSearcher().getHashMap();
        images = new Bitmap[list.size()];
        titles = new String[list.size()];
        int i = 0;

        for (Map.Entry pair : list.entrySet())
        {
            titles[i] = pair.getKey().toString();
            images[i] = (Bitmap) pair.getValue();
            i++;
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
 //       collectDataFromHashMapToTable();
        CustomList adapter = new CustomList(this, titles, images);
        recipeList.setAdapter(adapter);
        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  //na razie wyświetlanie przepisu jako nowa aktywość do pracy nad widokiem, potem będziemy ogarniać fragmenty we fragmencie
                // fragment we fragmencie nie przejdzie, ale bez problemu to zadziała z aktywnością tak jak chcemy. Lista nie znika przy powrocie, przejścia są płynne i szybkie.
                Intent intent = new Intent(getApplicationContext(), RecipeView.class);
                JSONObject recipeJSONObject = ((MyApplication) getApplication()).getSearcher().getJSONObjectFromArray(position); //!!!wczytuje nie te jsony z listy co są na position
//                intent.putExtra("title", titles[position]);
//                intent.putExtra("bitmap", bitmaps[position]);
                intent.putExtra("recipe", recipeJSONObject.toString());
                startActivity(intent);
            }
        });
        
    }

//    private void collectDataFromHashMapToTable() { //tu mielę z hashmapy na tablice bitmapy i tytulów
//        Searcher search = new Searcher();
//        data = search.tagsSearch_TitlesImages(category);
//        int size = data.size();
//        titles = new String[size];
//        bitmaps = new Bitmap[size];
//        int index = 0;
//        for (Map.Entry<String, Bitmap> entry : data.entrySet()) {
//            titles[index] = entry.getKey();
//            bitmaps[index] = entry.getValue();
//            index++;
//        }
//
//    }


}
