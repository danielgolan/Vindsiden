package com.vindsiden.windwidget;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.vindsiden.windwidget.model.Measurement;
import com.vindsiden.windwidget.model.Spots;
import com.vindsiden.windwidget.model.WindDirection;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 03.12.13
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */


public class Notification {

    Spots spots = new Spots();
    WindDirection windDirection = new WindDirection();

    //android.support.v4.app.NotificationCompat.Builder


    public void createNotification(Context context, String title, String message, String summary, int icon) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(message);
        //.set

// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, Settings.class);

// add so it removes
        mBuilder.setAutoCancel(true);

//add vibrate and sound only use if you have the correct permissions in manifest or it will crash
//  long[] vibrate = {0,100,200,300};
//    mBuilder.setVibrate(vibrate);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        mBuilder.setDefaults(android.app.Notification.DEFAULT_LIGHTS);
        mBuilder.setOnlyAlertOnce(true);

        if (summary != null) {
            NotificationCompat.BigTextStyle bigStyle =
                    new NotificationCompat.BigTextStyle();
            // Sets a title for the Inbox style big view
            bigStyle.setBigContentTitle(title);
            bigStyle.setSummaryText(message);
            bigStyle.bigText(summary);


            //set a content

            // Moves the big view style object into the notification object.
            mBuilder.setStyle(bigStyle);


        }

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(Settings.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(200, mBuilder.build());
    }


    public void compileNotification(Context context, Measurement mostRecentMesurment) {
        //Conext can be found through getApplicationContext. Measurement is used so that user


        String avgWind = mostRecentMesurment.getWindAvg();

        int intavgWind = (int) Double.parseDouble(avgWind);

        String maxWind = mostRecentMesurment.getWindMax();
        //int intmaxWind = (int) Double.parseDouble(maxWind);

        String minWind = mostRecentMesurment.getWindMin();
        //int intminWind = (int) Double.parseDouble(minWind);

        String avgDir = mostRecentMesurment.getDirectionAvg();
        //int intavgDir = (int) Double.parseDouble(avgDir);

        String temprature = mostRecentMesurment.getTemprature();
        //int intTemp = (int) Double.parseDouble(temprature);

        String spotID = mostRecentMesurment.getStationID();
        String spotName = spots.getSpotNameFromID(spotID);

        Log.d("VindsidenNotification", "spot ID : " + spotID + "spot name : " + spotName);


        String Message = "Viser vindmåling for : " + spotName + "\n" + "Snitt vidmåling" + avgWind + "\n" + "Vinden blåser " + windDirection.getWindDir(avgDir) + "\n" + "Tempraturen er : " + temprature;

        String anbefaling;


        //TODO : bruker skal kunne endre dette på egenhånd !
        if (intavgWind > 6 && intavgWind < 12.0) { //avgWind større enn 6 og mindre enn 12
            anbefaling = "Passe vind Dra til : " + spots.getSuggestedSpot(avgWind, avgDir);


        } else if (intavgWind > 12.0) {
            anbefaling = "For mye vind";

        } else if (intavgWind < 5) {
            anbefaling = "Finn på noe annet gøy";


        } else {
            anbefaling = "Det skjedde en feil et sted..";
        }


        int icon = R.drawable.ic_launcher;


        Notification notification = new Notification();

        //Context context, String title, String msg, String other)
        notification.createNotification(context, "Vindmåleren melder vind", anbefaling, Message, icon);
    }


}


