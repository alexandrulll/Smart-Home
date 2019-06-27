package com.example.licenta;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import cz.msebera.android.httpclient.Header;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "HumidityListActivity";

    private Button controlButton;
    private Button achizitieButton;
    private Button supraveghereButton;
    private Button configurare;

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

        reqAlarmEntries("http://192.168.100.11:8080/api/alarms");

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

        configurare = findViewById(R.id.btn_config);

        configurare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlarmConfigDialog dialog = new AlarmConfigDialog(MainActivity.this);
                dialog.create();
                dialog.show();

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (dialog.isDonePressed()) {
                            TemperatureAlarmConfig config = new TemperatureAlarmConfig();
                            config.setAlarmLabel(dialog.getLabel());
                            config.setGeneratingEntity(dialog.getGenEntityVal());
                            config.setValue(dialog.getVal());
                            config.setValue(1);

                            updateAlarmConfig("http://192.168.100.11:8080/api/alarms/config", config);
                        }
                    }
                });
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

    private void updateAlarmConfig(String url, TemperatureAlarmConfig config) {
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();

        params.put("value", config.getValue());
        params.put("alarmLabel", config.getAlarmLabel());
        params.put("generatingEntity", config.getGeneratingEntity());
        params.put("id", config.getId());
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {

                    Log.d(TAG, "json " + params);

                    Intent returnIntent = getIntent();
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
