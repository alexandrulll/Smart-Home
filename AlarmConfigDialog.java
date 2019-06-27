package com.example.licenta;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class AlarmConfigDialog extends Dialog {
    private EditText value;
    private EditText alarmLabel;
    private EditText generatingEntity;
    private Button cancel;
    private Button done;

    private boolean donePressed = false;
    private double val;
    private String genEntityVal, genValue, label;
    private Context context;

    public AlarmConfigDialog(@NonNull @android.support.annotation.NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_alarm_config);

        bindViews();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                donePressed = false;
                dismiss();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                donePressed = true;

                genEntityVal = generatingEntity.getText().toString();
                label = alarmLabel.getText().toString();

                if (value.getText().toString().equals("") ||
                        genEntityVal.equals("") || label.equals("")) {
                    Toast.makeText(context, "One or more fields are empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    val = Double.parseDouble(value.getText().toString());
                    dismiss();
                }
            }
        });
    }

    private void bindViews() {
        value = findViewById(R.id.et_value);
        alarmLabel = findViewById(R.id.et_alarm_label);
        generatingEntity = findViewById(R.id.et_generating_entity);
        cancel = findViewById(R.id.btn_cancel);
        done = findViewById(R.id.btn_done);
    }

    public boolean isDonePressed() {
        return donePressed;
    }

    public double getVal() {
        return val;
    }

    public String getGenEntityVal() {
        return genEntityVal;
    }

    public String getGenValue() {
        return genValue;
    }

    public String getLabel() {
        return label;
    }
}
