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

public class LightAdapter extends RecyclerView.Adapter<LightAdapter.ViewHolder> {

    private List<Light> lights;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewId, textViewFullSpectrum, textViewInfraredSpectrum, textViewVisibleSpectrum, textViewTimestamp, textViewMeasureUnit;

        public ViewHolder(View view) {
            super(view);
            textViewId = view.findViewById(R.id.lightId);
            textViewFullSpectrum = view.findViewById(R.id.fullSpectrumValue);
            textViewInfraredSpectrum = view.findViewById(R.id.infraredSpectrumValue);
            textViewVisibleSpectrum = view.findViewById(R.id.visibleSpectrumValue);
            textViewTimestamp = view.findViewById(R.id.lightTimeStamp);
            textViewMeasureUnit = view.findViewById(R.id.lightMeasureUnit);
        }
    }

    public LightAdapter(Context context, ArrayList<Light> lights) {
        this.lights = lights;
        this.context = context;
    }

    @NonNull
    @Override
    public LightAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.current_light_item, viewGroup, false);

        return new LightAdapter.ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final LightAdapter.ViewHolder viewHolder, int i) {
        Light light = lights.get(i);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);

        viewHolder.textViewId.setText(String.valueOf(light.getId()));
        viewHolder.textViewFullSpectrum.setText(String.valueOf(light.getFullSpectrum()));
        viewHolder.textViewInfraredSpectrum.setText(String.valueOf(light.getInfraredSpectrum()));
        viewHolder.textViewVisibleSpectrum.setText(String.valueOf(light.getVisibleSpectrum()));
        viewHolder.textViewTimestamp.setText(simpleDateFormat.format((light.getTimeStamp())));
        viewHolder.textViewMeasureUnit.setText(String.valueOf(light.getMeasureUnit()));

    }

    @Override
    public int getItemCount() {
        return lights.size();
    }
}
