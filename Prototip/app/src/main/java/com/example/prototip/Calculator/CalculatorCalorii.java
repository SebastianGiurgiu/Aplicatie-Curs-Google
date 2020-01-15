package com.example.prototip.Calculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototip.R;

public class CalculatorCalorii extends AppCompatActivity {

    int ages;
    private SeekBar seekBarAge;
    private TextView textViewAge;
    private CheckBox checkBoxM;
    private CheckBox checkBoxF;
    private CheckBox checkBoxAnother;
    private String gender;
    private Button calculateButton;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_calorii);


        Spinner spinner = findViewById(R.id.spinnerPurpose);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.purposes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        calculateButton = findViewById(R.id.calculateButton);
        seekBarAge = findViewById(R.id.seekBarAge);
        textViewAge = findViewById(R.id.textViewAge);
        checkBoxM = findViewById(R.id.checkbox_masculin);
        checkBoxF = findViewById(R.id.checkbox_feminim);
        checkBoxAnother = findViewById(R.id.checkbox_another);
        textViewAge.setText(String.valueOf(15));
        seekBarAge.setMax(80);
        seekBarAge.setMin(15);

        checkBoxM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "masculin";
                checkBoxF.setChecked(false);
                checkBoxAnother.setChecked(false);
            }
        });

        checkBoxF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "feminim";
                checkBoxM.setChecked(false);
                checkBoxAnother.setChecked(false);
            }
        });

        checkBoxAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast.makeText(CalculatorCalorii.this,"You are not funny",Toast.LENGTH_SHORT).show();
               checkBoxAnother.setChecked(false);
            }
        });

        seekBarAge.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    int progress_value;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;
                        textViewAge.setText(String.valueOf(progress));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textViewAge.setText(String.valueOf(progress_value));
                        Toast.makeText(CalculatorCalorii.this,gender,Toast.LENGTH_SHORT).show();
                       // textViewGreutateSeek.setText("Dsadsadsadsads");
                    }
                }
        );


        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CalculatorCalorii.this,"The magic number is : 2200",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
