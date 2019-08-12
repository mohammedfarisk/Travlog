package com.example.travlog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;

import com.google.firebase.database.core.view.View;

import java.lang.String;

public class AddNewLocation extends AppCompatActivity{

    Spinner continentSpinner, countrySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_new_location);

        //continents spinner
        continentSpinner = (Spinner) findViewById(R.id.continentsSpinner);
        countrySpinner = (Spinner) findViewById(R.id.countriesSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.continents_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        continentSpinner.setAdapter(adapter);


        continentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, android.view.View view, int i, long l) {
                String text = continentSpinner.getSelectedItem().toString();
                switch (text){
                    case "Africa":
                        ArrayAdapter<CharSequence> adapter_country_africa = ArrayAdapter.createFromResource(AddNewLocation.this.getApplicationContext(), R.array.countries_africa_array, android.R.layout.simple_spinner_item);
                        adapter_country_africa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        countrySpinner.setAdapter(adapter_country_africa);
                        break;
                    case "Asia":
                        ArrayAdapter<CharSequence> adapter_country_asia = ArrayAdapter.createFromResource(AddNewLocation.this.getApplicationContext(), R.array.countries_asia_array, android.R.layout.simple_spinner_item);
                        adapter_country_asia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        countrySpinner.setAdapter(adapter_country_asia);
                        break;
                    case "Europe":
                        ArrayAdapter<CharSequence> adapter_country_europe = ArrayAdapter.createFromResource(AddNewLocation.this.getApplicationContext(), R.array.countries_europe_array, android.R.layout.simple_spinner_item);
                        adapter_country_europe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        countrySpinner.setAdapter(adapter_country_europe);
                        break;
                    case "North America":
                        ArrayAdapter<CharSequence> adapter_country_north_america = ArrayAdapter.createFromResource(AddNewLocation.this.getApplicationContext(), R.array.countries_north_america_array, android.R.layout.simple_spinner_item);
                        adapter_country_north_america.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        countrySpinner.setAdapter(adapter_country_north_america);
                        break;
                    case "Australia":
                        ArrayAdapter<CharSequence> adapter_country_australia = ArrayAdapter.createFromResource(AddNewLocation.this.getApplicationContext(), R.array.countries_australia_array, android.R.layout.simple_spinner_item);
                        adapter_country_australia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        countrySpinner.setAdapter(adapter_country_australia);
                        break;
                    case "South America":
                        ArrayAdapter<CharSequence> adapter_country_south_america = ArrayAdapter.createFromResource(AddNewLocation.this.getApplicationContext(), R.array.countries_south_america_array, android.R.layout.simple_spinner_item);
                        adapter_country_south_america.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        countrySpinner.setAdapter(adapter_country_south_america);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        //countries spinner

    }
}
