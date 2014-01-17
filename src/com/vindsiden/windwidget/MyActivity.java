package com.vindsiden.windwidget;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.vindsiden.windwidget.model.Measurement;
import com.vindsiden.windwidget.model.Spots;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 30.11.13
 * Time: 12:15
 * To change this template use File | Settings | File Templates.
 */


public class MyActivity extends Activity {

    //Activity which basicly is the GUI. The user can click the menu button to configure widgets from here.
    //TODO: Daniel add some fancy wind graphs
    TextView txt_spotName;
    TextView txt_spotTemp;
    TextView txt_spotWind;
    Measurement measurement2;
    public Boolean b;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        txt_spotName = (TextView) findViewById(R.id.txt_spotName);
        txt_spotTemp = (TextView) findViewById(R.id.txt_spotTemp);
        txt_spotWind = (TextView) findViewById(R.id.txt_spotWindDir);


        final Spinner spotChooser = (Spinner) findViewById(R.id.spin_spotvelger);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spots, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spotChooser.setAdapter(adapter);

        spotChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                //User selected a value in the spinner
                //String s = String.valueOf(pos);
                String spotName = (String) spotChooser.getItemAtPosition(pos);
                

              
                Spots spots = new Spots();
                spotName = spots.getSpotIdFromName(spotName);
                //spotName now equals the ID instead of the Name
                //Call the updateNow function with the ID and a debug parameter.
                VindsidenUpdateService vindsidenUpdateService = new VindsidenUpdateService();
                vindsidenUpdateService.updateNow(spotName, "user");
                
                //Here the code shoud somehow wait untill "measurement2" is set by the Async task.
                //My biggest issue that i dont know how to do this code right.
                
                
                
                try {
                    txt_spotName.setText(measurement2.getStationID());

                } catch (Exception e) {


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }
    
    
    public void setTextView (String s ){
        //This should in theory work but i cant get it to setText.
        //The error i get is basicly that "txt_spotname" = null
        txt_spotName.setText(s);
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


}
