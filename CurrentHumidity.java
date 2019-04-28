package com.example.licenta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class CurrentHumidity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ArrayList<Humidity> humidities;
    private HumidityAdapter adapter;
    private Intent parentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_humidity);

        parentIntent = getIntent();

        humidities = (ArrayList<Humidity>) parentIntent.getSerializableExtra("current_humidity");
        adapter = new HumidityAdapter(this, humidities);

        recyclerView = findViewById(R.id.rv_current_humidity);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }
}
