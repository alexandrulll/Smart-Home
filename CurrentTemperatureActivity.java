package com.example.licenta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class CurrentTemperatureActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ArrayList<Temperature> temperatures;
    private TemperatureAdapter adapter;
    private Intent parentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_temperature);

        parentIntent = getIntent();

        temperatures = (ArrayList<Temperature>) parentIntent.getSerializableExtra("current_temperature");
        adapter = new TemperatureAdapter(this, temperatures);

        recyclerView = findViewById(R.id.rv_current_temperature);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }
}
