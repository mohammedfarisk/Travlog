package com.example.travlog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;

import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;

public class AddNewLocation extends AppCompatActivity{

    Spinner continentSpinner, countrySpinner, statesSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_new_location);

        //continents spinner
        continentSpinner = (Spinner) findViewById(R.id.continentsSpinner);
        countrySpinner = (Spinner) findViewById(R.id.countriesSpinner);
        statesSpinner = (Spinner) findViewById(R.id.spinnerStates);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.continents_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        continentSpinner.setAdapter(adapter);


        continentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, android.view.View view, int i, long l) {

                //getting name of selected continent and removing whitespace and changing complete name to lowercase
                String continent = continentSpinner.getSelectedItem().toString();

                String text = continent.replaceAll("\\s+", "");
                text = text.toLowerCase();

                //creating name of corresponding countrie's xml string array
                String continentName = "countries_"+text+"_array";

                //fetching the list of countries of selected continent and storing it in to an array
                int countriesArrayID= getResources().getIdentifier(continentName , "array",AddNewLocation.this.getPackageName());
                String[] items = getResources().getStringArray(countriesArrayID);

                //showing list of countries as spinner dropdown items
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AddNewLocation.this.getApplicationContext(),   android.R.layout.simple_spinner_item, items);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                countrySpinner.setAdapter(spinnerArrayAdapter);

                countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        String country = countrySpinner.getSelectedItem().toString();

                        country = country.replaceAll("\\s+", "");
                        country = country.toLowerCase();
                        String stateName = "states_"+country+"_array";

                        int statesArrayID= getResources().getIdentifier(stateName , "array",AddNewLocation.this.getPackageName());
                        String[] items = getResources().getStringArray(statesArrayID);
                        System.out.println("STATE = "+items[0]);

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AddNewLocation.this.getApplicationContext(),   android.R.layout.simple_spinner_item, items);
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                        statesSpinner.setAdapter(spinnerArrayAdapter);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        //countries spinner

    }
}
