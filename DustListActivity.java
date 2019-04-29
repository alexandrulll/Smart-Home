package com.example.licenta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class DustListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ArrayList<Dust> dusts;
    private DustAdapter adapter;
    private Intent parentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dust_list);

        parentIntent = getIntent();

        dusts = (ArrayList<Dust>) parentIntent.getSerializableExtra("dust_list");
        adapter = new DustAdapter(this, dusts);

        recyclerView = findViewById(R.id.rv_dust_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

    }
}
