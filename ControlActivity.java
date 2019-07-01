package com.example.licenta;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ControlActivity extends AppCompatActivity {

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.buttonFirstRelayOn:
                if (checked)

                    reqRelayFirstSwitchOn("http://192.168.0.100:8080/relay/27/true");
                    Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.firstRelayOn,
                            Snackbar.LENGTH_LONG)
                            .show();
                break;
            case R.id.buttonFirstRelayOff:
                if (checked)

                    reqRelayFirstSwitchOff("http://192.168.0.100:8080/relay/27/false");
                    Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.firstRelayOff,
                            Snackbar.LENGTH_LONG)
                            .show();
                break;
            case R.id.buttonSecondRelayOn:
                if (checked)

                    reqRelaySecondSwitchOn("http://192.168.0.100:8080/relay/26/true");
                    Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.secondRelayOn,
                            Snackbar.LENGTH_LONG)
                            .show();
                break;
            case R.id.buttonSecondRelayOff:
                if (checked)

                    reqRelaySecondSwitchOff("http://192.168.0.100:8080/relay/26/false");
                    Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.secondRelayOff,
                            Snackbar.LENGTH_LONG)
                            .show();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

    }

    private void reqRelayFirstSwitchOn(String url) {

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

    private void reqRelayFirstSwitchOff(String url) {

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

    private void reqRelaySecondSwitchOn(String url) {

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

    private void reqRelaySecondSwitchOff(String url) {

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
