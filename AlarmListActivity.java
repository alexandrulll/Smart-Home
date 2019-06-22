package com.example.licenta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class AlarmListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ArrayList<Alarm> alarms;
    private AlarmAdapter adapter;
    private Intent parentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        parentIntent = getIntent();

        alarms = (ArrayList<Alarm>) parentIntent.getSerializableExtra("alarm_list");
        adapter = new AlarmAdapter(this, alarms);

        recyclerView = findViewById(R.id.rv_alarm_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

    }
}
