package com.example.licenta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class TemperatureListActivity extends AppCompatActivity {

    public static final String TAG = "TemperatureListActivity";

    private RecyclerView recyclerView;

    private ArrayList<Temperature> temperatures;
    private TemperatureAdapter adapter;
    private Intent parentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_list);

        parentIntent = getIntent();

        temperatures = (ArrayList<Temperature>) parentIntent.getSerializableExtra("temperature_list");
        adapter = new TemperatureAdapter(this, temperatures);

        Log.d(TAG, "onCreate: activity created -> " + temperatures.size());

        recyclerView = findViewById(R.id.rv_temperature_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }
}
