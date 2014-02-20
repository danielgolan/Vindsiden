package com.vindsiden.windwidget.model;

/**
 * Created by Daniel on 23.01.14.
 */
public class Sted {

    private int id;
    public String KommuneNummer;
    public String StedNavn;
    public String Prioritet;
    public String StedType;
    public String Kommune;
    public String Fylke;
    private float lat, lon;
    public String Hoyde;
    public String NB_url;
    public String EN_url;
    public String Vindsiden_URL;
    public String Kilde;
    public String Informasjon;
    public String Veibeskrivelse;
    public String Vannforhold;
    public String Fasiliteter;
    public String Egnet_for;
    public String Vindretning;

    public int getTimesUsed() {
        return TimesUsed;
    }

    public void setTimesUsed(int timesUsed) {
        TimesUsed = timesUsed;
    }

    public int TimesUsed;


    public Sted() {

    }

    public Sted(int id, String stedNavn, String NB_url) {

        this.id = id;
        this.StedNavn = stedNavn;
        this.NB_url = NB_url;

    }

    public Sted(String stedNavn, String NB_url) {

        this.StedNavn = stedNavn;
        this.NB_url = NB_url;


    }

    public Sted(String stedNavn) {

        this.StedNavn = stedNavn;


    }

    public String getInformasjon() {
        return Informasjon;
    }

    public void setInformasjon(String informasjon) {
        Informasjon = informasjon;
    }

    public String getVeibeskrivelse() {
        return Veibeskrivelse;
    }

    public void setVeibeskrivelse(String veibeskrivelse) {
        Veibeskrivelse = veibeskrivelse;
    }

    public String getVannforhold() {
        return Vannforhold;
    }

    public void setVannforhold(String vannforhold) {
        Vannforhold = vannforhold;
    }

    public String getFasiliteter() {
        return Fasiliteter;
    }

    public void setFasiliteter(String fasiliteter) {
        Fasiliteter = fasiliteter;
    }

    public String getEgnet_for() {
        return Egnet_for;
    }

    public void setEgnet_for(String egnet_for) {
        Egnet_for = egnet_for;
    }

    public String getVindretning() {
        return Vindretning;
    }

    public void setVindretning(String vindretning) {
        Vindretning = vindretning;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKommune() {
        return Kommune;
    }

    public void setKommune(String kommune) {
        this.Kommune = kommune;
    }

    public String getNB_url() {
        return NB_url;
    }

    public void setNB_url(String NB_url) {
        this.NB_url = NB_url;
    }

    public String getStedType() {
        return StedType;
    }

    public void setStedType(String stedType) {
        this.StedType = stedType;
    }


    public String getEN_url() {
        return EN_url;
    }

    public void setEN_url(String EN_url) {
        this.EN_url = EN_url;
    }

    public String getHoyde() {
        return Hoyde;
    }

    public void setHoyde(String hoyde) {
        Hoyde = hoyde;
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

    public String getStedNavn() {
        return StedNavn;
    }

    public void setStedNavn(String stedNavn) {
        StedNavn = stedNavn;
    }


    public String getFylke() {
        return Fylke;
    }

    public void setFylke(String fylke) {
        Fylke = fylke;
    }

    public String getVindsiden_URL() {
        return Vindsiden_URL;
    }

    public void setVindsiden_URL(String vindsiden_URL) {
        Vindsiden_URL = vindsiden_URL;
    }

    public String getPrioritet() {
        return Prioritet;
    }

    public void setPrioritet(String prioritet) {
        Prioritet = prioritet;
    }

    public String getKommuneNummer() {
        return KommuneNummer;
    }

    public void setKommuneNummer(String kommuneNummer) {
        KommuneNummer = kommuneNummer;
    }

    public String getKilde() {
        return Kilde;
    }

    public void setKilde(String kilde) {
        Kilde = kilde;
    }

}
