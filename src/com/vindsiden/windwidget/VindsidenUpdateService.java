package com.vindsiden.windwidget;

import android.os.AsyncTask;
import android.util.Log;
import com.vindsiden.windwidget.config.WindWidgetConfig;
import com.vindsiden.windwidget.model.Measurement;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Daniel on 15.12.13.
 */
public class VindsidenUpdateService {
    //This class should Update Mesurments for all stations every 10mins
    //Todo interval should be user set and not hardcoded

    private static final String NEXT_SCHEDULE_URI_POSTFIX = "/next_schedule";
    private static final String WIDGET_URI_PREFIX = "/widget_id/";
    List<Measurement> measurements = null;

    public void updateNow(String stationID, String from) {
        //upDateNow is just used to call the GetData method

        //stationID = "1";
        try {

            if (stationID.equals("")) {
                //Actually used for debugging
                stationID = "1";
       
            }

          //Start the GetData
            String[] input = {String.valueOf(stationID), from};
            new GetData().execute(input);


        } catch (Exception e) {
            Log.d("Vindsiden2" + " UpdateNow", "Failed");


        }


    }


    class GetData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... data) {

            String s = data.toString();
            Log.d("Vindsiden2 + Convert array to string", s);

            try {
                String urlString = WindWidgetConfig.getVindsidenUrlPrefix() + data[0].toString()
                        + WindWidgetConfig.getVindsidenUrlPostfix();
                Log.d("Vindsiden2", urlString);
                measurements = (new VindsidenWebXmlReader()).loadXmlFromNetwork(urlString);
                Log.d("Vindsiden2", measurements.toString());
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

            String Return = "";
            int array = data.length;

            if (array == 1) {
                //stop
                Return = "";


            } else if (array == 2) {
                Return = data[1].toString();
            }


            return Return;
        }


        protected void onPostExecute(String result) {

            //   fillData(measurements);
            MyActivity myActivity = new MyActivity();
            myActivity.measurement2 = measurements.get(0);
            //Commect

        }


    }


}
