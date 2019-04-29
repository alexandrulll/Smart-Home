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

public class DustAdapter extends RecyclerView.Adapter<DustAdapter.ViewHolder> {

    private List<Dust> dusts;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewId, textViewDustDensity, textViewDensityUnit, textViewVoltageUnit, textViewVoltage, textViewTimestamp;

        public ViewHolder(View view) {
            super(view);
            textViewId = view.findViewById(R.id.dustId);
            textViewDustDensity = view.findViewById(R.id.dustDesnsity);
            textViewTimestamp = view.findViewById(R.id.dustTimeStamp);
            textViewDensityUnit = view.findViewById(R.id.densityUnit);
            textViewVoltageUnit = view.findViewById(R.id.voltageUnit);
            textViewVoltage = view.findViewById(R.id.voltage);
        }
    }

    public DustAdapter(Context context, ArrayList<Dust> dusts) {
        this.dusts = dusts;
        this.context = context;
    }

    @NonNull
    @Override
    public DustAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.current_dust_item, viewGroup, false);

        return new DustAdapter.ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final DustAdapter.ViewHolder viewHolder, int i) {
        Dust dust = dusts.get(i);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);

        viewHolder.textViewId.setText(String.valueOf(dust.getId()));
        viewHolder.textViewDustDensity.setText(String.valueOf(dust.getDustDesnsity()));
        viewHolder.textViewTimestamp.setText(simpleDateFormat.format((dust.getTimeStamp())));
        viewHolder.textViewDensityUnit.setText(String.valueOf(dust.getDensityUnit()));
        viewHolder.textViewVoltage.setText(String.valueOf(dust.getVoltage()));
        viewHolder.textViewVoltageUnit.setText(String.valueOf(dust.getVoltageUnit()));
    }

    @Override
    public int getItemCount() {
        return dusts.size();
    }
}
