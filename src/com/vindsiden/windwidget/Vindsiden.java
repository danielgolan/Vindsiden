package com.vindsiden.windwidget;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.vindsiden.windwidget.config.WindWidgetConfig;
import com.vindsiden.windwidget.model.Measurement;
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
    String spotName;
    String spotID;
    String suggestedSpot;
    AutoCompleteTextView AcTv_sok;
    final Spots spots = new Spots();
    final WindDirection windDirection = new WindDirection();


    Measurement mostRecentMeasurement;
    List<Measurement> measurements = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        //Setting up the GUI
        txt_Name = (TextView) findViewById(R.id.txt_Name);
        txt_Temp = (TextView) findViewById(R.id.txt_Temp);
        txt_windDir = (TextView) findViewById(R.id.txt_windDir);
        txt_avgWind = (TextView) findViewById(R.id.txt_avgWind);
        //  txt_maxWind = (TextView) findViewById(R.id.txt_maxWind);
        //txt_minWind = (TextView) findViewById(R.id.txt_minWind);
        txt_suggestedSpot = (TextView) findViewById(R.id.txt_suggestedSpot);

        //Reset Textboxes
        resetText();
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


        AcTv_sok = (AutoCompleteTextView) findViewById(R.id.AcTv_sok);
        String[] spotsArray = getResources().getStringArray(R.array.spots);
        ArrayAdapter<String> AutoCompleteadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, spotsArray);
        AcTv_sok.setAdapter(AutoCompleteadapter);

        AcTv_sok.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {


                //spotName returner en String med navnet på det som er valgt
                spotName = adapterView.getItemAtPosition(pos).toString();
                //Sender inn spot navn og returnerer en ID
                spotID = spots.getSpotIdFromName(spotName);
                //downloadOneMeasurment tar inn en string med Vindsiden ID.
                String[] input = {spotID};
                new downloadOneMeasurment().execute(input);

            }
        });


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

                    resetText();


                } else if (phonyMeasurment == false) {
                    txt_Name.setText("Viser info for " + spotName);
                    txt_avgWind.setText("Snitt siste vindmåling er :" + mostRecentMeasurement.getWindAvg() + " M/S" + "\n" + "Maks vind : " + mostRecentMeasurement.getWindMax() + " M/S Min. vind : " + mostRecentMeasurement.getWindMin() + " M/S");


                    txt_windDir.setText("Vinden blåser fra " + windDirection.getWindDir(mostRecentMeasurement.getDirectionAvg()));


                    txt_Temp.setText("Temperaturen er : " + mostRecentMeasurement.getTemprature() + " grader");


                }


            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), "Her har det skjedd noe feil, prøv en annen spot", Toast.LENGTH_SHORT).show();

                resetText();
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


    private void resetText() {
        txt_Name.setText("");
        txt_avgWind.setText("");
        txt_Temp.setText("");
        txt_windDir.setText("");


    }

}