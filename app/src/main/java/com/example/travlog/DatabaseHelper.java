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


    public DatabaseHelper(Context context) {
        //this function is a constructor and it creates a database with name "travlog"
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //query to create a new table. we can write queries to create tables here as new lines
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("create table "+ TABLE_CONTINENTS +" (continents_id INTEGER PRIMARY KEY AUTOINCREMENT,  continent TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //WRITE CODES IN IT AND CALL THIS FUNCTION IF WE NEED TO MAKE CHANGES IN EXISTING TABE STRUCTURE
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTINENTS);
        onCreate(sqLiteDatabase);

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
            System.out.println("NO DATA IN TABLE");
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

    public void deleteRow(int id)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String table_name = "continents";
        int numberOfRowsdeleted = sqLiteDatabase.delete(table_name, "continents_id="+id, null);
        if(numberOfRowsdeleted > 0)
        {
            System.out.println("numberOfRowsdeleted="+numberOfRowsdeleted);
        }
        else
        {
            System.out.println("ROW DELETION FAILED");
        }
    }

    public void createTable()
    {
        SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = this.getWritableDatabase();
//        sqLiteDatabase.execSQL("create table "+ TABLE_CONTINENTS +" (continents_id INTEGER PRIMARY KEY AUTOINCREMENT,  continent TEXT)");
//        sqLiteDatabase.execSQL("create table "+ TABLE_COUNTRIES +" (countries_id INTEGER PRIMARY KEY AUTOINCREMENT,  country TEXT, country_code TEXT, continent TEXT, continents_id INTEGER, tele_extension TEXT)");
//        sqLiteDatabase.execSQL("create table "+ TABLE_STATES +" (states_id INTEGER PRIMARY KEY AUTOINCREMENT,  state TEXT, state_code TEXT, country TEXT, countries_id INTEGER, continent TEXT, continents_id INTEGER)");
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
        db.close();
        return db_result;
    }

}
