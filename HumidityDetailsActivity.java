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

public class HumidityDetailsActivity extends AppCompatActivity {

    public static final String TAG = "HumidityDetailsActivity";

    private Button currentHumidityButton;
    private Button humidityListButton;

    private HumidityAdapter humidityAdapter;

    private ArrayAdapter<String> adapter;
    private ArrayList<Humidity> humidities;
    private ArrayList<Humidity> humiditiesFromDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity_details);

        humidities = new ArrayList<>();
        humiditiesFromDB = new ArrayList<>();

        humidityAdapter = new HumidityAdapter(this, humidities);

        reqCurrentHumidity("http://192.168.100.11:8080/humidity/save");

    }

    private void reqHumidityEntries(String url) {

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
                        Humidity humidity = new Humidity();

                        humidity.setId(Long.parseLong(object.getString("id")));
                        humidity.setValue(Double.parseDouble(object.getString("value")));
                        humidity.setSensorName(object.getString("sensorName"));
                        humidity.setTimeStamp(Long.parseLong(object.getString("timeStamp")));
                        humidity.setMeasureUnit(object.getString("measureUnit"));

                        humiditiesFromDB.add(humidity);
                        Log.d(TAG, "onResponse: added item -> " + humidity.getId());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                humidityAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                    Log.d(TAG, "onResponse: items in list -> " + humiditiesFromDB.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                humidityListButton = (Button) findViewById(R.id.humidityListButton);
                humidityListButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(HumidityDetailsActivity.this, HumidityListActivity.class)
                                .putExtra("humidity_list", humiditiesFromDB));
                    }
                });

            }
        });
    }


    private void reqCurrentHumidity(String url) {

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

                    final Humidity humidity = new Humidity();
                    humidity.setId(id);
                    humidity.setMeasureUnit(measureUnit);
                    humidity.setTimeStamp(timeStamp);
                    humidity.setSensorName(sensorName);
                    humidity.setValue(value);

                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            humidities.add(humidity);
                            humidityAdapter.notifyDataSetChanged();
                            Log.d(TAG, "run: added sensor -> " + humidity.getId());

                            currentHumidityButton = (Button) findViewById(R.id.currentHumidityButton);
                            currentHumidityButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    reqHumidityEntries("http://192.168.100.11:8080/humidity/all");
                                    startActivity(new Intent(HumidityDetailsActivity.this, CurrentHumidity.class)
                                            .putExtra("current_humidity", humidities));

                                }
                            });
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
