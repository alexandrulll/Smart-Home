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

public class DustDetailsActivity extends AppCompatActivity {

    public static final String TAG = "DustDetails";

    private Button currentDustButton;
    private Button dustListButton;

    private DustAdapter dustAdapter;

    private ArrayAdapter<String> adapter;
    private ArrayList<Dust> dusts;
    private ArrayList<Dust> dustsFromDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dust_details);

        dusts = new ArrayList<>();
        dustsFromDB = new ArrayList<>();

        dustAdapter = new DustAdapter(this, dusts);

        reqCurrentDust("http://192.168.100.11:8080/dust/save");

    }

    private void reqDustEntries(String url) {

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
                        Dust dust = new Dust();

                        dust.setId(Long.parseLong(object.getString("id")));
                        dust.setDensityUnit(object.getString("densityUnit"));
                        dust.setDustDesnsity(Float.parseFloat(object.getString("dustDesnsity")));
                        dust.setVoltageUnit(object.getString("voltageUnit"));;
                        dust.setVoltage(Float.parseFloat(object.getString("voltage")));
                        dust.setTimeStamp(Long.parseLong(object.getString("timeStamp")));

                        dustsFromDB.add(dust);
                        Log.d(TAG, "onResponse: added item -> " + dust.getId());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dustAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                    Log.d(TAG, "onResponse: items in list -> " + dustsFromDB.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dustListButton = (Button) findViewById(R.id.dustListButton);
                dustListButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(DustDetailsActivity.this, DustListActivity.class)
                                .putExtra("dust_list", dustsFromDB));
                    }
                });

            }
        });
    }


    private void reqCurrentDust(String url) {

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
                    float dustDesnsity = (float) resObj.getLong("dustDesnsity");
                    float voltage = (float) resObj.getLong("voltage");
                    long timeStamp = resObj.getLong("timeStamp");
                    String densityUnit = resObj.getString("densityUnit");
                    String voltageUnit = resObj.getString("voltageUnit");

                    final Dust dust = new Dust();

                    dust.setId(id);
                    dust.setVoltage(voltage);
                    dust.setDustDesnsity(dustDesnsity);
                    dust.setDensityUnit(densityUnit);
                    dust.setVoltageUnit(voltageUnit);
                    dust.setTimeStamp(timeStamp);

                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            dusts.add(dust);
                            dustAdapter.notifyDataSetChanged();
                            Log.d(TAG, "run: added sensor -> " + dust.getId());

                            currentDustButton = (Button) findViewById(R.id.currentDustButton);
                            currentDustButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    reqDustEntries("http://192.168.100.11:8080/dust/all");
                                    startActivity(new Intent(DustDetailsActivity.this, CurrentDustActivity.class)
                                            .putExtra("current_dust", dusts));

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
