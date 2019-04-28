package com.example.licenta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LightDetailsActivity extends AppCompatActivity {

    public static final String TAG = "LightDetailsActivity";

    private Button currentLightButton;
    private Button allLightButton;

    private LightAdapter lightAdapter;

    private ArrayAdapter<String> adapter;
    private ArrayList<Light> lights;
    private ArrayList<Light> lightsFromDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_details);

        Log.d(TAG, "onCreate: activity created");

        lights = new ArrayList<>();
        lightAdapter = new LightAdapter(this, lights);

        reqCurrentLight("http://192.168.100.11:8080/tsl/save");
        currentLightButton = (Button) findViewById(R.id.currentLightButton);
        currentLightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: button clicked");
                startActivity(new Intent(LightDetailsActivity.this, CurrentLightActivity.class)
                        .putExtra("current_light", lights));

            }
        });

        lightsFromDB = new ArrayList<>();

        reqLightEntries("http://192.168.100.11:8080/tsl/all");
        allLightButton = (Button) findViewById(R.id.lightListButton);
        allLightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LightDetailsActivity.this, LightListActivity.class)
                        .putExtra("light_list", lightsFromDB));
            }
        });

    }

    private void reqLightEntries(String url) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = response.body().string();

                try {

                    JSONArray array = new JSONArray(json);
                    Log.d(TAG, "onResponse: json -> " + json);
                    int size = array.length();

                    for (int i = 0; i < size; i++) {
                        JSONObject object = array.getJSONObject(i);
                        Log.d(TAG, "onResponse: object -> " + object);

                        Light light = new Light();

                        light.setId(Long.parseLong(object.getString("id")));
                        light.setFullSpectrum(Double.parseDouble(object.getString("fullSpectrum")));
                        light.setVisibleSpectrum(Double.parseDouble(object.getString("visibleSpectrum")));
                        light.setInfraredSpectrum(Double.parseDouble(object.getString("infraredSpectrum")));
                        light.setTimeStamp(Long.parseLong(object.getString("timeStamp")));
                        light.setMeasureUnit(object.getString("measureUnit"));

                        lightsFromDB.add(light);
                        Log.d(TAG, "onResponse: added item -> " + light.getId());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lightAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                    Log.d(TAG, "onResponse: items in list -> " + lightsFromDB.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void reqCurrentLight(String url) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback(){
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d(TAG, "onResponse: server responded");
                try {

                    String json = response.body().string();
                    Log.d(TAG, "onResponse: json -> " + json);

                    JSONObject resObj = new JSONObject(json);

                    long id = resObj.getLong("id");
                    double fullSpectrum = resObj.getDouble("fullSpectrum");
                    double infraredSpectrum = resObj.getDouble("infraredSpectrum");
                    double visibleSpectrum = resObj.getDouble("visibleSpectrum");
                    long timeStamp = resObj.getLong("timeStamp");
                    String measureUnit = resObj.getString("measureUnit");

                    final Light light = new Light();
                    light.setId(id);
                    light.setMeasureUnit(measureUnit);
                    light.setTimeStamp(timeStamp);
                    light.setFullSpectrum(fullSpectrum);
                    light.setInfraredSpectrum(infraredSpectrum);
                    light.setVisibleSpectrum(visibleSpectrum);

                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            lights.add(light);
                            Log.d(TAG, "run: added sensor -> " + light.getId());
                        }});

                } catch(JSONException e) {

                }

                Log.d(TAG, "onResponse: done fetching data");
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });

    }
}
