package com.example.travlog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.AdapterView;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class AddNewLocation extends AppCompatActivity{

    DatabaseHelper travlogDB;

    Spinner locationsSpinner, continentSpinner, countrySpinner, statesSpinner, districtsSpinner, spinnerNearestCities, spinnerNearestTouristsSpot, spinnerAddLocationDetails;

    Button buttonSubmit;
    EditText editTextDistanceFromBangalore, editTextDistanceFromHome, editTextGoogleMapLocation;


    String state, table_name, column_to_fetch, column_to_serach, district, country, continent, location_name, nearest_city, google_map_location, nearest_tourist_spot;
    Integer distance_from_bangalore, distance_from_home, continents_id, countries_id, states_id, districts_id, editTextId;
    String[] value_to_search, columns_to_fetch, values_to_search, continentArray, countriesArray;
    Boolean result, result_districts, result_locations, result_nearest_cities, result_nearest_tourist_spots;
    Cursor dataFromTable;

    List<String> db_table_result_rows_list = new ArrayList<>();

    ScrollView addItemsEditTextScrollView;

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
        editTextId = 0;

        travlogDB = new DatabaseHelper(this);


        //continents spinner
        locationsSpinner = (Spinner) findViewById(R.id.locationsSpinner);
        continentSpinner = (Spinner) findViewById(R.id.continentsSpinner);
        countrySpinner = (Spinner) findViewById(R.id.countriesSpinner);
        statesSpinner = (Spinner) findViewById(R.id.spinnerStates);
        districtsSpinner = (Spinner) findViewById(R.id.spinnerDistrict);
        spinnerNearestCities = (Spinner) findViewById(R.id.spinnerNearestCities);
        spinnerNearestTouristsSpot = (Spinner) findViewById(R.id.nearestTouristSpotsSpinner);
        spinnerAddLocationDetails = (Spinner) findViewById(R.id.locationDetailsSpinner);

        buttonSubmit = (Button) findViewById(R.id.btn_submit_new_location);

//        editTextLocationName = (EditText) findViewById(R.id.editTextNameOfLocation);
        editTextDistanceFromBangalore = (EditText) findViewById(R.id.editTextDistanceFromBangalore);
        editTextDistanceFromHome = (EditText) findViewById(R.id.editTextDistanceFromHome);
        editTextGoogleMapLocation = (EditText) findViewById(R.id.editTextGoogleMapLocation);


        addItemsEditTextScrollView = (ScrollView)findViewById(R.id.addItemsEditTextScrollView);


        //        travlogDB.deleteTable("locations");
        travlogDB.createTable();



        //inserting data in to continents table
        continentArray = new String[]{"Africa", "Asia", "Europe", "North America", "Australia", "South America"};
        for(int i = 0; i<6; i++)
        {
            travlogDB.insertDataContinents(continentArray[i]);
        }

        countriesArray = new String[]{"countries_africa_array", "countries_asia_array", "countries_europe_array", "countries_southamerica_array", "countries_australia_array"};

        for(int i = 0; i < 5; i++)
        {
            int countriesArrayID= getResources().getIdentifier(countriesArray[i] , "array",AddNewLocation.this.getPackageName());
            String[] countries = getResources().getStringArray(countriesArrayID);


            for(i=0; i<15; i++)
            {
                travlogDB.insertDataCountries(countries[i]);
            }
        }

//        location name spinner flow starts here

        locationsSpinner.setOnTouchListener(new AdapterView.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                System.out.println("On Touch action detected on location Spinner");

                table_name = "locations";
                column_to_fetch = "location_name";
                column_to_serach = "location_name";
                value_to_search = new String[]{"*"};

                db_table_result_rows_list = travlogDB.getAllQueriedRows(column_to_fetch, table_name, column_to_serach, value_to_search);
                System.out.println("DB SEARCH RESULT="+db_table_result_rows_list);
                db_table_result_rows_list.add("Add New Location +");
                System.out.println("ADDED : Add New Location as new item in spinner");

                ArrayAdapter<String> spinnerArrayAdapterLocationName = new ArrayAdapter<String>(AddNewLocation.this.getApplicationContext(),   android.R.layout.simple_spinner_item, db_table_result_rows_list);
                spinnerArrayAdapterLocationName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                locationsSpinner.setAdapter(spinnerArrayAdapterLocationName);

                locationsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        System.out.println("ONE ITEM HAS BEEN SELECTED");
                        location_name = locationsSpinner.getSelectedItem().toString();

                        System.out.println("SELECTED ITEM="+location_name);

                        if(location_name == "Add New Location +")
                        {
                            System.out.println("SELECTED ITEM IS : Add New Location +");
                            //showing pop up alert dialog box to add new item
                            AlertDialog.Builder builder = new AlertDialog.Builder(AddNewLocation.this);
                            builder.setTitle("Add New Location");
                            builder.setMessage("Add New Location");

                            final EditText input_add_new_location_name = new EditText(AddNewLocation.this);
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT);
                            input_add_new_location_name.setLayoutParams(lp);
                            builder.setView(input_add_new_location_name);

                            builder.setCancelable(true).setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            // this block will execute when we click on "Yes"
                                            location_name =  input_add_new_location_name.getText().toString();

                                            db_table_result_rows_list.remove(0);
                                            db_table_result_rows_list.add(location_name);

                                            ArrayAdapter<String> spinnerArrayAdapterLocationName = new ArrayAdapter<String>(AddNewLocation.this.getApplicationContext(),   android.R.layout.simple_spinner_item, db_table_result_rows_list);
                                            spinnerArrayAdapterLocationName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                            locationsSpinner.setAdapter(spinnerArrayAdapterLocationName);

                                            locationsSpinner.setSelection(((ArrayAdapter<String>)locationsSpinner.getAdapter()).getPosition(location_name));

                                            System.out.println("SUCCESSFULLY ADDED NEW LOCATION NAME");
                                        }
                                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // this block will execute when we click on "Cancel"
                                    System.out.println("USER CLICKED ON CANCEL BUTTON");
                                    location_name = "";
                                    dialogInterface.cancel();
                                }
                            });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.setTitle("Add New Location Name");
                            alertDialog.show();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
//                        System.out.println("NOTHING SELECTED");
                    }
                });

                return false;
            }
        });

//        location name spinner flow ends here

//        location_name = editTextLocationName.getText().toString();
        if(locationsSpinner.getSelectedItem() != null)
        {
            location_name = locationsSpinner.getSelectedItem().toString();
//            System.out.println("LOCATION NAME = "+location_name);
        }

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
//                        System.out.println("selected country = "+country);

                        statesSpinner.setOnTouchListener(new AdapterView.OnTouchListener(){

                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                //hereeeee
//                                location_name = editTextLocationName.getText().toString();
                                location_name = locationsSpinner.getSelectedItem().toString();

                                if(location_name.isEmpty())
                                {

                                }
                                else
                                {
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
//                                        System.out.println("selected state = "+state);
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
//                                                            System.out.println("hehe="+state);
                                                                db_table_result_rows_list.remove(0);
                                                                db_table_result_rows_list.add(state);

                                                                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AddNewLocation.this.getApplicationContext(),   android.R.layout.simple_spinner_item, db_table_result_rows_list);
                                                                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                                                                statesSpinner.setAdapter(spinnerArrayAdapter);

                                                                statesSpinner.setSelection(((ArrayAdapter<String>)statesSpinner.getAdapter()).getPosition(state));

                                                                //district spinner flow starts here
                                                                if(state.isEmpty())
                                                                {

                                                                }
                                                                else
                                                                {

//                                                                System.out.println("entered to district spinner flow");
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
//                                                                                System.out.println("selected district = "+district);
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

                                            else
                                            {
                                                //district spinner flow starts here
                                                if(state.isEmpty())
                                                {

                                                }
                                                else
                                                {

//                                                System.out.println("entered to district spinner flow");
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
//                                                                                System.out.println("selected district = "+district);
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
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {
                                            state = "";
                                        }

                                    });

//                                System.out.println("stateeee="+state);
                                }
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
        spinnerNearestCities.setOnTouchListener(new AdapterView.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

//                location_name = editTextLocationName.getText().toString();
                location_name = locationsSpinner.getSelectedItem().toString();


                if(location_name.isEmpty())
                {

                }
                else
                {
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
//                            System.out.println("selected nearest city = "+nearest_city);
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
                }

                return false;
            }
        });

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

        spinnerNearestTouristsSpot.setOnTouchListener(new AdapterView.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

//                location_name = editTextLocationName.getText().toString();
                location_name = locationsSpinner.getSelectedItem().toString();


                if(location_name.isEmpty())
                {

                }
                else
                {

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
//                            System.out.println("selected nearest tourist spot = "+nearest_city);
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
                                alertDialog.setTitle("Add New Nearest Tourist Spot");
                                alertDialog.show();
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            nearest_tourist_spot = "";
                        }
                    });
                }
                return false;
            }
        });


        //add location details flow starts here
        spinnerAddLocationDetails.setOnTouchListener(new AdapterView.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                LayoutInflater layoutInflater = LayoutInflater.from(AddNewLocation.this);

                final View addLocationDetailsView = layoutInflater.inflate(R.layout.add_multiple_item_alert_dialog, null);

                final  AlertDialog alertDialogAddLocationDetails = new AlertDialog.Builder(AddNewLocation.this).create();

                alertDialogAddLocationDetails.setView(addLocationDetailsView);

                alertDialogAddLocationDetails.show();



//                int childCount = idAddMultipleItemAlertDialogRelativeLayout.getChildCount();
//                int childCount = idAddMultipleItemAlertDialogScrollView.getChildCount();
//                if(childCount == 0)
//                {
//                    System.out.println("no children");
//                }
//                else
//                {
//                    System.out.println("NO:OF CHILDREN ELEMENTS="+String.valueOf(childCount));
//                }

                alertDialogAddLocationDetails.findViewById(R.id.imageViewPlus).setOnClickListener(new View.OnClickListener() {
                    int i = 0;
                    @Override
                    public void onClick(View view) {

                        EditText editTextDetails = new EditText(AddNewLocation.this);

                        editTextDetails.setLayoutParams(new ScrollView.LayoutParams(
                                ScrollView.LayoutParams.WRAP_CONTENT,
                                ScrollView.LayoutParams.WRAP_CONTENT
                        ));

                        addItemsEditTextScrollView.addView(editTextDetails);

//                        if(editTextId == 1)
//                        {
//                            editTextId = 1;
//                        }
//                        else
//                        {
//                            //getting id of last editText and incrementing 1 to it, to generate id for new editText
//                        }
                    }
                });

                return true;
            }
        });
        //add location details flow ends here
    }

    public void submiNewLocation(View view)
    {
//        location_name = editTextLocationName.getText().toString();
        location_name = locationsSpinner.getSelectedItem().toString();

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

//        System.out.println("Location Name = "+location_name);
//        System.out.println("Continent = "+continent);
//        System.out.println("Country = "+country);
//        System.out.println("State = "+state);
//        System.out.println("District = "+district);
//        System.out.println("Nearest City = "+nearest_city);
//        System.out.println("Distance from Bangalore = "+distance_from_bangalore);
//        System.out.println("Distance from Home = "+distance_from_home);
//        System.out.println("Google Map Location = "+google_map_location);
//        System.out.println("Nearest Tourist Spot = "+nearest_tourist_spot);

        if(continent == "Africa"){continents_id = 1;}
        else if(continent == "Asia"){continents_id = 2;}
        else if(continent == "Europe"){continents_id = 3;}
        else if(continent == "North America"){continents_id = 4;}
        else if(continent == "Australia"){continents_id = 5;}
        else if(continent == "South America"){continents_id = 6;}

        //taking countries id
        values_to_search = new String[]{country};
        columns_to_fetch = new String[]{"countries_id"};
        dataFromTable =  travlogDB.getDataFromTable("countries", columns_to_fetch, "country", values_to_search);

        if( dataFromTable != null && dataFromTable.moveToFirst() )
        {
            countries_id = Integer.parseInt(dataFromTable.getString(dataFromTable.getColumnIndex("countries_id")));
//            dataFromTable.close();
        }
        else
        {
            countries_id = 0;
        }

        // inserting data in to states table
        result = travlogDB.insertDataStates(state, country, countries_id, continent, continents_id);

        if(result)
        {
            //getting states_id from states table
            values_to_search = new String[]{state};
            columns_to_fetch = new String[]{"states_id"};
            dataFromTable =  travlogDB.getDataFromTable("states", columns_to_fetch, "state", values_to_search);

            if( dataFromTable != null && dataFromTable.moveToFirst() )
            {
                states_id = Integer.parseInt(dataFromTable.getString(dataFromTable.getColumnIndex("states_id")));
//                dataFromTable.close();
            }
            else
            {
                states_id = 0;
            }
        }

        //inserting data in to districts table
        result_districts = travlogDB.insertDataDistricts(district, state, states_id, country, countries_id, continent, continents_id);
        if(result_districts)
        {
            //getting districts_id from districts table
            values_to_search = new String[]{district};
            columns_to_fetch = new String[]{"districts_id"};
            dataFromTable =  travlogDB.getDataFromTable("districts", columns_to_fetch, "district", values_to_search);

            if( dataFromTable != null && dataFromTable.moveToFirst() )
            {
                String abc = dataFromTable.getString(dataFromTable.getColumnIndex("states_id"));
//                System.out.println("stes iddd");
//                System.out.println(abc);
//                dataFromTable.close();
            }
            else
            {
                districts_id = 0;
            }
        }

        //inserting data in to locations table
        result_locations = travlogDB.insertDataLocations(location_name, district, districts_id, state, states_id, country, countries_id, continent, continents_id, distance_from_bangalore, distance_from_home, google_map_location);

        //inserting data in to nearest cities table
        result_nearest_cities = travlogDB.insertDataNearestCities(nearest_city, location_name);

        //inserting data in to nearest tourist spots table
        result_nearest_tourist_spots = travlogDB.insertDataNearestTouristSpots(nearest_tourist_spot, location_name);


    }
}
