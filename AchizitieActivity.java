package com.example.licenta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class AchizitieActivity extends AppCompatActivity {

    private Button humidityDetails;
    private Button lightDetails;
    private Button temperatureDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achizitie);

        humidityDetails = (Button) findViewById(R.id.humidityInfoButton);
        humidityDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AchizitieActivity.this, HumidityDetailsActivity.class
                ));
            }
        });

        lightDetails = (Button) findViewById(R.id.lightInfoButton);
        lightDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AchizitieActivity.this, LightDetailsActivity.class
                ));
            }
        });

        temperatureDetails = (Button) findViewById(R.id.temperatureInfoButton);
        temperatureDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AchizitieActivity.this, TemperatureDetailsActivity.class
                ));
            }
        });
    }
}
