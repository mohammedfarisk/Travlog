package com.example.travlog;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class AddNewLocation extends AppCompatActivity{

    DatabaseHelper travlogDB;

    Spinner continentSpinner, countrySpinner, statesSpinner;

    List<String> db_table_result_rows_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_new_location);

        travlogDB = new DatabaseHelper(this);


        //continents spinner
        continentSpinner = (Spinner) findViewById(R.id.continentsSpinner);
        countrySpinner = (Spinner) findViewById(R.id.countriesSpinner);
        statesSpinner = (Spinner) findViewById(R.id.spinnerStates);


//        String continentArray = "countries_southamerica_array";
//        int countriesArrayID= getResources().getIdentifier(continentArray , "array",AddNewLocation.this.getPackageName());
//        String[] continents = getResources().getStringArray(countriesArrayID);
//
//        travlogDB.createTable();

//        for(int i=0; i<15; i++)
//        {
//            travlogDB.insertDataCountries(continents[i]);
//        }



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

                        String table_name = "states";
                        String column_to_fetch = "state";
                        String column_to_serach = "country";
                        String[] value_to_search = {country};

                        db_table_result_rows_list = travlogDB.getAllQueriedRows(column_to_fetch, table_name, column_to_serach, value_to_search);


//                        country = country.replaceAll("\\s+", "");
//                        country = country.toLowerCase();
//                        String stateName = "states_"+country+"_array";
//
//                        int statesArrayID= getResources().getIdentifier(stateName , "array",AddNewLocation.this.getPackageName());
//                        String[] items = getResources().getStringArray(statesArrayID);
//                        System.out.println("STATE = "+items[0]);

                        db_table_result_rows_list.add("Add State +");

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AddNewLocation.this.getApplicationContext(),   android.R.layout.simple_spinner_item, db_table_result_rows_list);
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view

                        if(statesSpinner.getSelectedItem().toString() == "Add State +")
                        {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                            alertDialog.setTitle("NEW STATE");
                            alertDialog.setMessage("Enter Name of State");
                        }
                        else
                        {
                            statesSpinner.setAdapter(spinnerArrayAdapter);
                        }
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

    }
}
