package com.example.licenta;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Random;

public class TemperatureListActivity extends AppCompatActivity {

    public static final String TAG = "TemperatureListActivity";
    private final int fillColor = Color.argb(150, 51, 181, 229);

    private RecyclerView recyclerView;
    private LineChart chart;
    private CheckBox displayGraph;

    private ArrayList<Temperature> temperatures;
    private TemperatureAdapter adapter;
    private Intent parentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_list);

        parentIntent = getIntent();

        temperatures = (ArrayList<Temperature>) parentIntent.getSerializableExtra("temperature_list");
        adapter = new TemperatureAdapter(this, temperatures);

        Log.d(TAG, "onCreate: activity created -> " + temperatures.size());

        bindViews();
        visualChartSetup();

        displayGraph.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    chart.setVisibility(View.VISIBLE);
                }
                else {
                    chart.setVisibility(View.GONE);
                }
            }
        });

        setData(temperatures);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    private void bindViews() {
        recyclerView = findViewById(R.id.rv_temperature_list);
        chart = findViewById(R.id.chart1);
        displayGraph = findViewById(R.id.cb_show_graph);
    }


    private void visualChartSetup() {
        chart.setViewPortOffsets(0, 0, 0, 0);
        chart.setBackgroundColor(Color.rgb(104, 241, 175));

        chart.getDescription().setEnabled(false);

        chart.setTouchEnabled(true);

        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);
        chart.setMaxHighlightDistance(300);

        XAxis x = chart.getXAxis();
        x.setDrawGridLines(true);
        x.setTextSize(9f);
        x.setTextColor(Color.BLACK);

        YAxis y = chart.getAxisLeft();
        y.setLabelCount(6, false);
        y.setTextColor(Color.BLACK);
        y.setTextSize(12f);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(true);
        y.setAxisLineColor(Color.WHITE);

        chart.getAxisRight().setEnabled(false);

        chart.getLegend().setEnabled(false);

        chart.invalidate();
    }

    private void setData(ArrayList<Temperature> temperatures) {

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < temperatures.size(); i++) {
            Temperature temperature = temperatures.get(i);
            values.add(new Entry(i, (float) temperature.getValue()));
        }

        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.BLACK);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.WHITE);
            set1.setFillColor(Color.WHITE);
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            LineData data = new LineData(set1);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            chart.setData(data);
        }
    }
}
