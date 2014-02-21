package com.vindsiden.windwidget.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.vindsiden.windwidget.model.Place;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 03.02.14.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    //Path to the device folder with databases
    public static String DB_PATH = "/data/data/com.vindsiden.windwidget/databases/";

    //Database file name
    public static String DB_NAME = "steder.sqlite";
    public static String COLUMN_NAME = "Stadnamn";
    public static String TABLE_NAME = "Steder";

    public SQLiteDatabase database;
    private static Context context;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;

    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */

    public void createDatabase() throws IOException {

        boolean dbExist = checkDataBase();

       /* if (dbExist) {
            //do nothing - database already exist
            Log.d("Database", "Database exists");
        } else { */

        //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();
                Log.d("Database", "Copying");

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        //  }


    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (Exception e) {

            Log.i("Database", "database does't exist yet.");

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        String s = context.getAssets().open(DB_NAME).toString();

        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);


        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (database != null)
            database.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * CreateSuggestionList takes in a search-String and returns a list of places.
     *
     * @param searchTerm
     * @return
     */
    public List<Place> CreateSuggestionList(String searchTerm) {
        List<Place> recordList = new ArrayList<Place>();
        //Select query
        String sql = "";
        sql += "SELECT * FROM " + TABLE_NAME;
        sql += " WHERE " + COLUMN_NAME + " LIKE '%" + searchTerm + "%'";
        //sql += " ORDER BY " + COLUMN_ID + " DESC";
        //Sort based on Name and Accending F.ex Alpha Alhan2 and so on..
        //  sql += " ORDER BY "+ COLUMN_NAME+" ASC";
        sql += " LIMIT 10";


        //Create instance of database
        SQLiteDatabase database = this.getWritableDatabase();


        //execute the query
        Cursor cursor = database.rawQuery(sql, null);

        //Looping rows and adding to the list

        if (cursor.moveToFirst()) {

            do {
                //Create a string with the objectName
                String objectName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                //Create a new "Place" with the object name
                Place place = new Place(objectName);

                recordList.add(place);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return recordList;

    }


    /**
     * The findPlaceAutoComplete takes in a placename (String) and returns a Place filled with Stedsnavn,Stedstype and Municipality.
     * @param placeName
     * @return Place object filled with Stedsnavn, Stedstype and Municipality.
     */

    public Place findPlaceAutoComplete(String placeName) {

        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " =  \"" + placeName + "\"";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        Place place = new Place();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            //Tallet på slutten under indikerer hvilket felt den henter fra. Dermed er det lett å hente f.eks URL eller lign

//            if (cursor != null) place.setId(Integer.parseInt(cursor.getString(0)));  //Kollonne 0 = Kommunenummer
            place.setPlaceName(cursor.getString(1));     //Kollonne 1 = Stedsnavn
            //place.setPriority(cursor.getString(2));     //Kollonne 2 = Priority
            place.setPlaceType(cursor.getString(3));  //Kollonne 3 = Stedstype
            place.setMunicipality(cursor.getString(4));   //Kolonne 4 = Municipality Navn
            //place.setCounty(cursor.getString(5));    //Kollonne 5 = County
            //place.setLat(cursor.getFloat(6));    //Kollonne 6 = Lat
            //place.setLon(cursor.getFloat(7));    //Kollonne 7 = Long
            //place.setHeight(cursor.getString(8));    //Kollonne 8 = Høyde
            //place.setNB_url(cursor.getString(9));    //Kollonne 9 = URL Bokmål
            //place.setEN_url(cursor.getString(10));    //Kollonne 10 = URL Engelsk
            //place.setVindsiden_URL(cursor.getString(11));    //Kollonne 11 = Vindsiden URL
            //place.setSource(cursor.getString(12));    //Kolonne 12 = Source
            //place.setDescription(cursor.getString(13));    //Kolonne 13 = Description
            //place.setRoadDescription(cursor.getString(14));    //Kolonne 14 = RoadDescription
            //place.setWaterConditions(cursor.getString(15));    //Kolonne 15 = WaterConditions
            //place.setFacilities(cursor.getString(16));    //Kolonne 16 = Facilities
            //place.setSuited_For(cursor.getString(17));    //Kolonne 17 = Egnet for
            //place.setWindDirection(cursor.getString(18));    //Kolonne 18 = WindDirection


            cursor.close();

        } else {
            place = null;

        }
        database.close();
        return place;


    }

    public void updateTimesUsed(String placeName) {


    }

    public Place findPlace(String placeName) {

        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " =  \"" + placeName + "\"";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        Place place = new Place();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            //Tallet på slutten under indikerer hvilket felt den henter fra. Dermed er det lett å hente f.eks URL eller lign

//            if (cursor != null) place.setId(Integer.parseInt(cursor.getString(0)));  //Kollonne 0 = Kommunenummer
            place.setPlaceName(cursor.getString(1));     //Kollonne 1 = Stedsnavn
            place.setPriority(cursor.getString(2));     //Kollonne 2 = Priority
            place.setPlaceType(cursor.getString(3));  //Kollonne 3 = Stedstype
            place.setMunicipality(cursor.getString(4));   //Kolonne 4 = Municipality Navn
            place.setCounty(cursor.getString(5));    //Kollonne 5 = County
            place.setLat(cursor.getFloat(6));    //Kollonne 6 = Lat
            place.setLon(cursor.getFloat(7));    //Kollonne 7 = Long
            place.setHeight(cursor.getString(8));    //Kollonne 8 = Høyde
            place.setNB_url(cursor.getString(9));    //Kollonne 9 = URL Bokmål
            place.setEN_url(cursor.getString(10));    //Kollonne 10 = URL Engelsk
            place.setVindsiden_URL(cursor.getString(11));    //Kollonne 11 = Vindsiden URL
            place.setSource(cursor.getString(12));    //Kolonne 12 = Source
            place.setDescription(cursor.getString(13));    //Kolonne 13 = Description
            place.setRoadDescription(cursor.getString(14));    //Kolonne 14 = RoadDescription
            place.setWaterConditions(cursor.getString(15));    //Kolonne 15 = WaterConditions
            place.setFacilities(cursor.getString(16));    //Kolonne 16 = Facilities
            place.setSuited_For(cursor.getString(17));    //Kolonne 17 = Egnet for
            place.setWindDirection(cursor.getString(18));    //Kolonne 18 = WindDirection
            // place.setTimesUsed(cursor.getInt(19)); //Kolonne 19 = Antall ganger brukt


            cursor.close();

        } else {
            place = null;

        }
        database.close();
        return place;


    }


}
