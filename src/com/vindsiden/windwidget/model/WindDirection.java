package com.vindsiden.windwidget.model;

/**
 * Created by Daniel on 13.12.13.
 */
public class WindDirection {

    //TODO Bruk strings.xml og ikke hardkodet string

    public String getWindDir(String degrees) {


        int intDegrees = (int) Double.parseDouble(degrees);
        String direction = "";


        if (intDegrees > 0 && intDegrees < 11.25) {
            direction = "Nord";


        } else if (intDegrees > 11.25 && intDegrees < 33.75) {
            direction = "Nord-Nordøst";


        } else if (intDegrees > 33.75 && intDegrees < 56.25) {
            direction = "Nordøst";


        } else if (intDegrees > 56.25 && intDegrees < 78.75) {
            direction = "Øst-Nordøst";


        } else if (intDegrees > 78.75 && intDegrees < 101.25) {
            direction = "Øst";


        } else if (intDegrees > 101.25 && intDegrees < 123.75) {
            direction = "Øst-Sørøst";


        } else if (intDegrees > 123.75 && intDegrees < 146.25) {
            direction = "Sørøst";


        } else if (intDegrees > 146.25 && intDegrees < 168.75) {
            direction = "Sør-Sørøst";


        } else if (intDegrees > 168.75 && intDegrees < 191.25) {
            direction = "Sør";


        } else if (intDegrees > 191.25 && intDegrees < 213.75) {
            direction = "Sør-Sørvest";


        } else if (intDegrees > 213.75 && intDegrees < 236.75) {
            direction = "Sørvest";


        } else if (intDegrees > 236.25 && intDegrees < 258.75) {
            direction = "Vest-Sørvest";


        } else if (intDegrees > 258.75 && intDegrees < 281.25) {
            direction = "Vest";


        } else if (intDegrees > 281.25 && intDegrees < 303.75) {
            direction = "Vest-Nordvest";


        } else if (intDegrees > 303.75 && intDegrees < 326.25) {
            direction = "Nordvest";


        } else if (intDegrees > 326.25 && intDegrees < 348.75) {
            direction = "Nord-Nordvest";


        } else if (intDegrees > 348.75 && intDegrees < 360) {
            direction = "Nord";


        } else {
            direction = "Noe feil må ha skjedd her";
        }

           /*
            N North 0°
        NNE North -northeast 22.5°
        NE NorthEast 45°
        ENE East -northeast 67.5°
        E East 90°
        ESE East -southeast 112.5°
        SE SouthEast 135°
        SSE South -southeast 157.5°
        S South 180°
        SSW South -southwest 202.5°
        SW Southwest 225°
        WSW West -southwest 247.5°
        W West 270°
        WNW West -northwest 292.5°
        NW Northwest 315°
        NNW North -northwest 337.5°
        Copied from :     http://www.windfinder.com/wind/windspeed.htm
        */


        return direction;
    }


}
