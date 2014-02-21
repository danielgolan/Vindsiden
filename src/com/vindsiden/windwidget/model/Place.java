package com.vindsiden.windwidget.model;

/**
 * Created by Daniel on 23.01.14.
 */
public class Place {

    private int id;
    public String MunicipalityNumber;
    public String PlaceName;
    public String Priority;
    public String PlaceType;
    public String Municipality; //Kommune
    public String County;
    private float lat, lon;
    public String Height;
    public String NB_url;
    public String EN_url;
    public String Vindsiden_URL;
    public String Source;
    public String Description;
    public String RoadDescription;
    public String WaterConditions;
    public String Facilities;
    public String Suited_For;
    public String WindDirection;

    public int getTimesUsed() {
        return TimesUsed;
    }

    public void setTimesUsed(int timesUsed) {
        TimesUsed = timesUsed;
    }

    public int TimesUsed;


    public Place() {

    }

    /**
     * Creates a new Place with an ID, placeName and NB_url
     *
     * @param id        id in form of a number which the database uses
     * @param placeName placeName as a string
     * @param NB_url    Yr URL
     */

    public Place(int id, String placeName, String NB_url) {

        this.id = id;
        this.PlaceName = placeName;
        this.NB_url = NB_url;

    }

    public Place(String placeName, String NB_url) {

        this.PlaceName = placeName;
        this.NB_url = NB_url;


    }

    public Place(String placeName) {

        this.PlaceName = placeName;


    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getRoadDescription() {
        return RoadDescription;
    }

    public void setRoadDescription(String roadDescription) {
        RoadDescription = roadDescription;
    }

    public String getWaterConditions() {
        return WaterConditions;
    }

    public void setWaterConditions(String waterConditions) {
        WaterConditions = waterConditions;
    }

    public String getFacilities() {
        return Facilities;
    }

    public void setFacilities(String facilities) {
        Facilities = facilities;
    }

    public String getSuited_For() {
        return Suited_For;
    }

    public void setSuited_For(String suited_For) {
        Suited_For = suited_For;
    }

    public String getWindDirection() {
        return WindDirection;
    }

    public void setWindDirection(String windDirection) {
        WindDirection = windDirection;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMunicipality() {
        return Municipality;
    }

    public void setMunicipality(String municipality) {
        this.Municipality = municipality;
    }

    public String getNB_url() {
        return NB_url;
    }

    public void setNB_url(String NB_url) {
        this.NB_url = NB_url;
    }

    public String getPlaceType() {
        return PlaceType;
    }

    public void setPlaceType(String placeType) {
        this.PlaceType = placeType;
    }


    public String getEN_url() {
        return EN_url;
    }

    public void setEN_url(String EN_url) {
        this.EN_url = EN_url;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public String getPlaceName() {
        return PlaceName;
    }

    public void setPlaceName(String placeName) {
        PlaceName = placeName;
    }


    public String getCounty() {
        return County;
    }

    public void setCounty(String county) {
        County = county;
    }

    /**
     *
     * @return returns the Vindsiden URL for the selected Place
     */
    public String getVindsiden_URL() {
        return Vindsiden_URL;
    }

    public void setVindsiden_URL(String vindsiden_URL) {
        Vindsiden_URL = vindsiden_URL;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    /**
     * @return returns the MunicipalityNumber for the selected Place
     * Not used often
     */
    public String getMunicipalityNumber() {
        return MunicipalityNumber;
    }

    public void setMunicipalityNumber(String municipalityNumber) {
        MunicipalityNumber = municipalityNumber;
    }

    /**
     * @return returns the Source for the selected Place
     */
    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

}
