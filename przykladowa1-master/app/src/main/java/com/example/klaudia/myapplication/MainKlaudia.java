package com.example.klaudia.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import java.util.ArrayList;
import java.util.HashMap;

public class MainKlaudia extends AppCompatActivity
{
    Button send;
    ListView list;
    EditText tags;
    EditText calories;
    EditText type;
//    ArrayList<String> adapterList = new ArrayList<String>();
    Searcher searcher;
    Recipe [] recipes;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_klaudia);

        send = (Button) findViewById(R.id.send);
        tags = (EditText) findViewById(R.id.tags);
        calories = (EditText) findViewById(R.id.calories);
        type = (EditText) findViewById(R.id.type);
        list = (ListView) findViewById(R.id.listView);

        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                searcher = new Searcher(MainKlaudia.this);
                //aby w comlex searchu mozna bylo uzywac tylko niektorych pol do wyszukiwania uzywam hashmapy, do ktorej dodaje nazwe atrybutu i jego wartosc
                // (to co wpisal uzytkownik)
                //nazwy kluczy musza miec konstrkucje : a_nazwa, gdzie a to pierwsza litera typu danych, a nazwa to IDENTYCZNA nazwa pola jak w bazie danych
                HashMap<String,String> nameValue = new HashMap<String, String>();
                nameValue.put("s_query", tags.getText().toString());
                nameValue.put("i_maxCalories", calories.getText().toString());
                nameValue.put("s_type", type.getText().toString());

                HashMap<String, Bitmap> results = searcher.complexSearch_Titles(nameValue);
                if (results != null)
                {
                    CustomList adapter = new CustomList(MainKlaudia.this, results.keySet().toArray(new String [results.size()]), results.values().toArray(new Bitmap [results.size()]));
                    list.setAdapter(adapter);
                }
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
