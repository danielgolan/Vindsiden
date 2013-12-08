package com.vindsiden.windwidget.model;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 28.11.13
 * Time: 21:17
 * To change this template use File | Settings | File Templates.
 */
public class WindWidgetStation {
    int index;
    int stationId;
    String stationName;

    public int getIndex() {
        return index;
    }

    public int getStationId() {
        return stationId;
    }

    public String getStationName() {
        return stationName;
    }


    WindWidgetStation(int index, int stationId, String stationName) {
        this.index = index;
        this.stationId = stationId;
        this.stationName = stationName;
    }

}
