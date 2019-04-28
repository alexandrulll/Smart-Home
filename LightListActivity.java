package com.example.licenta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class LightListActivity extends AppCompatActivity {

    public static final String TAG = "LightListActivity";

    private RecyclerView recyclerView;

    private ArrayList<Light> lights;
    private LightAdapter adapter;
    private Intent parentIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_list);


        parentIntent = getIntent();

        lights = (ArrayList<Light>) parentIntent.getSerializableExtra("light_list");
        adapter = new LightAdapter(this, lights);

        Log.d(TAG, "onCreate: activity created -> " + lights.size());

        recyclerView = findViewById(R.id.rv_light_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

}
