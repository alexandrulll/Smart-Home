package com.example.licenta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button controlButton;
    private Button achizitieButton;
    private Button supraveghereButton;

    private ArrayList<MotionSensor> motionSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        motionSensors = new ArrayList<>();
        //reqMotionSensorEntrance("http://192.168.100.11:8080/motion/start");

        achizitieButton = (Button) findViewById(R.id.butonAchizitie);
        achizitieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AchizitieActivity.class));
            }
        });

        controlButton = (Button) findViewById(R.id.butonControlEchipament);
        controlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ControlActivity.class));
            }
        });

        supraveghereButton = (Button) findViewById(R.id.butonSupraveghere);
        supraveghereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SupraveghereActivity.class));
            }
        });
    }

    private void reqMotionSensorEntrance(String url) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {

                    String json = response.body().string();
                    JSONObject resObj = new JSONObject(json);
                    long id = resObj.getLong("id");
                    String alertText = resObj.getString("alertText");
                    long timeStamp = resObj.getLong("timeStamp");

                    final MotionSensor motionSensor = new MotionSensor();
                    motionSensor.setId(id);
                    motionSensor.setAlertText(alertText);
                    motionSensor.setTimeStamp(timeStamp);

                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            motionSensors.add(motionSensor);
                        }});

                } catch(JSONException e) {

                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });

    }
}
