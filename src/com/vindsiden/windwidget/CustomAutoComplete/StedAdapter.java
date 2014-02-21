package com.vindsiden.windwidget.CustomAutoComplete;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.vindsiden.windwidget.Database.DataBaseHelper;
import com.vindsiden.windwidget.R;
import com.vindsiden.windwidget.Vindsiden;
import com.vindsiden.windwidget.model.Place;

/**
 * Created by Daniel on 02.02.14.
 */
public class StedAdapter extends ArrayAdapter<String> {

    final String TAG = "com.vindsiden.windwidget.CustomAutoComplete";
    Context mContext;
    int layoutResouceId;

    String[] items = null;


    public StedAdapter(Context context, int layoutResouceId, String[] data) {
        super(context, layoutResouceId, data);
        this.layoutResouceId = layoutResouceId;
        this.mContext = context;
        this.items = data;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Vindsiden) mContext).getLayoutInflater();
                convertView = inflater.inflate(layoutResouceId, parent, false);
            }
            Place place;
            DataBaseHelper dataBaseHelper = new DataBaseHelper(Vindsiden.getInstance());
            place = dataBaseHelper.findPlaceAutoComplete(items[position]);


            //  Take input

            //  ImageView ikon = (ImageView) convertView.findViewById(R.id.imageView);
            TextView stedNavn = (TextView) convertView.findViewById(R.id.txt_AutoName);
            TextView stedsType = (TextView) convertView.findViewById(R.id.txt_AutoType);

            stedNavn.setText(place.PlaceName);
            stedsType.setText(place.PlaceType + " i " + place.Municipality);
/*
            if (place.PlaceType.equals("Kirke")) {
                ikon.setImageResource(R.drawable.type_church);

            } else if (place.PlaceType.equals("Kitespot")) {
                ikon.setImageResource(R.drawable.type_kite);

            } else if (place.PlaceType.equals("Windsurfingspot")) {
                ikon.setImageResource(R.drawable.type_windsurfing);

            } else if (place.PlaceType.equals("By")) {
                ikon.setImageResource(R.drawable.type_city);

            } else if (place.PlaceType.equals("Byområde")) {
                ikon.setImageResource(R.drawable.type_city);

            } else if (place.PlaceType.equals("Flyplass")) {
                ikon.setImageResource(R.drawable.type_airport);

            } else if (place.PlaceType.equals("Turisthytte")) {
                ikon.setImageResource(R.drawable.type_hut);

            } else {
                ikon.setImageDrawable(null);
            } */


        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;

    }

}
