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

public class TemperatureDetailsActivity extends AppCompatActivity {

    public static final String TAG = "TemperatureDetaiActivit";

    private Button currentTemperatureButton;
    private Button temperatureListButton;

    private TemperatureAdapter temperatureAdapter;

    private ArrayAdapter<String> adapter;
    private ArrayList<Temperature> temperatures;
    private ArrayList<Temperature> temperaturesFromDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_details);

        temperatures = new ArrayList<>();
        temperaturesFromDB = new ArrayList<>();

        temperatureAdapter = new TemperatureAdapter(this, temperatures);

        reqCurrentTemperature("http://192.168.100.11:8080/temperature/save");
    }

    private void reqTemperatureEntries(String url) {

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
                        Temperature temperature = new Temperature();

                        temperature.setId(Long.parseLong(object.getString("id")));
                        temperature.setValue(Double.parseDouble(object.getString("value")));
                        temperature.setSensorName(object.getString("sensorName"));
                        temperature.setTimeStamp(Long.parseLong(object.getString("timeStamp")));
                        temperature.setMeasureUnit(object.getString("measureUnit"));

                        temperaturesFromDB.add(temperature);
                        Log.d(TAG, "onResponse: added item -> " + temperature.getId());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                temperatureAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                    Log.d(TAG, "onResponse: items in list -> " + temperaturesFromDB.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                temperatureListButton = (Button) findViewById(R.id.temperatureListButton);
                temperatureListButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(TemperatureDetailsActivity.this, TemperatureListActivity.class)
                                .putExtra("temperature_list", temperaturesFromDB));
                    }
                });
            }
        });
    }


    private void reqCurrentTemperature(String url) {

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
                    double value = resObj.getDouble("value");
                    long timeStamp = resObj.getLong("timeStamp");
                    String sensorName = resObj.getString("sensorName");
                    String measureUnit = resObj.getString("measureUnit");

                    final Temperature temperature = new Temperature();
                    temperature.setId(id);
                    temperature.setMeasureUnit(measureUnit);
                    temperature.setTimeStamp(timeStamp);
                    temperature.setSensorName(sensorName);
                    temperature.setValue(value);

                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            temperatures.add(temperature);
                            Log.d(TAG, "run: added sensor -> " + temperature.getId());
                        }});

                } catch(JSONException e) {

                }

                currentTemperatureButton = (Button) findViewById(R.id.currentTemperatureButton);
                currentTemperatureButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reqTemperatureEntries("http://192.168.100.11:8080/temperature/all");
                        startActivity(new Intent(TemperatureDetailsActivity.this, CurrentTemperatureActivity.class)
                                .putExtra("current_temperature", temperatures));

                    }
                });

                Log.d(TAG, "onResponse: done fetching data");
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });

    }
}
