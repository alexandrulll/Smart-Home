package com.example.licenta;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.AppLaunchChecker;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {

    private List<Alarm> alarms;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewId, textViewLabel, textViewEntity, textViewValue;

        public ViewHolder(View view) {
            super(view);
            textViewId = view.findViewById(R.id.alarmId);
            textViewLabel = view.findViewById(R.id.alarmLabel);
            textViewEntity = view.findViewById(R.id.generatingEntity);
            textViewValue = view.findViewById(R.id.generatingValue);
        }
    }

    public AlarmAdapter(Context context, ArrayList<Alarm> alarms) {
        this.alarms = alarms;
        this.context = context;
    }

    @NonNull
    @Override
    public AlarmAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.current_alarm_item, viewGroup, false);

        return new AlarmAdapter.ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final AlarmAdapter.ViewHolder viewHolder, int i) {
        Alarm alarm = alarms.get(i);

        viewHolder.textViewId.setText(String.valueOf(alarm.getId()));
        viewHolder.textViewEntity.setText(String.valueOf(alarm.getGeneratingEntity()));
        viewHolder.textViewLabel.setText(String.valueOf(alarm.getAlarmLabel()));
        viewHolder.textViewValue.setText(String.valueOf(alarm.getGeneratingValue()));
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }
}
