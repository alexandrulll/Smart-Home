package com.example.licenta;

import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.util.Log;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONException;

import cz.msebera.android.httpclient.Header;

public class SupraveghereActivity extends AppCompatActivity {
    public static final String TAG = "SupraveghereActivity";

    private Button listAllMotions;

    private SupraveghereAdapter supraveghereAdapter;
    private ArrayAdapter<String> adapter;

    private ArrayList<MotionSensor> motionSensors;
    private ArrayList<MotionSensor> motionSensorsFromDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supraveghere);

        motionSensors = new ArrayList<>();
        motionSensorsFromDB = new ArrayList<>();

        supraveghereAdapter = new SupraveghereAdapter(this, motionSensors);

        reqMotionSensorEntries("http://192.168.100.11:8080/motion/all");

        listAllMotions = (Button) findViewById(R.id.motionListButton);
        listAllMotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SupraveghereActivity.this, MotionSensorList.class)
                        .putExtra("motion_sensors", motionSensorsFromDB));
            }
        });

    }

    private void reqMotionSensorEntries(String url) {

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
                        MotionSensor motionSensor = new MotionSensor();

                        motionSensor.setId(Long.parseLong(object.getString("id")));
                        motionSensor.setAlertText(object.getString("alertText"));
                        motionSensor.setTimeStamp(Long.parseLong(object.getString("timeStamp")));

                        motionSensorsFromDB.add(motionSensor);
                        Log.d(TAG, "onResponse: added item -> " + motionSensor.getId());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                supraveghereAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                    Log.d(TAG, "onResponse: items in list -> " + motionSensorsFromDB.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
