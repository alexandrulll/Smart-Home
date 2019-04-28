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

public class TemperatureAdapter extends RecyclerView.Adapter<TemperatureAdapter.ViewHolder> {

    private List<Temperature> temperatures;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewId, textViewValue, textViewTimestamp, textViewSensorName, textViewMeasureUnit;

        public ViewHolder(View view) {
            super(view);
            textViewId = view.findViewById(R.id.temperatureId);
            textViewValue = view.findViewById(R.id.temperatureValue);
            textViewTimestamp = view.findViewById(R.id.temperatureTimeStamp);
            textViewSensorName = view.findViewById(R.id.temperatureSensorName);
            textViewMeasureUnit = view.findViewById(R.id.temperatureMeasureUnit);
        }
    }

    public TemperatureAdapter(Context context, ArrayList<Temperature> temperatures) {
        this.temperatures = temperatures;
        this.context = context;
    }

    @NonNull
    @Override
    public TemperatureAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.current_temperature_item, viewGroup, false);

        return new TemperatureAdapter.ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final TemperatureAdapter.ViewHolder viewHolder, int i) {
        Temperature temperature = temperatures.get(i);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);

        viewHolder.textViewId.setText(String.valueOf(temperature.getId()));
        viewHolder.textViewValue.setText(String.valueOf(temperature.getValue()));
        viewHolder.textViewTimestamp.setText(simpleDateFormat.format((temperature.getTimeStamp())));
        viewHolder.textViewMeasureUnit.setText(String.valueOf(temperature.getMeasureUnit()));
        viewHolder.textViewSensorName.setText(String.valueOf(temperature.getSensorName()));

    }

    @Override
    public int getItemCount() {
        return temperatures.size();
    }
}
