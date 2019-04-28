package com.example.licenta;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SupraveghereAdapter extends RecyclerView.Adapter<SupraveghereAdapter.ViewHolder> {
    private List<MotionSensor> motionSensors;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewId, textViewAlert, textViewTimestamp;

        public ViewHolder(View view) {
            super(view);
            textViewId = view.findViewById(R.id.sensorId);
            textViewAlert = view.findViewById(R.id.sensorAlertText);
            textViewTimestamp = view.findViewById(R.id.senorTimestamp);

        }
    }

    public SupraveghereAdapter(Context context, ArrayList<MotionSensor> motionSensors) {
        this.motionSensors = motionSensors;
        this.context = context;
    }

    @NonNull
    @Override
    public SupraveghereAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.motion_list_item, viewGroup, false);

        return new SupraveghereAdapter.ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        MotionSensor motionSensor = motionSensors.get(i);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);

        viewHolder.textViewId.setText(String.valueOf(motionSensor.getId()));
        viewHolder.textViewAlert.setText(motionSensor.getAlertText());
        viewHolder.textViewTimestamp.setText(simpleDateFormat.format((motionSensor.getTimeStamp())));

    }

    @Override
    public int getItemCount() {
        return motionSensors.size();
    }
}
