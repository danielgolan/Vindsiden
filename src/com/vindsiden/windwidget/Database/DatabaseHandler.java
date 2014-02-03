package com.vindsiden.windwidget.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.vindsiden.windwidget.model.Sted;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 23.01.14.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "steder.db";
    private static final String TABLE_PLACES = "steder";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "_stedsNavn";
    private static final String COLUMN_URL = "_url";
    //Type = kite, surf, by, bygd osv..
    private static final String COLUMN_TYPE = "_type";

    //Path to the device folder with databases
    public static String DB_PATH;

    //Database file name
    public static String DB_NAME;
    public SQLiteDatabase database;
    public final Context context = this.context;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // createDatabase();


/*
        String CREATE_STEDS_TABLE = "CREATE TABLE " + TABLE_PLACES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + " TEXT" + "," +
                COLUMN_URL + " TEXT" + "," +
                COLUMN_TYPE + " TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_STEDS_TABLE);
        Log.d("VindsidenDatabase", "Database opprettet");
  */

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        // sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
        //onCreate(sqLiteDatabase);

    }

    /*public void addPlace(Sted sted) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, sted.get_stedsNavn());
        values.put(COLUMN_URL, sted.get_url());
        SQLiteDatabase database = this.getWritableDatabase();

        database.insert(TABLE_PLACES, null, values);
        database.close();

    } */

    //Takes in place and returns ID, URL , Name and Type when searched for.
    //This is just for practice this wont be needed in the final app.
   /* public Sted findPlace(String placeName) {

        String query = "Select * FROM " + TABLE_PLACES + " WHERE " + COLUMN_NAME + " =  \"" + placeName + "\"";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        Sted sted = new Sted();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            sted.set_id(Integer.parseInt(cursor.getString(0)));
            sted.set_stedsNavn(cursor.getString(1));
            sted.set_url(cursor.getString(2));
            cursor.close();

        } else {
            sted = null;

        }
        database.close();
        return sted;


    }                 */
   /*
    //Takes in a placeName and deleletes the record if it finds it.
    //This is just for practice this wont be needed in the final app.
   public boolean deletePlace(String placeName) {
        boolean result = false;
        String query = "Select * FROM " + TABLE_PLACES + " WHERE " + COLUMN_NAME + " =  \"" + placeName + "\"";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        Sted sted = new Sted();
        if (cursor.moveToFirst()) {
            sted.set_id(Integer.parseInt(cursor.getString(0)));
            database.delete(TABLE_PLACES, COLUMN_ID + "= ?", new String[]{String.valueOf(sted.get_id())});
            cursor.close();
            result = true;

        }

        database.close();
        return result;

    }

   public boolean checkIfExists(String placeName) {
        boolean recordExists = false;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + COLUMN_ID + " FROM" + TABLE_PLACES + " WHERE " + COLUMN_NAME + " = '" + placeName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                recordExists = true;
            }

        }
        cursor.close();
        database.close();
        return recordExists;
    }    */

    public List<Sted> read(String searchTerm) {
        List<Sted> recordList = new ArrayList<Sted>();
        //Select query
        String sql = "";
        sql += "SELECT * FROM " + DB_NAME;
        sql += " WHERE " + COLUMN_NAME + " LIKE '%" + searchTerm + "%'";
        sql += " ORDER BY " + COLUMN_ID + " DESC";
        sql += " LIMIT 5";


        //Create instance of database
        SQLiteDatabase database = this.getWritableDatabase();


        //execute the query
        Cursor cursor = database.rawQuery(sql, null);

        //Looping rows and adding to the list

        if (cursor.moveToFirst()) {

            do {
                //Create a string with the objectName
                String objectName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                //Create a new "Sted" with the object name
                Sted sted = new Sted(objectName);

                recordList.add(sted);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return recordList;

    }

      /*

    public void createDatabase() {
        boolean dbExists = checkDataBase();

        if (!dbExists) {
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e(this.getClass().toString(), "Copying error");
                throw new Error("Error copying database");

            }
        } else {
            Log.i(this.getClass().toString(), "Database already exists");
        }


    }


    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;
        try {
            String path = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {
            Log.e(this.getClass().toString(), "Error while checking db");

        }

        //Android doesn’t like resource leaks, everything should
        // be closed
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null;
    }

    //Method for copying the database
    private void copyDataBase() throws IOException {
        //Open a stream for reading from our ready-made database
        //The stream source is located in the assets
        InputStream externalDbStream = context.getAssets().open(DB_NAME);

        //Path to the created empty database on your Android device
        String outFileName = DB_PATH + DB_NAME;

        //Now create a stream for writing the database byte by byte
        OutputStream localDbStream = new FileOutputStream(outFileName);

        //Copying the database
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = externalDbStream.read(buffer)) > 0) {
            localDbStream.write(buffer, 0, bytesRead);
        }
        //Don’t forget to close the streams
        localDbStream.close();
        externalDbStream.close();
    }



    public SQLiteDatabase openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        if (database == null) {
            createDatabase();
            database = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }
        return database;
    }

    @Override
    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }

            */


}
