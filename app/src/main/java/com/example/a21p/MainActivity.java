package com.example.a21p;

import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Spinner sourceUnitSpinner, destinationUnitSpinner;
    private EditText valueEditText;
    private Button convertButton;
    private TextView resultTextView;

    private HashMap<String, String> mapping = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mapping.put("Inch", "Length");
        mapping.put("Foot", "Length");
        mapping.put("Yard", "Length");
        mapping.put("Mile", "Length");
        mapping.put("Pound", "Weight");
        mapping.put("Ounce", "Weight");
        mapping.put("Top", "Weight");
        mapping.put("Celsius", "Temperature");
        mapping.put("Fahrenheit", "Temperature");
        mapping.put("Kelvin", "Temperature");

        sourceUnitSpinner = findViewById(R.id.sourceUnitSpinner);
        destinationUnitSpinner = findViewById(R.id.descUnitSpinner);
        valueEditText = findViewById(R.id.valueEditText);
        convertButton = findViewById(R.id.convertButton);
        resultTextView = findViewById(R.id.resultTextView);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageConversion();
            }
        });
    }

    private void manageConversion() {
        if (valueEditText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }
        String sourceUnit = sourceUnitSpinner.getSelectedItem().toString();
        String destinationUnit = destinationUnitSpinner.getSelectedItem().toString();
        double value = Double.parseDouble(valueEditText.getText().toString());
        double result = 0;
        if(sourceUnit.equalsIgnoreCase(destinationUnit)) {
            Toast.makeText(getApplicationContext(), "Cannot convert " + sourceUnit + " to " + destinationUnit, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!mapping.get(sourceUnit).equalsIgnoreCase(mapping.get(destinationUnit))) {
            String error = "Cannot convert " + mapping.get(sourceUnit) + " to " + mapping.get(destinationUnit);
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            return;
        }

        if (mapping.get(sourceUnit).equalsIgnoreCase("Length")) {
            result = convertLength(value, sourceUnit, destinationUnit);
        }else if (mapping.get(sourceUnit).equalsIgnoreCase("Weight")){
            result = convertWeight(value, sourceUnit, destinationUnit);
        }else {
            result = convertTemperature(value, sourceUnit, destinationUnit);
        }

        resultTextView.setText(String.valueOf(result));
    }

    private double convertLength(double value, String sourceUnit, String destinationUnit) {
        double inches;
        switch (sourceUnit) {
            case "Inch":
                inches = value;
                break;
            case "Foot":
                inches = value * 12;
                break;
            case "Yard":
                inches = value * 36;
                break;
            case "Mile":
                inches = value * 63360;
                break;
            default:
                return 0;
        }

        double result;
        switch (destinationUnit) {
            case "Inch":
                result = inches;
                break;
            case "Foot":
                result = inches / 12;
                break;
            case "Yard":
                result = inches / 36;
                break;
            case "Mile":
                result = inches / 63360;
                break;
            default:
                return 0;
        }
        return result;
    }

    private double convertWeight(double value, String sourceUnit, String destinationUnit) {
        double pounds = 0;

        switch (sourceUnit) {
            case "Pound":
                pounds = value;
                break;
            case "Ounce":
                pounds = value / 16;
                break;
            case "Ton":
                pounds = value * 2000;
                break;
        }
        switch (destinationUnit) {
            case "Pound":
                return pounds;
            case "Ounce":
                return pounds * 16;
            case "Ton":
                return pounds / 2000;
            default:
                return 0;
        }
    }

    private double convertTemperature(double value, String sourceUnit, String destinationUnit) {
        double celsiusValue = 0;
        switch (sourceUnit) {
            case "Celsius":
                celsiusValue = value;
                break;
            case "Fahrenheit":
                celsiusValue = (value - 32) / 1.8;
                break;
            case "Kelvin":
                celsiusValue = value - 273.15;
                break;
        }
        switch (destinationUnit) {
            case "Celsius":
                return celsiusValue;
            case "Fahrenheit":
                return (celsiusValue * 1.8) + 32;
            case "Kelvin":
                return celsiusValue + 273.15;
            default:
                return 0;
        }
    }
}