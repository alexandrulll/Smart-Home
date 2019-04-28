package com.example.licenta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class CurrentLightActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ArrayList<Light> lights;
    private LightAdapter adapter;
    private Intent parentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_light);

        parentIntent = getIntent();

        lights = (ArrayList<Light>) parentIntent.getSerializableExtra("current_light");
        adapter = new LightAdapter(this, lights);

        recyclerView = findViewById(R.id.rv_current_light);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }
}
