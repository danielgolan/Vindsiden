package com.vindsiden.windwidget;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.vindsiden.windwidget.CustomAutoComplete.CustomAutoCompleteView;
import com.vindsiden.windwidget.CustomAutoComplete.CustomAutocompleteTextChangedListener;
import com.vindsiden.windwidget.Database.DataBaseHelper;
import com.vindsiden.windwidget.Location.MyLocationListener;
import com.vindsiden.windwidget.config.WindWidgetConfig;
import com.vindsiden.windwidget.model.Measurement;
import com.vindsiden.windwidget.model.Place;
import com.vindsiden.windwidget.model.Spots;
import com.vindsiden.windwidget.model.WindDirection;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 30.11.13
 * Time: 12:15
 * To change this template use File | Settings | File Templates.
 */


public class Vindsiden extends Activity {

    //Activity which basicly is the GUI. The user can click the menu button to configure widgets from here.
    //TODO: Daniel add some fancy wind graphs
    TextView txt_Name; //Spot name
    TextView txt_Temp; //spot temprature
    //TextView txt_minWind; //Spot minimum wind (last 10 minutes)
    //TextView txt_maxWind; //Spot maximum wind (last 10 minutes)
    TextView txt_avgWind; //Spot average wind (last 10 minutes)
    TextView txt_windDir; //Spot wind direction (last 10 minutes)
    TextView txt_suggestedSpot;
    public String spotName;
    String spotID;
    String suggestedSpot;
    public CustomAutoCompleteView AcTv_sok;
    Button leggtilSted;
    Button soekPaSted;
    Button slettSted;
    EditText et_soek;
    EditText et_URL;
    static Vindsiden vindsiden;


    final Spots spots = new Spots();
    final WindDirection windDirection = new WindDirection();


    Measurement mostRecentMeasurement;
    List<Measurement> measurements = null;

    // adapter for auto-complete
    public ArrayAdapter<String> myAdapter;

    // for database operations
    // public DatabaseHandler databaseHandler;


    // just to add some initial value
    public String[] item = new String[]{"Please search..."};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ViewServer.get(this).addWindow(this);
        vindsiden = this;

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener mlocationListener = new MyLocationListener();


        //     locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocationListener);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //if (MyLocationListener.latitude>0){
            String lat = String.valueOf(MyLocationListener.latitude);
            String lon = String.valueOf(MyLocationListener.longitude);
            // Toast.makeText(getApplicationContext(),"Location : \n" + "Lat : " + lat + "\n" + "Lontitude : " + lon,Toast.LENGTH_LONG).show();


            //}
        }


        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        // dataBaseHelper = new DataBaseHelper(this);


        try {

            dataBaseHelper.createDatabase();

        } catch (Exception e) {

            throw new Error("Unable to create database");

        }

        try {

            //    dataBaseHelper.openDataBase();

        } catch (Exception e) {

            Log.e("Database", "Error");

        }


        //Setting up the GUI

        txt_avgWind = (TextView) findViewById(R.id.txt_avgWind);
        txt_suggestedSpot = (TextView) findViewById(R.id.txt_suggestedSpot);


        //Reset Textboxes

//

        CharSequence[] strings;

        Resources res = getResources();
        strings = res.getTextArray(R.array.spots);
        String s = "";
        for (int i = 0; i < strings.length; i++) {
            s = strings[i].toString();
            //Log.d("Vindsiden-Test",s);
            spotID = spots.getSpotIdFromName(s);
            if (spotID.equals("")) {

            } else {
                String[] input = {spotID};
                new GetSuggestedSpot().execute(input);

            }

        }


        try {

            //databaseHandler = new DatabaseHandler(Vindsiden.this);
            //dataBaseHelper = new DataBaseHelper(Vindsiden.this);

            AcTv_sok = (CustomAutoCompleteView) findViewById(R.id.AcTv_sok);
            AcTv_sok.addTextChangedListener(new CustomAutocompleteTextChangedListener(this));
            myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
            AcTv_sok.setAdapter(myAdapter);


        } catch (Exception e) {


        }


        AcTv_sok.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {


                //spotName returner en String med navnet på det som er valgt
                //spotName = adapterView.getItemAtPosition(pos).toString();
                //Sender inn spot navn og returnerer en ID
                //spotID = spots.getSpotIdFromName(spotName);
                //downloadOneMeasurment tar inn en string med Vindsiden ID.
                // String[] input = {spotID};
                //  new downloadOneMeasurment().execute(input);


                SoekSted();

            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }

    //OnOptionsItemSelected is called when user clicks the menu/overflow button
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        //Switch case to check which menu item user clicked
        switch (menuItem.getItemId()) {

            case R.id.settings:
                //Define settings page to be com.vindsiden.windwidget/.Settings
                String SettingsPage = "com.vindsiden.windwidget/.Settings";

                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setComponent(ComponentName.unflattenFromString(SettingsPage));
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    startActivity(intent);
                } catch (ActivityNotFoundException e)

                {
                    //This will be called when activity is not found (Check manifest)
                    Log.d("", e.getStackTrace().toString());
                }

                break;

            case R.id.About:
                About.Show(this);


                //TODO : Add featurerequest/send email to developer
                //http://stackoverflow.com/questions/2197741/how-to-send-email-from-my-android-application


        }
        return true;


    }


    public static Vindsiden getInstance() {
        return vindsiden;
    }

    class downloadOneMeasurment extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... data) {

            String s = data.toString();
            Log.d("Vindsiden2 + Convert array to string", s);

            try {
                String urlString = WindWidgetConfig.getVindsidenUrlPrefix() + data[0].toString()
                        + WindWidgetConfig.getVindsidenUrlPostfix();
                Log.d("Vindsiden2", urlString);
                measurements = (new VindsidenWebXmlReader()).loadXmlFromNetwork(urlString);

                //Filldata here


            } catch (IOException e) {
                Log.d("Vindsiden2", "An IO exception occured. Stack follows: ");
                Log.d("Vindsiden2", e.getStackTrace().toString());
                // xmlRetrievalSuccessful = false;
                // not certain how robust throwing a runtime exception is, might break stuff with recurrence etc!
                // throw new RuntimeException(getResources().getString(R.string.connection_error));
            } catch (XmlPullParserException e) {
                Log.d("Vindsiden", "An XmlPullParserException occured. Stack follows: ");
                Log.d("Vindsiden", e.getStackTrace().toString());
                //xmlRetrievalSuccessful = false;
                // throw new RuntimeException(getResources().getString(R.string.xml_error));
            }
            Log.d("Vindsiden ", "Got data, now find measurment");


            return null;
        }


        protected void onPostExecute(String result) {
            boolean phonyMeasurment = false;


            try {
                mostRecentMeasurement = measurements.get(0);

                float temp = Float.valueOf(mostRecentMeasurement.getTemprature());
                if (temp <= (-20)) {
                    phonyMeasurment = true;

                } else if (temp >= (40)) {
                    phonyMeasurment = true;


                }

                {
                    phonyMeasurment = false;
                }

                if (phonyMeasurment == true) {
                    Toast.makeText(getApplicationContext(), "Her har det skjedd noe feil hos vindsiden, prøv en annen spot", Toast.LENGTH_SHORT).show();


                } else if (phonyMeasurment == false) {
                    txt_Name.setText("Viser info for " + spotName);
                    txt_avgWind.setText("Snitt siste vindmåling er :" + mostRecentMeasurement.getWindAvg() + " M/S" + "\n" + "Maks vind : " + mostRecentMeasurement.getWindMax() + " M/S Min. vind : " + mostRecentMeasurement.getWindMin() + " M/S");


                    txt_windDir.setText("Vinden blåser fra " + windDirection.getWindDir(mostRecentMeasurement.getDirectionAvg()));


                    txt_Temp.setText("Temperaturen er : " + mostRecentMeasurement.getTemprature() + " grader");


                }


            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), "Her har det skjedd noe feil, prøv en annen spot", Toast.LENGTH_SHORT).show();


                Log.d("Vindsiden4", e.toString());

            }


        }


    }

    class GetSuggestedSpot extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... data) {

            String s = data.toString();


            try {
                String urlString = WindWidgetConfig.getVindsidenUrlPrefix() + data[0].toString()
                        + WindWidgetConfig.getVindsidenUrlPostfix();
                Log.d("VindsidenSuggestedSpot", "URL downloading : " + urlString);
                measurements = (new VindsidenWebXmlReader()).loadXmlFromNetwork(urlString);

                //Filldata here


            } catch (IOException e) {
                Log.d("Vindsiden2", "An IO exception occured. Stack follows: ");
                Log.d("Vindsiden2", e.getStackTrace().toString());
                // xmlRetrievalSuccessful = false;
                // not certain how robust throwing a runtime exception is, might break stuff with recurrence etc!
                // throw new RuntimeException(getResources().getString(R.string.connection_error));
            } catch (XmlPullParserException e) {
                Log.d("Vindsiden", "An XmlPullParserException occured. Stack follows: ");
                Log.d("Vindsiden", e.getStackTrace().toString());
                //xmlRetrievalSuccessful = false;
                // throw new RuntimeException(getResources().getString(R.string.xml_error));
            }


            return null;
        }


        protected void onPostExecute(String result) {


            boolean phonyMeasurment = false;


            try {
                mostRecentMeasurement = measurements.get(0);

                float temp = Float.valueOf(mostRecentMeasurement.getTemprature());

                if (temp <= (-20)) {
                    phonyMeasurment = true;

                } else {
                    phonyMeasurment = false;
                }

                if (phonyMeasurment == true) {
                    // Toast.makeText(getApplicationContext(), "Her har det skjedd noe feil, prøv en annen spot (Anbefalt spot)", Toast.LENGTH_SHORT).show();


                } else if (phonyMeasurment == false) {
                    //Get suggested spot


                    Log.d("VindsidenSuggestedSpot", "wind avg : " + mostRecentMeasurement.getWindAvg());
                    Log.d("VindsidenSuggestedSpot", "Direction avg: " + mostRecentMeasurement.getDirectionAvg());
                    suggestedSpot += spots.getSuggestedSpot(mostRecentMeasurement.getWindAvg(), mostRecentMeasurement.getDirectionAvg());
                    Log.d("VindsidenSuggestedSpot", "Suggested spot : " + suggestedSpot);

                    if (suggestedSpot.equals("null")) {
                        suggestedSpot = "Pr. nå er det ingen anbefalte spotter";

                    }
                    txt_suggestedSpot.setText(suggestedSpot);


                }


            } catch (Exception e) {

                // Toast.makeText(getApplicationContext(), "Her har det skjedd noe feil, prøv en annen spot (Anbefalt spot)", Toast.LENGTH_SHORT).show();


                Log.d("VindsidenSuggestedSpot", e.toString());

            }


        }

    }


    public Place createSted(String stednavn) {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(Vindsiden.getInstance());
        Place place1 = dataBaseHelper.findPlace(stednavn);
        return place1;
    }

    public void SoekSted() {
        //is triggered when a user clicks the choosen spot.

        DataBaseHelper dataBaseHelper = new DataBaseHelper(Vindsiden.this);
        Place place = dataBaseHelper.findPlace(AcTv_sok.getText().toString());
        if (place != null) {
            //TODO Bygg om dette for å få mer info. Feks "getId" må endres til annen info
            //    et_soek.setText("ID = " + String.valueOf(place.getId()) + "Navn er : " + place.getPlaceType());
            //    et_URL.setText("URL er : " + place.getNB_url());

        } else {

            et_soek.setText("Fant ikkeno !");
        }

        Intent myIntent = new Intent(Vindsiden.this, SpotDetails.class);
        myIntent.putExtra("StedsNavn", place.getPlaceName());
        /*
        myIntent.putExtra("Municipality", place.getMunicipality());
        myIntent.putExtra("Type", place.getPlaceType());
        myIntent.putExtra("NB_Url", place.getNB_url());
        myIntent.putExtra("EN_Url", place.getEN_url());
        myIntent.putExtra("Lon", place.getLon());
        myIntent.putExtra("Lat", place.getLat());
        myIntent.putExtra("KomuneNummer", place.getMunicipalityNumber());
        myIntent.putExtra("Priority", place.getPriority());
        myIntent.putExtra("County", place.getCounty());
        myIntent.putExtra("Height", place.getHeight());
        myIntent.putExtra("Vindsiden_URL", place.getVindsiden_URL());
        myIntent.putExtra("Source", place.getSource());
        myIntent.putExtra("Description", place.getDescription());
        myIntent.putExtra("RoadDescription", place.getRoadDescription());
        myIntent.putExtra("WaterConditions", place.getWaterConditions());
        myIntent.putExtra("Facilities", place.getFacilities());
        myIntent.putExtra("Suited_For", place.getSuited_For());
        myIntent.putExtra("WindDirection", place.getWindDirection());  */

        //Update database and add another item to the List of most used places.


        startActivity(myIntent);
        //spotName = place.getPlaceName();
        AcTv_sok.setText("");


    }

    public String[] getSpotsInTheArea(Location location) {

        return null;
    }


    public String[] getItemsFromDb(String searchTerm) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(Vindsiden.this);
        //databaseHandler = new DatabaseHandler(Vindsiden.this);
        //Add items on to the array dnamicly
        List<Place> places = dataBaseHelper.CreateSuggestionList(searchTerm);
        int rowCount = places.size();

        String[] item = new String[rowCount];
        int x = 0;
        for (Place place : places) {
            item[x] = place.PlaceName;

            x++;

        }
        return item;

    }


}