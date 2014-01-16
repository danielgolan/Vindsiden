package com.vindsiden.windwidget.model;

/**
 * Created by Daniel on 14.12.13.
 */
public class Spots {

    public String getWindDirectionFromDegrees(String avgWind, String direction) {

        int intDegrees = (int) Double.parseDouble(direction);
        int intavgWind = Integer.parseInt(avgWind);
        String suggestedSpot = "";


        if (intDegrees > 135 && intDegrees < 270 && intavgWind > 6) {
            //If wind is correct degree and windstrengt is over 6 ms larkollen is suggested

            suggestedSpot = suggestedSpot + "Larkollen";
        } else if (intDegrees > 135 && intDegrees < 270 && intavgWind > 6) {
            suggestedSpot = suggestedSpot + "Verket";
        } else if (intDegrees > 157.5 && intDegrees < 202.5 && intavgWind > 6 || intDegrees > 247.5 && intDegrees < 292.5 && intavgWind > 6) {
            suggestedSpot = suggestedSpot + "Gilhusodden";
        } else if (intDegrees > 157.5 && intDegrees < 202.5 && intavgWind > 6 || intDegrees > 337.5 && intDegrees < 360 && intavgWind > 6 || intDegrees > 0 && intDegrees < 22.5 && intavgWind > 6) {
            suggestedSpot = suggestedSpot + "Ekebergsletta";
        } else if (intDegrees > 202.5 && intDegrees < 360 && intavgWind > 6 || intDegrees > 0 && intDegrees < 22.5 && intavgWind > 6) {
            suggestedSpot = suggestedSpot + "Refsnes - Jeløya";
        } else if (intDegrees > 112.5 && intDegrees < 247.5 && intavgWind > 6) {
            suggestedSpot = suggestedSpot + "Framnes - Jeløya";
        } else if (intDegrees > 0 && intDegrees < 157.5 && intavgWind > 6) {
            suggestedSpot = suggestedSpot + "Feskjær";
        } else if (intDegrees > 67.5 && intDegrees < 202.5 && intavgWind > 6) {
            suggestedSpot = "Larkollen";
        }


        return suggestedSpot;
    }


    public String getSpotIdFromName(String spotName) {
        String spotID = "";
        if (spotName.equals("Larkollen")) {
            spotID = "1";
        } else if (spotName.equals("Hvasser")) {

            spotID = "51";
        }


        return spotID;
    }
}
