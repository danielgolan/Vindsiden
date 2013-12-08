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
import android.widget.Button;

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);



        Button b = (Button) findViewById(R.id.button);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s= "Notification";
                Notification notification = new Notification();

               // notification.createNotification(getApplicationContext(),"Title","Message","Other me");


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
    public boolean onOptionsItemSelected(MenuItem menuItem){

        //Switch case to check which menu item user clicked
        switch (menuItem.getItemId()){

            case R.id.settings:
                //Define settings page to be com.vindsiden.windwidget/.Settings
                String SettingsPage = "com.vindsiden.windwidget/.Settings";

                try
                {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setComponent(ComponentName.unflattenFromString(SettingsPage));
                    intent.addCategory(Intent.CATEGORY_LAUNCHER );
                    startActivity(intent);
                }
                catch (ActivityNotFoundException e)

                {
                    //This will be called when activity is not found (Check manifest)
                    Log.d("",e.getStackTrace().toString());
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