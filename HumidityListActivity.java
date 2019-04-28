package com.example.licenta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class HumidityListActivity extends AppCompatActivity {

    public static final String TAG = "HumidityListActivity";

    private RecyclerView recyclerView;

    private ArrayList<Humidity> humidities;
    private HumidityAdapter adapter;
    private Intent parentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity_list);

        parentIntent = getIntent();

        humidities = (ArrayList<Humidity>) parentIntent.getSerializableExtra("humidity_list");
        adapter = new HumidityAdapter(this, humidities);

        Log.d(TAG, "onCreate: activity created -> " + humidities.size());

        recyclerView = findViewById(R.id.rv_humidity_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }
}
