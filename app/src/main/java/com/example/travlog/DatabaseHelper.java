package com.example.travlog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.List;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "travlog";
    public static final String TABLE_CONTINENTS = "continents";
    public static final String COL_CONTINENTS_CONTINENTS_ID = "continents_id";
    public static final String COL_CONTINENTS_CONTINENT = "continent";

    public static final String TABLE_COUNTRIES = "countries";
    public static final String COL_COUNTRIES_COUNTRIES_ID = "countries_id";
    public static final String COL_COUNTRIES_COUNTRY = "country";
    public static final String COL_COUNTRIES_COUNTRY_CODE = "country_code";
    public static final String COL_COUNTRIES_TELE_EXTENSION = "tele_extension";

    public static final String TABLE_STATES = "states";
    public static final String COL_STATES_STATES_ID = "states_id";
    public static final String COL_STATES_STATE = "state";
    public static final String COL_STATES_STATE_CODE = "state_code";

    public static final String TABLE_DISTRICTS = "districts";
    public static final String COL_DISTRICTS_DISTRICTS_ID = "districts_id";
    public static final String COL_DISTRICTS_DISTRICT = "district";
    public static final String COL_DISTRICTS_DISTRICT_CODE = "district_code";

    public static final String TABLE_LOCATIONS = "locations";
    public static final String COL_LOCATIONS_LOCATIONS_ID = "locations_id";
    public static final String COL_LOCATIONS_LOCATION_NAME = "location_name";
    public static final String COL_LOCATIONS_DISTANCE_FROM_BANGALORE = "distance_from_bangalore";
    public static final String COL_LOCATIONS_DISTANCE_FROM_HOME = "distance_from_home";
    public static final String COL_LOCATIONS_GOOGLE_MAP_LOCATION_URL = "google_map_location_url";

    public static final String TABLE_NEAREST_CITIES = "nearest_cities";
    public static final String COL_NEAREST_CITIES_NEAREST_CITIES_ID = "nearest_cities_id";
    public static final String COL_NEAREST_CITIES_CITY_NAME = "city_name";
    public static final String COL_NEAREST_CITIES_DISTANCE_TO_LOCATION = "distance_to_location";

    public static final String TABLE_NEAREST_TOURIST_SPOTS = "nearest_tourist_spots";
    public static final String COL_NEAREST_TOURIST_SPOTS_NEAREST_TOURIST_SPOTS_ID = "nearest_tourist_spots_id";
    public static final String COL_NEAREST_TOURIST_SPOTS_NEAREST_TOURIST_SPOT = "nearest_tourist_spot";

    public static final String TABLE_LOCATION_DETAILS = "location_details";
    public static final String COL_LOCATION_DETAILS_LOCATION_DETAILS_ID = "location_details_id";
    public static final String COL_LOCATION_DETAILS_LOCATION_DETAILS = "location_details";





    public DatabaseHelper(Context context) {
        //this function is a constructor and it creates a database with name "travlog"
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //query to create a new table. we can write queries to create tables here as new lines
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("create table "+ TABLE_CONTINENTS +" (continents_id INTEGER PRIMARY KEY AUTOINCREMENT,  continent TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //WRITE CODES IN IT AND CALL THIS FUNCTION IF WE NEED TO MAKE CHANGES IN EXISTING TABE STRUCTURE
//        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTINENTS);
//        onCreate(sqLiteDatabase);

    }

    public  boolean CheckIsDataAlreadyInDBorNot(String TableName, String dbfield, String fieldValue)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
        Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean insertDataContinents(String continent)
    {
        //checking data already there in table or not
        String table_name = "continents";
        String column_to_serach = "continent";
        String[] coloumns_to_fetch = {"continent"};
        String[] values_to_search = {continent};
        Cursor dataFromTable = this.getDataFromTable(table_name, coloumns_to_fetch, column_to_serach, values_to_search);

        if(dataFromTable.getCount() == 0)
        {
//            System.out.println("NO DATA IN TABLE");
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_CONTINENTS_CONTINENT, continent);
            long result = sqLiteDatabase.insert(TABLE_CONTINENTS, null, contentValues);

            if(result == -1)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
//            if( dataFromTable != null && dataFromTable.moveToFirst() ){
//                continent = dataFromTable.getString(dataFromTable.getColumnIndex("continent"));
//                System.out.println("continent = "+continent);
//                dataFromTable.close();
//            }

            return false;
        }
    }

    public boolean insertDataCountries(String country)
    {
        //checking data already there in table or not
        String continent = "South America";
        Integer continents_id = 6;
        String table_name = "countries";
        String coloumn_name = "country";
        String country_code = null;
        String tele_extension = null;
        String value = country;

        String column_to_serach = "continent";
        String[] coloumns_to_fetch = {"continent"};
        String[] values_to_search = {continent};

//        Cursor dataFromTable = this.getDataFromTable(table_name, coloumn_name, value);
        Cursor dataFromTable = this.getDataFromTable(table_name, coloumns_to_fetch, column_to_serach, values_to_search);


        if(dataFromTable.getCount() == 0)
        {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_COUNTRIES_COUNTRY, country);
            contentValues.put(COL_COUNTRIES_COUNTRY_CODE, country_code);
            contentValues.put(COL_COUNTRIES_TELE_EXTENSION, tele_extension);
            contentValues.put(COL_CONTINENTS_CONTINENT, continent);
            contentValues.put(COL_CONTINENTS_CONTINENTS_ID, continents_id);
            long result = sqLiteDatabase.insert(TABLE_COUNTRIES, null, contentValues);

            if(result == -1)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
//            if( dataFromTable != null && dataFromTable.moveToFirst() ){
//                country = dataFromTable.getString(dataFromTable.getColumnIndex("country"));
//                dataFromTable.close();
//            }

            return false;
        }
    }

    public boolean insertDataStates(String state, String country, Integer countries_id, String continent, Integer continents_id)
    {
        //checking data already there in table or not
        String table_name = "states";

        String column_to_serach = "state";
        String[] coloumns_to_fetch = {"state"};
        String[] values_to_search = {state};

        Cursor dataFromTable = this.getDataFromTable(table_name, coloumns_to_fetch, column_to_serach, values_to_search);


        if(dataFromTable.getCount() == 0)
        {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(COL_STATES_STATE, state);
            contentValues.put(COL_STATES_STATE_CODE, "");
            contentValues.put(COL_COUNTRIES_COUNTRY, country);
            contentValues.put(COL_COUNTRIES_COUNTRIES_ID, countries_id);
            contentValues.put(COL_CONTINENTS_CONTINENT, continent);
            contentValues.put(COL_CONTINENTS_CONTINENTS_ID, continents_id);
            long result = sqLiteDatabase.insert(TABLE_STATES, null, contentValues);

            if(result == -1)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
//            if( dataFromTable != null && dataFromTable.moveToFirst() ){
//                country = dataFromTable.getString(dataFromTable.getColumnIndex("country"));
//                dataFromTable.close();
//            }

            return false;
        }
    }

    public boolean insertDataDistricts(String district, String state, Integer states_id, String country, Integer countries_id, String continent, Integer continents_id)
    {
        //checking data already there in table or not
        String table_name = "districts";

        String column_to_serach = "district";
        String[] coloumns_to_fetch = {"district"};
        String[] values_to_search = {district};

        Cursor dataFromTable = this.getDataFromTable(table_name, coloumns_to_fetch, column_to_serach, values_to_search);


        if(dataFromTable.getCount() == 0)
        {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(COL_DISTRICTS_DISTRICT, district);
            contentValues.put(COL_DISTRICTS_DISTRICT_CODE, "");
            contentValues.put(COL_STATES_STATE, state);
            contentValues.put(COL_STATES_STATES_ID, states_id);
            contentValues.put(COL_COUNTRIES_COUNTRY, country);
            contentValues.put(COL_COUNTRIES_COUNTRIES_ID, countries_id);
            contentValues.put(COL_CONTINENTS_CONTINENT, continent);
            contentValues.put(COL_CONTINENTS_CONTINENTS_ID, continents_id);
            long result = sqLiteDatabase.insert(TABLE_DISTRICTS, null, contentValues);

            if(result == -1)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
//            if( dataFromTable != null && dataFromTable.moveToFirst() ){
//                country = dataFromTable.getString(dataFromTable.getColumnIndex("country"));
//                dataFromTable.close();
//            }

            return false;
        }
    }

    public boolean insertDataLocations(String location_name, String district, Integer districts_id, String state, Integer states_id, String country, Integer countries_id, String continent, Integer continents_id, Integer distance_from_bangalore, Integer distance_from_home, String google_map_location_url)
    {
        //checking data already there in table or not
        String table_name = "locations";

        String column_to_serach = "location_name";
        String[] coloumns_to_fetch = {"location_name"};
        String[] values_to_search = {location_name};

        Cursor dataFromTable = this.getDataFromTable(table_name, coloumns_to_fetch, column_to_serach, values_to_search);


        if(dataFromTable.getCount() == 0)
        {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(COL_LOCATIONS_LOCATION_NAME, location_name);
            contentValues.put(COL_DISTRICTS_DISTRICT, district);
            contentValues.put(COL_DISTRICTS_DISTRICTS_ID, districts_id);
            contentValues.put(COL_STATES_STATE, state);
            contentValues.put(COL_STATES_STATES_ID, states_id);
            contentValues.put(COL_COUNTRIES_COUNTRY, country);
            contentValues.put(COL_COUNTRIES_COUNTRIES_ID, countries_id);
            contentValues.put(COL_CONTINENTS_CONTINENT, continent);
            contentValues.put(COL_CONTINENTS_CONTINENTS_ID, continents_id);
            contentValues.put(COL_LOCATIONS_DISTANCE_FROM_BANGALORE, distance_from_bangalore);
            contentValues.put(COL_LOCATIONS_DISTANCE_FROM_HOME, distance_from_home);
            contentValues.put(COL_LOCATIONS_GOOGLE_MAP_LOCATION_URL, google_map_location_url);
            long result = sqLiteDatabase.insert(TABLE_LOCATIONS, null, contentValues);

            if(result == -1)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
//            if( dataFromTable != null && dataFromTable.moveToFirst() ){
//                country = dataFromTable.getString(dataFromTable.getColumnIndex("country"));
//                dataFromTable.close();
//            }

            return false;
        }
    }

    public boolean insertDataNearestCities(String city_name, String location_name)
    {
        //checking data already there in table or not
        String table_name = TABLE_NEAREST_CITIES;

        String column_to_serach = "location_name";
        String[] coloumns_to_fetch = {"location_name"};
        String[] values_to_search = {location_name};

        Cursor dataFromTable = this.getDataFromTable(table_name, coloumns_to_fetch, column_to_serach, values_to_search);


        if(dataFromTable.getCount() == 0)
        {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(COL_NEAREST_CITIES_CITY_NAME, city_name);
            contentValues.put(COL_LOCATIONS_LOCATION_NAME, location_name);

            long result = sqLiteDatabase.insert(TABLE_NEAREST_CITIES, null, contentValues);

            if(result == -1)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
//            if( dataFromTable != null && dataFromTable.moveToFirst() ){
//                country = dataFromTable.getString(dataFromTable.getColumnIndex("country"));
//                dataFromTable.close();
//            }

            return false;
        }
    }

    public boolean insertDataNearestTouristSpots(String nearest_tourist_spot, String location_name)
    {
        //checking data already there in table or not
        String table_name = "nearest_tourist_spots";

        String column_to_serach = "nearest_tourist_spot";
        String[] coloumns_to_fetch = {"nearest_tourist_spot"};
        String[] values_to_search = {nearest_tourist_spot};

        Cursor dataFromTable = this.getDataFromTable(table_name, coloumns_to_fetch, column_to_serach, values_to_search);


        if(dataFromTable.getCount() == 0)
        {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(COL_NEAREST_TOURIST_SPOTS_NEAREST_TOURIST_SPOT, nearest_tourist_spot);
            contentValues.put(COL_LOCATIONS_LOCATION_NAME, location_name);

            long result = sqLiteDatabase.insert(TABLE_NEAREST_TOURIST_SPOTS, null, contentValues);
            System.out.println("result of spot addition = "+result);

            if(result == -1)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
//            if( dataFromTable != null && dataFromTable.moveToFirst() ){
//                country = dataFromTable.getString(dataFromTable.getColumnIndex("country"));
//                dataFromTable.close();
//            }

            return false;
        }
    }

    public void deleteRow(int id)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String table_name = "continents";
        int numberOfRowsdeleted = sqLiteDatabase.delete(table_name, "continents_id="+id, null);
        if(numberOfRowsdeleted > 0)
        {
//            System.out.println("numberOfRowsdeleted="+numberOfRowsdeleted);
        }
        else
        {
//            System.out.println("ROW DELETION FAILED");
        }
    }


    public boolean isTableExists(String table_name)
    {
        //function to check if a table exists or not
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        if(true)
        {
            if(sqLiteDatabase == null || !sqLiteDatabase.isOpen())
            {
                sqLiteDatabase = getReadableDatabase();
            }

            if(!sqLiteDatabase.isReadOnly())
            {
//                sqLiteDatabase.close();
                sqLiteDatabase = getReadableDatabase();
            }
        }

        Cursor cursor = sqLiteDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+table_name+"'", null);
        if(cursor!=null)
        {
            if(cursor.getCount()>0)
            {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public void createTable()
    {
        SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = this.getWritableDatabase();

        boolean exists_continents = this.isTableExists(TABLE_CONTINENTS);
        if(!exists_continents)
        {
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_CONTINENTS +" (continents_id INTEGER PRIMARY KEY AUTOINCREMENT,  continent TEXT)");
        }

        boolean exists_countries = this.isTableExists(TABLE_COUNTRIES);
        if(!exists_countries)
        {
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_COUNTRIES +" (countries_id INTEGER PRIMARY KEY AUTOINCREMENT,  country TEXT, country_code TEXT, continent TEXT, continents_id INTEGER, tele_extension TEXT)");
        }

        boolean exists_states = this.isTableExists(TABLE_STATES);
        if(!exists_states)
        {
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_STATES +" (states_id INTEGER PRIMARY KEY AUTOINCREMENT,  state TEXT, state_code TEXT, country TEXT, countries_id INTEGER, continent TEXT, continents_id INTEGER)");
        }

        boolean exists_districts = this.isTableExists(TABLE_DISTRICTS);
        if(!exists_districts)
        {
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_DISTRICTS +" (districts_id INTEGER PRIMARY KEY AUTOINCREMENT,  district TEXT, district_code TEXT, state TEXT, states_id INTEGER, country TEXT, countries_id INTEGER, continent TEXT, continents_id INTEGER)");
        }


        boolean exists_locations = this.isTableExists(TABLE_LOCATIONS);
        if(!exists_locations)
        {
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_LOCATIONS +" (locations_id INTEGER PRIMARY KEY AUTOINCREMENT,  location_name TEXT, district TEXT, districts_id INTEGER, state TEXT, states_id INTEGER, country TEXT, countries_id INTEGER, continent TEXT, continents_id INTEGER, distance_from_bangalore INTEGER, distance_from_home INTEGER, google_map_location_url TEXT)");
        }



        boolean exists_nearest_cities = this.isTableExists(TABLE_NEAREST_CITIES);
        if(!exists_nearest_cities)
        {
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NEAREST_CITIES +" (nearest_cities_id INTEGER PRIMARY KEY AUTOINCREMENT, city_name TEXT, location_name TEXT)");

        }


        boolean exists_nearest_tourist_spots = this.isTableExists(TABLE_NEAREST_TOURIST_SPOTS);
        if(!exists_nearest_tourist_spots)
        {
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NEAREST_TOURIST_SPOTS +" (nearest_tourist_spots_id INTEGER PRIMARY KEY AUTOINCREMENT, nearest_tourist_spot TEXT, location_name TEXT)");
        }

        boolean exists_location_details = this.isTableExists(TABLE_LOCATION_DETAILS);
        if(!exists_nearest_tourist_spots)
        {
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_LOCATION_DETAILS +" (location_details_id INTEGER PRIMARY KEY AUTOINCREMENT, location_name TEXT, locations_id INTEGER)");
        }

    }

    public void deleteTable(String table_name)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("drop table if exists "+ table_name);
    }
//    checkIfRowAlreadyThere

    public Cursor getDataFromTable(String table_name, String[] columns_to_fetch, String column_to_serach, String[] values_to_search)
    {
        //used to check if particular entry already there in queried table or not
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String[] coloumnsToFetch = columns_to_fetch;
        String whereCondition = column_to_serach + " = ?";
        String[] selectionArguments = values_to_search;
        Cursor cursor = sqLiteDatabase.query(table_name, coloumnsToFetch, whereCondition, selectionArguments, null, null, null);
        return cursor;
    }

    public List<String> getAllQueriedRows(String column_to_fetch, String table_name, String column_to_serach, String[] value_to_search)
    {
        //this function used to show all queried rows to list it as spinner items

        List<String> db_result=new ArrayList<>();
        //get readable database
        SQLiteDatabase db=this.getReadableDatabase();

        String query = "SELECT " + column_to_fetch + " FROM " + table_name + " WHERE " + column_to_serach + "=?";

        Cursor cursor=db.rawQuery(query, value_to_search);
        if(cursor.moveToFirst())
        {
            do {
                db_result.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        //close the cursor
        cursor.close();
        //close the database
//        db.close();
        return db_result;
    }

}
