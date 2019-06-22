package com.example.licenta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity {

    private Button controlButton;
    private Button achizitieButton;
    private Button supraveghereButton;

    private AlarmAdapter alarmAdapter;
    private ArrayAdapter<String> adapter;

    private ArrayList<MotionSensor> motionSensors;
    private ArrayList<Alarm> alarms;
    private ArrayList<Alarm> alarmsFromDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        motionSensors = new ArrayList<>();
        alarmsFromDB = new ArrayList<>();
        alarms = new ArrayList<>();

        alarmAdapter = new AlarmAdapter(this, alarms);

        reqAlarmEntries("http://192.168.100.11:8080/alarms");

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.motionSensorStart:
                reqStartMotionSensor("http://192.168.100.11:8080/motion/start");
                return  true;
            case R.id.alarmList:
                startActivity(new Intent(MainActivity.this, AlarmListActivity.class)
                        .putExtra("alarm_list", alarmsFromDB));
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void reqAlarmEntries(String url) {

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
                    int size = array.length();

                    for (int i = 0; i < size; i++) {
                        JSONObject object = array.getJSONObject(i);
                        Alarm alarm = new Alarm();

                        alarm.setId(Long.parseLong(object.getString("id")));
                        alarm.setAlarmLabel(object.getString("alarmLabel"));
                        alarm.setGeneratingEntity(object.getString("generatingEntity"));
                        alarm.setGeneratingValue(object.getString("generatingValue"));

                        alarmsFromDB.add(alarm);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alarmAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void reqStartMotionSensor(String url) {

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

                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                        }});

                } catch(Exception e) {

                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });

    }

}
