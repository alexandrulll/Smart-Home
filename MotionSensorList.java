package com.example.licenta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MotionSensorList extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ArrayList<MotionSensor> motionSensors;
    private SupraveghereAdapter adapter;
    private Intent parentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_sensor_list);

        parentIntent = getIntent();

        motionSensors = (ArrayList<MotionSensor>) parentIntent.getSerializableExtra("motion_sensors");
        adapter = new SupraveghereAdapter(this, motionSensors);

        recyclerView = findViewById(R.id.rv_motion_sensors);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

    }
}
