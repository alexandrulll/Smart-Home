package com.example.licenta;

import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    private Button getLastMotion;
    private Button startMotionDetectorSensor;

    private ArrayList<MotionSensor> motionSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supraveghere);

        Log.d(TAG, "onCreate: activity created");

        motionSensors = new ArrayList<>();

        reqMotionSensorEntrance("http://192.168.100.11:8080/motion/latest");
        getLastMotion = (Button) findViewById(R.id.motionListButton);
        getLastMotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: button clicked");
                startActivity(new Intent(SupraveghereActivity.this, MotionSensorList.class)
                        .putExtra("motion_sensors", motionSensors));
            }
        });

        startMotionDetectorSensor = (Button) findViewById(R.id.startMotionSensor);
        startMotionDetectorSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(findViewById(R.id.coordinatorSupraveghere), R.string.startMotionSensorDetect,
                        Snackbar.LENGTH_LONG)
                        .show();
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
                Log.d(TAG, "onResponse: server responded");
                try {

                    String json = response.body().string();
                    Log.d(TAG, "onResponse: json -> " + json);
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
                            Log.d(TAG, "run: added sensor -> " + motionSensor.getId());
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
