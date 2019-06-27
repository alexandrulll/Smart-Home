package com.example.licenta;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HumidityAdapter extends RecyclerView.Adapter<HumidityAdapter.ViewHolder> {

    private List<Humidity> humidities;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewId, textViewValue, textViewTimestamp, textViewSensorName, textViewMeasureUnit;

        public ViewHolder(View view) {
            super(view);
            textViewId = view.findViewById(R.id.humidityId);
            textViewValue = view.findViewById(R.id.humidityValue);
            textViewTimestamp = view.findViewById(R.id.humidityTimeStamp);
            textViewSensorName = view.findViewById(R.id.humiditySensorName);
            textViewMeasureUnit = view.findViewById(R.id.humidityMeasureUnit);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final HumidityAdapter.ViewHolder viewHolder, int i) {
        Humidity humidity = humidities.get(i);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);

        viewHolder.textViewId.setText(String.valueOf(humidity.getId()));
        viewHolder.textViewValue.setText(String.valueOf(humidity.getValue()));
        viewHolder.textViewTimestamp.setText(simpleDateFormat.format((humidity.getTimeStamp())));
        viewHolder.textViewMeasureUnit.setText(String.valueOf(humidity.getMeasureUnit()));
        viewHolder.textViewSensorName.setText(String.valueOf(humidity.getSensorName()));

    }

    public HumidityAdapter(Context context, ArrayList<Humidity> humidities) {
        this.humidities = humidities;
        this.context = context;
    }

    @NonNull
    @Override
    public HumidityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.current_humidity_item, viewGroup, false);

        return new HumidityAdapter.ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return humidities.size();
    }
}
