package com.vindsiden.windwidget;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.vindsiden.windwidget.model.Sted;

/**
 * Created by Daniel on 04.02.14.
 */
public class SpotDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spotdetail1);

        TextView tv_Navn = (TextView) findViewById(R.id.tv_Stedsnavn);
        TextView tv_Bygd = (TextView) findViewById(R.id.tv_StedType);
        TextView tv_Kommune = (TextView) findViewById(R.id.tv_kommune);
        TextView tv_Informasjon = (TextView) findViewById(R.id.txt_detail_Informasjon);
        TextView tv_EgnetFor = (TextView) findViewById(R.id.txt_detail_EgnetFor);
        TextView tv_InformasjonHeading = (TextView) findViewById(R.id.txt_detail_InformasjonHeading);
        TextView tv_EgnetForHeading = (TextView) findViewById(R.id.txt_detail_EgnetForHeading);
        TextView tv_Fasiliteter = (TextView) findViewById(R.id.txt_detail_Fasiliteter);
        TextView tv_FasiliteterHeading = (TextView) findViewById(R.id.txt_detail_FasiliteterHeading);
        TextView tv_Vannforhold = (TextView) findViewById(R.id.txt_detail_Vannforhold);
        TextView tv_VannforholdHeading = (TextView) findViewById(R.id.txt_detail_VannforholdHeading);

        TextView tv_Url = (TextView) findViewById(R.id.tv_url);
        WebView webView = (WebView) findViewById(R.id.ww_Yr);
        Button b_navigate = (Button) findViewById(R.id.b_navigate);

        //Recive intent
        Intent intent = getIntent();
        //Set strings to information from the intent
        Sted sted = new Sted();
        sted.setStedNavn(intent.getStringExtra("StedsNavn"));
        sted.setKommune(intent.getStringExtra("Kommune"));

        sted.setStedType(intent.getStringExtra("Type"));
        sted.setEN_url(intent.getStringExtra("EN_Url"));
        sted.setNB_url(intent.getStringExtra("NB_Url"));
        sted.setLat(intent.getFloatExtra("Lat", 0));
        sted.setLon(intent.getFloatExtra("Lon", 0));
        sted.setInformasjon(intent.getStringExtra("Informasjon"));
        sted.setEgnet_for(intent.getStringExtra("Egnet_for"));
        sted.setKilde(intent.getStringExtra("Kilde"));
        sted.setFasiliteter(intent.getStringExtra("Fasiliteter"));
        sted.setVannforhold(intent.getStringExtra("Vannforhold"));


        final String Lon_String = String.valueOf(sted.getLon());

        final String Lat_String = String.valueOf(sted.getLat());


        b_navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start a new intent to navigate to location
                String coordinates = Lat_String + "," + Lon_String;
                //This will fail if google maps is not installed
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + coordinates));
                    startActivity(i);
                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), "Vennligst sjekk at du har installert Google Maps", Toast.LENGTH_SHORT).show();
                }


            }
        });


        tv_Navn.setText(sted.getStedNavn());
        tv_Bygd.setText(sted.getStedType());

        if (sted.getKilde().equals("Vindsiden")) {
            tv_Informasjon.setText(sted.getInformasjon());
            tv_EgnetFor.setText(sted.getEgnet_for());
            tv_Fasiliteter.setText(sted.getFasiliteter());
            tv_Vannforhold.setText(sted.getVannforhold());
        } else {
            tv_Informasjon.setVisibility(View.GONE);
            tv_EgnetFor.setVisibility(View.GONE);
            tv_EgnetForHeading.setVisibility(View.GONE);
            tv_InformasjonHeading.setVisibility(View.GONE);
            tv_Fasiliteter.setVisibility(View.GONE);
            tv_FasiliteterHeading.setVisibility(View.GONE);
            tv_Vannforhold.setVisibility(View.GONE);
            tv_VannforholdHeading.setVisibility(View.GONE);
        }

        //tv_Url.setText(Url);
        //Linkify.addLinks(tv_Url,Linkify.ALL) ;
        // tv_Url.getLinksClickable(true);
        tv_Kommune.setText(sted.getKommune());
        String URL = sted.getNB_url();
        URL = URL.replace("/varsel.xml", "");


        String webUrl = URL + "/meteogram.png";
        webView.loadUrl(webUrl);


        // String value = getIntent().getStringExtra(var); // Getting the value
    }
}
