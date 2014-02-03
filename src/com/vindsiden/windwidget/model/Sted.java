package com.vindsiden.windwidget.model;

/**
 * Created by Daniel on 23.01.14.
 */
public class Sted {


    private int _id;
    public String _stedsNavn;
    private String _url;

    public Sted() {

    }

    public Sted(int id, String StedsNavn, String Url) {

        this._id = id;
        this._stedsNavn = StedsNavn;
        this._url = Url;

    }

    public Sted(String StedsNavn, String Url) {

        this._stedsNavn = StedsNavn;
        this._url = Url;


    }

    public Sted(String StedsNavn) {

        this._stedsNavn = StedsNavn;


    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_stedsNavn() {
        return _stedsNavn;
    }

    public void set_stedsNavn(String _stedsNavn) {
        this._stedsNavn = _stedsNavn;
    }

    public String get_url() {
        return _url;
    }

    public void set_url(String _url) {
        this._url = _url;
    }


}
