package com.vindsiden.windwidget.model;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 28.11.13
 * Time: 21:16
 * To change this template use File | Settings | File Templates.
 */

import android.appwidget.AppWidgetProvider;
import android.util.Log;

public class Measurement {
    /**
     * A single measurement of the wind - normally created from Vindsiden.com XML.
     * {@inheritDoc}
     */

    private final String tag = AppWidgetProvider.class.getName(); // getSimpleName());

    private final String stationID, time, windAvg, directionAvg,windMax,windMin,temprature;

    public Measurement(String stationID, String time, String windAvg, String windMax,String windMin, String temprature, String directionAvg) {
        Log.d("vindsiden" + tag, "Measurement created: Station: " + stationID + " time " + time + " windAvg: " + windAvg + " dir: " + directionAvg +" windmax: " + windMax +" windmin: " + windMin +" teno: " + temprature);
        this.stationID = stationID;
        this.time = time;
        this.windAvg = windAvg;
        this.directionAvg = directionAvg;
        this.windMax = windMax;
        this.windMin = windMin;
        this.temprature = temprature;
    }

    public String getStationID() {
        return stationID;
    }

    public String getTime() {
        return time;
    }

    public String getWindAvg() {
        return windAvg;
    }

    public String getDirectionAvg() {
        return directionAvg;
    }

    public String getWindMax() {
        return windMax;
    }

    public String getWindMin() {
        return windMin;
    }

    public String getTemprature() {
        return temprature;
    }
}
