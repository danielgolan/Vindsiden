package com.vindsiden.windwidget.Location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by Daniel on 20.02.14.
 */
public class MyLocationListener implements LocationListener {
    public static double latitude;
    public static double longitude;

    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        String lat = String.valueOf(MyLocationListener.latitude);
        String lon = String.valueOf(MyLocationListener.longitude);
        //  Toast.makeText(Vindsiden.getInstance(), "Location : \n" + "Lat : " + lat + "\n" + "Lontitude : " + lon, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

        //GPS is enabled

    }

    @Override
    public void onProviderDisabled(String s) {


        //GPS is disabled
    }


}
