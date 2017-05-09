package com.example.klaudia.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import java.util.ArrayList;

public class MainKlaudia extends AppCompatActivity
{
    Button send;
    ListView list;
    EditText tags;
    ArrayList<String> adapterList = new ArrayList<String>();
    Searcher searcher = new Searcher();
    Recipe [] recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_klaudia);

        send = (Button) findViewById(R.id.send);
        tags = (EditText) findViewById(R.id.tags);
        list = (ListView) findViewById(R.id.listView);

        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String tagsText = tags.getText().toString();
                recipes = searcher.ingredientsSearch(tagsText);
                for (int i = 0; i < recipes.length; i++)
                    adapterList.add(recipes[i].getTitle());

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainKlaudia.this, android.R.layout.simple_list_item_1, adapterList);
                list.setAdapter(adapter);
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Intent intent = new Intent(getApplicationContext(), RecipeList.class);
                Bundle bundle = new Bundle();
                bundle.putInt("index", position);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });
    }
}
