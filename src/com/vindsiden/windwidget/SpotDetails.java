package com.vindsiden.windwidget;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.vindsiden.windwidget.config.WindWidgetConfig;
import com.vindsiden.windwidget.model.Measurement;
import com.vindsiden.windwidget.model.Sted;
import com.vindsiden.windwidget.model.WindDirection;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Daniel on 04.02.14.
 */
public class SpotDetails extends Activity {

    Measurement mostRecentMeasurement;
    List<Measurement> measurements = null;
    Sted sted = new Sted();
    WindDirection windDirection = new WindDirection();
    String windavg;
    float Start, End;
    Vindsiden vindsiden = new Vindsiden();


    FrameLayout FrameLayout;
    ImageView OverviewWindRose, OverviewArrow;
    TextView tv_ms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spotdetail1);
        //Recive intent
        Intent intent = getIntent();
        //Set strings to information from the intent

        //Create a intance of Sted with information
        Sted sted = vindsiden.createSted(intent.getStringExtra("StedsNavn"));

        //
        FrameLayout = (FrameLayout) findViewById(R.id.frameLayout5);
        WindRose windRose = new WindRose(SpotDetails.this);
        //Add a new windRose (Which is created under)
        FrameLayout.addView(windRose);
        OverviewWindRose = (ImageView) findViewById(R.id.iv_overview_image);
        OverviewArrow = (ImageView) findViewById(R.id.iv_overview_arrow);
        //Trick to getting the WindRose to stay in the Background
        OverviewWindRose.bringToFront();
        OverviewArrow.bringToFront();

        tv_ms = (TextView) findViewById(R.id.tv_overview_windAVG);


        if (sted.getVindretning().contains("-")) {
            String s = sted.getVindretning();
            String[] strings = s.split("-");
            Start = windDirection.getWindDeg(strings[0]);
            End = windDirection.getWindDeg(strings[1]);

        } else if (sted.getVindretning().contains("Alle")) {
            Start = 0;
            End = 360;
        } else {
            Log.d("Overview", "Strind is not Valid");
        }


        ActionBar actionBar = getActionBar();
        actionBar.setTitle(sted.getStedNavn());

        actionBar.setSubtitle(sted.getStedType() + " i " + sted.getKommune());


        final String Lon_String = String.valueOf(sted.getLon());

        final String Lat_String = String.valueOf(sted.getLat());


       /*
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
        });    */

      /*
        tv_Navn.setText(sted.getStedNavn());
        tv_Bygd.setText(sted.getStedType());

        if (sted.getKilde().equals("Vindsiden")||sted.getKilde().equals("Kitemekka")) {
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
        webView.loadUrl(webUrl);  */

        if (sted.getVindsiden_URL() != null) {
            String[] input = {sted.getVindsiden_URL()};
            new downloadOneMeasurment().execute(input);

        } else {
            tv_ms.setText("Data Utilgjengelig");


        }


        // String value = getIntent().getStringExtra(var); // Getting the value
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.spot_overview, menu);
        return true;


    }

    //OnOptionsItemSelected is called when user clicks the menu/overflow button
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        //Switch case to check which menu item user clicked
        switch (menuItem.getItemId()) {

            case R.id.windrose:
                //Define settings page to be com.vindsiden.windwidget/.Settings
                String SettingsPage = "com.vindsiden.windwidget/.WindRose";

                try {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setComponent(ComponentName.unflattenFromString(SettingsPage));
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    startActivity(intent);
                } catch (ActivityNotFoundException e)

                {
                    //This will be called when activity is not found (Check manifest)
                    Log.d("", e.getStackTrace().toString());
                }

                break;

            case R.id.About:
                About.Show(this);


                //TODO : Add featurerequest/send email to developer
                //http://stackoverflow.com/questions/2197741/how-to-send-email-from-my-android-application


        }
        return true;


    }


    class downloadOneMeasurment extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... data) {

            String s = data.toString();
            Log.d("Vindsiden2 + Convert array to string", s);


            try {
                String urlString = data[0].toString()
                        + WindWidgetConfig.getVindsidenUrlPostfix();
                Log.d("Vindsiden2", urlString);

                measurements = (new VindsidenWebXmlReader()).loadXmlFromNetwork(urlString);

                //Filldata here


            } catch (IOException e) {
                Log.d("Vindsiden2", "An IO exception occured. Stack follows: ");
                Log.d("Vindsiden2", e.getStackTrace().toString());
                // xmlRetrievalSuccessful = false;
                // not certain how robust throwing a runtime exception is, might break stuff with recurrence etc!
                // throw new RuntimeException(getResources().getString(R.string.connection_error));
            } catch (XmlPullParserException e) {
                Log.d("Vindsiden", "An XmlPullParserException occured. Stack follows: ");
                Log.d("Vindsiden", e.getStackTrace().toString());
                //xmlRetrievalSuccessful = false;
                // throw new RuntimeException(getResources().getString(R.string.xml_error));
            }
            Log.d("Vindsiden ", "Got data, now find measurment");


            return null;
        }


        protected void onPostExecute(String result) {
            boolean phonyMeasurment = false;


            try {
                mostRecentMeasurement = measurements.get(0);

                float temp = Float.valueOf(mostRecentMeasurement.getTemprature());

                //If temperature is below -20 or above +40 the measurment is probably wrong
                if (temp <= (-20)) {
                    phonyMeasurment = true;

                } else if (temp >= (40)) {
                    phonyMeasurment = true;

                }

                {
                    phonyMeasurment = false;
                }

                if (phonyMeasurment == true) {
                    Toast.makeText(getApplicationContext(), "Her har det skjedd noe feil" + "\n" + "Vennligst prøv en annen spot", Toast.LENGTH_SHORT).show();


                } else if (phonyMeasurment == false) {
                    windavg = mostRecentMeasurement.getWindAvg();
                    String String_windDirection = mostRecentMeasurement.getDirectionAvg();
                    ImageView iv_Arrow = (ImageView) findViewById(R.id.iv_overview_arrow);
                    Bitmap myImg = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);

                    Matrix matrix = new Matrix();
                    float f = Float.valueOf(String_windDirection);
                    matrix.postRotate(f);
                    Bitmap rotated = Bitmap.createBitmap(myImg, 0, 0, myImg.getWidth(), myImg.getHeight(), matrix, true);
                    iv_Arrow.setImageBitmap(rotated);

                    tv_ms.setText(windavg + "M/S");


                }


            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), "Her har det skjedd noe feil, prøv en annen spot", Toast.LENGTH_SHORT).show();

                // resetText();
                Log.d("Vindsiden4", e.toString());

            }


        }


    }

    public class WindRose extends View {

        public WindRose(Context context) {
            super(context);


        }

        @Override

        protected void onDraw(Canvas canvas) {


            super.onDraw(canvas);


            float height = (float) getHeight();
            float width = (float) getWidth();

            float radius;

            if (width > height) {
                radius = height / 2;

            } else {
                radius = width / 2;
            }

            // radius = (height )/ 2;


            Path path = new Path();
            path.addCircle(width, height, radius, Path.Direction.CCW);

            // / 2

            Resources resources = getResources();
            int color = resources.getColor(R.color.green_back);

            Paint paint = new Paint();

            paint.setColor(color);
            paint.setStrokeWidth(5);

            paint.setStyle(Paint.Style.FILL);
            float center_x, center_y;
            center_x = width / 2;
            center_y = height / 2;

            final RectF oval = new RectF();

            //Formulas :
            //SD = Start Degree
            //ED = End Degree

            //If cakepiece passes 0 (East)
            //SD, 360-(SD+ED)

            //Else :
            //SD, (ED-SD)

            oval.set(center_x - radius, center_y - radius, center_x + radius, center_y + radius);

            if (End > Start) {
                canvas.drawArc(oval, Start, (End - Start), true, paint);

            } else if (End < Start) {
                canvas.drawArc(oval, Start, ((360 - Start) + End), true, paint);
            }


        }
    }
}
