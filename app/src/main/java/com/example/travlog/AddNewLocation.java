package com.example.travlog;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class AddNewLocation extends AppCompatActivity{

    DatabaseHelper travlogDB;

    Spinner continentSpinner, countrySpinner, statesSpinner, districtsSpinner, spinnerNearestCities, spinnerNearestTouristsSpot;

    Button buttonSubmit;
    EditText editTextLocationName, editTextDistanceFromBangalore, editTextDistanceFromHome, editTextGoogleMapLocation;


    String state, table_name, column_to_fetch, column_to_serach, district, country, continent, location_name, nearest_city, google_map_location, nearest_tourist_spot;
    Integer distance_from_bangalore, distance_from_home;
    String[] value_to_search;

    List<String> db_table_result_rows_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_new_location);

        state = "";
        continent = "";
        country = "";
        district = "";
        table_name = "";
        column_to_fetch = "";
        column_to_serach = "";
        location_name = "";
        nearest_city = "";
        distance_from_bangalore = 0;
        distance_from_home = 0;
        google_map_location = "";
        nearest_tourist_spot = "";

        travlogDB = new DatabaseHelper(this);


        //continents spinner
        continentSpinner = (Spinner) findViewById(R.id.continentsSpinner);
        countrySpinner = (Spinner) findViewById(R.id.countriesSpinner);
        statesSpinner = (Spinner) findViewById(R.id.spinnerStates);
        districtsSpinner = (Spinner) findViewById(R.id.spinnerDistrict);
        spinnerNearestCities = (Spinner) findViewById(R.id.spinnerNearestCities);
        spinnerNearestTouristsSpot = (Spinner) findViewById(R.id.nearestTouristSpotsSpinner);
        buttonSubmit = (Button) findViewById(R.id.btn_submit_new_location);
        editTextLocationName = (EditText) findViewById(R.id.editTextLocationName);
        editTextDistanceFromBangalore = (EditText) findViewById(R.id.editTextDistanceFromBangalore);
        editTextDistanceFromHome = (EditText) findViewById(R.id.editTextDistanceFromHome);
        editTextGoogleMapLocation = (EditText) findViewById(R.id.editTextGoogleMapLocation);


        //        String continentArray = "countries_southamerica_array";
//        int countriesArrayID= getResources().getIdentifier(continentArray , "array",AddNewLocation.this.getPackageName());
//        String[] continents = getResources().getStringArray(countriesArrayID);
//
//        travlogDB.createTable();

//        for(int i=0; i<15; i++)
//        {
//            travlogDB.insertDataCountries(continents[i]);
//        }


        location_name = editTextLocationName.getText().toString();
        System.out.println("LOCATION NAME="+location_name);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.continents_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        continentSpinner.setAdapter(adapter);


        continentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, android.view.View view, int i, long l) {

                //getting name of selected continent and removing whitespace and changing complete name to lowercase
                continent = continentSpinner.getSelectedItem().toString();

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

                        country = countrySpinner.getSelectedItem().toString();

                        statesSpinner.setOnTouchListener(new AdapterView.OnTouchListener(){

                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                table_name = "states";
                                column_to_fetch = "state";
                                column_to_serach = "country";
                                value_to_search = new String[]{country};

                                db_table_result_rows_list = travlogDB.getAllQueriedRows(column_to_fetch, table_name, column_to_serach, value_to_search);

                                db_table_result_rows_list.add("Add State +");

                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AddNewLocation.this.getApplicationContext(),   android.R.layout.simple_spinner_item, db_table_result_rows_list);
                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                statesSpinner.setAdapter(spinnerArrayAdapter);

                                statesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        state = statesSpinner.getSelectedItem().toString();
                                        System.out.println("selected state = "+state);
                                        if(state == "Add State +")
                                        {
                                            //showing pop up alert dialog box to add new item
                                            AlertDialog.Builder builder = new AlertDialog.Builder(AddNewLocation.this);
                                            builder.setTitle("Add New State");
                                            builder.setMessage("Enter State Name");

                                            final EditText input_add_new_state = new EditText(AddNewLocation.this);
                                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.MATCH_PARENT);
                                            input_add_new_state.setLayoutParams(lp);
                                            builder.setView(input_add_new_state);
//                                    builder.setIcon(R.drawable.key);

                                            builder.setCancelable(true)
                                                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            // this block will execute when we click on "Yes"
                                                            state =  input_add_new_state.getText().toString();
                                                            db_table_result_rows_list.remove(0);
                                                            db_table_result_rows_list.add(state);

                                                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AddNewLocation.this.getApplicationContext(),   android.R.layout.simple_spinner_item, db_table_result_rows_list);
                                                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                                            statesSpinner.setAdapter(spinnerArrayAdapter);

                                                            statesSpinner.setSelection(((ArrayAdapter<String>)statesSpinner.getAdapter()).getPosition(state));



                                                            //district spinner flow starts here
                                                            if(state != "")
                                                            {
                                                                districtsSpinner.setOnTouchListener(new AdapterView.OnTouchListener() {
                                                                    @Override
                                                                    public boolean onTouch(View view, MotionEvent motionEvent) {
                                                                        table_name = "districts";
                                                                        column_to_fetch = "district";
                                                                        column_to_serach = "state";
                                                                        value_to_search = new String[]{state};

                                                                        db_table_result_rows_list = travlogDB.getAllQueriedRows(column_to_fetch, table_name, column_to_serach, value_to_search);

                                                                        db_table_result_rows_list.add("Add District +");

                                                                        ArrayAdapter<String> spinnerArrayAdapterDistrict = new ArrayAdapter<String>(AddNewLocation.this.getApplicationContext(),   android.R.layout.simple_spinner_item, db_table_result_rows_list);
                                                                        spinnerArrayAdapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                                                        districtsSpinner.setAdapter(spinnerArrayAdapterDistrict);

                                                                        districtsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                            @Override
                                                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                                                district = districtsSpinner.getSelectedItem().toString();
                                                                                System.out.println("selected district = "+district);
                                                                                if(district == "Add District +")
                                                                                {
                                                                                    //showing pop up alert dialog box to add new item
                                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(AddNewLocation.this);
                                                                                    builder.setTitle("Add New District");
                                                                                    builder.setMessage("Enter District Name");

                                                                                    final EditText input_add_new_district = new EditText(AddNewLocation.this);
                                                                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                                                                            LinearLayout.LayoutParams.MATCH_PARENT);
                                                                                    input_add_new_district.setLayoutParams(lp);
                                                                                    builder.setView(input_add_new_district);

                                                                                    builder.setCancelable(true)
                                                                                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                                                                                @Override
                                                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                                                    // this block will execute when we click on "Yes"
                                                                                                    district =  input_add_new_district.getText().toString();

                                                                                                    db_table_result_rows_list.remove(0);
                                                                                                    db_table_result_rows_list.add(district);

                                                                                                    ArrayAdapter<String> spinnerArrayAdapterDistrict = new ArrayAdapter<String>(AddNewLocation.this.getApplicationContext(),   android.R.layout.simple_spinner_item, db_table_result_rows_list);
                                                                                                    spinnerArrayAdapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                                                                                    districtsSpinner.setAdapter(spinnerArrayAdapterDistrict);

                                                                                                    districtsSpinner.setSelection(((ArrayAdapter<String>)districtsSpinner.getAdapter()).getPosition(district));

                                                                                                }
                                                                                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                                                        @Override
                                                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                                                            // this block will execute when we click on "Cancel"
                                                                                            district = "";
                                                                                            dialogInterface.cancel();
                                                                                        }
                                                                                    });

                                                                                    AlertDialog alertDialog = builder.create();
                                                                                    alertDialog.setTitle("Add New District");
                                                                                    alertDialog.show();
                                                                                }

                                                                            }

                                                                            @Override
                                                                            public void onNothingSelected(AdapterView<?> adapterView) {
                                                                                district = "";
                                                                            }
                                                                        });
                                                                        return false;
                                                                    }
                                                                });
                                                            }

                                                            //district spinnner flow ends here


                                                        }
                                                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    // this block will execute when we click on "Cancel"
                                                    state = "";
                                                    dialogInterface.cancel();
                                                }
                                            });

                                            AlertDialog alertDialog = builder.create();
                                            alertDialog.setTitle("Add New State");
                                            alertDialog.show();
                                        }

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        state = "";
                                    }

                                });
                                return false;
                            }
                        });
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

        //here
        if(location_name != "")
        {
            spinnerNearestCities.setOnTouchListener(new AdapterView.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    table_name = "nearest_cities";
                    column_to_fetch = "city_name";
                    column_to_serach = "location_name";
                    value_to_search = new String[]{location_name};

                    db_table_result_rows_list = travlogDB.getAllQueriedRows(column_to_fetch, table_name, column_to_serach, value_to_search);

                    db_table_result_rows_list.add("Add Nearest City +");

                    ArrayAdapter<String> spinnerArrayAdapterNearestCity = new ArrayAdapter<String>(AddNewLocation.this.getApplicationContext(),   android.R.layout.simple_spinner_item, db_table_result_rows_list);
                    spinnerArrayAdapterNearestCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinnerNearestCities.setAdapter(spinnerArrayAdapterNearestCity);

                    spinnerNearestCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            nearest_city = spinnerNearestCities.getSelectedItem().toString();
                            System.out.println("selected nearest city = "+nearest_city);
                            if(nearest_city == "Add Nearest City +")
                            {
                                //showing pop up alert dialog box to add new item
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddNewLocation.this);
                                builder.setTitle("Add New Nearest City");
                                builder.setMessage("Enter Nearest City Name");

                                final EditText input_add_new_nearest_city = new EditText(AddNewLocation.this);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                input_add_new_nearest_city.setLayoutParams(lp);
                                builder.setView(input_add_new_nearest_city);

                                builder.setCancelable(true)
                                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                // this block will execute when we click on "Yes"
                                                nearest_city =  input_add_new_nearest_city.getText().toString();

                                                db_table_result_rows_list.remove(0);
                                                db_table_result_rows_list.add(nearest_city);

                                                ArrayAdapter<String> spinnerArrayAdapterNearestCity = new ArrayAdapter<String>(AddNewLocation.this.getApplicationContext(),   android.R.layout.simple_spinner_item, db_table_result_rows_list);
                                                spinnerArrayAdapterNearestCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                                spinnerNearestCities.setAdapter(spinnerArrayAdapterNearestCity);

                                                spinnerNearestCities.setSelection(((ArrayAdapter<String>)spinnerNearestCities.getAdapter()).getPosition(nearest_city));
                                            }
                                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // this block will execute when we click on "Cancel"
                                        nearest_city = "";
                                        dialogInterface.cancel();
                                    }
                                });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.setTitle("Add New Nearest City");
                                alertDialog.show();
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            nearest_city = "";
                        }
                    });
                    return false;
                }
            });
        }

        String dist_blr = editTextDistanceFromBangalore.getText().toString();
        if(dist_blr == null || dist_blr.isEmpty())
        {

        }
        else
        {
            distance_from_bangalore = Integer.parseInt(dist_blr);
        }

        String dist_home = editTextDistanceFromHome.getText().toString();
        if(dist_home == null || dist_home.isEmpty())
        {

        }
        else
        {
            distance_from_home = Integer.parseInt(dist_home);
        }

        if(location_name != "")
        {
            spinnerNearestTouristsSpot.setOnTouchListener(new AdapterView.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    //            Nearest Tourist Spots
                    table_name = "nearest_tourist_spots";
                    column_to_fetch = "nearest_tourist_spot";
                    column_to_serach = "location_name";
                    value_to_search = new String[]{location_name};

                    db_table_result_rows_list = travlogDB.getAllQueriedRows(column_to_fetch, table_name, column_to_serach, value_to_search);

                    db_table_result_rows_list.add("Add Nearest Tourists Spot +");

                    ArrayAdapter<String> spinnerArrayAdapterNearestTouristsSpot = new ArrayAdapter<String>(AddNewLocation.this.getApplicationContext(),   android.R.layout.simple_spinner_item, db_table_result_rows_list);
                    spinnerArrayAdapterNearestTouristsSpot.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinnerNearestTouristsSpot.setAdapter(spinnerArrayAdapterNearestTouristsSpot);

                    spinnerNearestTouristsSpot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            nearest_tourist_spot = spinnerNearestTouristsSpot.getSelectedItem().toString();
                            System.out.println("selected nearest tourist spot = "+nearest_city);
                            if(nearest_tourist_spot == "Add Nearest Tourists Spot +")
                            {
                                //showing pop up alert dialog box to add new item
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddNewLocation.this);
                                builder.setTitle("Add Tourists Spot");
                                builder.setMessage("Enter Spot Name");

                                final EditText input_add_new_nearest_tourist_spot = new EditText(AddNewLocation.this);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                input_add_new_nearest_tourist_spot.setLayoutParams(lp);
                                builder.setView(input_add_new_nearest_tourist_spot);

                                builder.setCancelable(true)
                                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                // this block will execute when we click on "Yes"
                                                nearest_tourist_spot =  input_add_new_nearest_tourist_spot.getText().toString();

                                                db_table_result_rows_list.remove(0);
                                                db_table_result_rows_list.add(nearest_tourist_spot);

                                                ArrayAdapter<String> spinnerArrayAdapterNearestTouristsSpot = new ArrayAdapter<String>(AddNewLocation.this.getApplicationContext(),   android.R.layout.simple_spinner_item, db_table_result_rows_list);
                                                spinnerArrayAdapterNearestTouristsSpot.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                                spinnerNearestTouristsSpot.setAdapter(spinnerArrayAdapterNearestTouristsSpot);

                                                spinnerNearestTouristsSpot.setSelection(((ArrayAdapter<String>)spinnerNearestTouristsSpot.getAdapter()).getPosition(nearest_tourist_spot));
                                            }
                                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // this block will execute when we click on "Cancel"
                                        nearest_tourist_spot = "";
                                        dialogInterface.cancel();
                                    }
                                });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.setTitle("Add New Nearest City");
                                alertDialog.show();
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            nearest_tourist_spot = "";
                        }
                    });
                    return false;
                }
            });
        }
    }

    public void submiNewLocation(View view)
    {
        location_name = editTextLocationName.getText().toString();
        continent = continentSpinner.getSelectedItem().toString();
        country = countrySpinner.getSelectedItem().toString();
        state = statesSpinner.getSelectedItem().toString();
        district = districtsSpinner.getSelectedItem().toString();
        nearest_city = spinnerNearestCities.getSelectedItem().toString();

        String dist_blr = editTextDistanceFromBangalore.getText().toString();
        if(dist_blr == null || dist_blr.isEmpty())
        {
            distance_from_bangalore = 0;
        }
        else
        {
            distance_from_bangalore = Integer.parseInt(dist_blr);
        }

        String dist_home = editTextDistanceFromHome.getText().toString();
        if(dist_home == null || dist_home.isEmpty())
        {
            distance_from_home = 0;
        }
        else
        {
            distance_from_home = Integer.parseInt(dist_home);
        }


        google_map_location = editTextGoogleMapLocation.getText().toString();
        nearest_tourist_spot = spinnerNearestTouristsSpot.getSelectedItem().toString();

        System.out.println("Location Name = "+location_name);
        System.out.println("Continent = "+continent);
        System.out.println("Country = "+country);
        System.out.println("State = "+state);
        System.out.println("District = "+district);
        System.out.println("Nearest City = "+nearest_city);
        System.out.println("Distance from Bangalore = "+distance_from_bangalore);
        System.out.println("Distance from Home = "+distance_from_home);
        System.out.println("Google Map Location = "+google_map_location);
        System.out.println("Nearest Tourist Spot = "+nearest_tourist_spot);
    }
}
