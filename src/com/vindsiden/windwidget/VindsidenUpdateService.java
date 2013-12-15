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

    public void updateNow() {
        //upDateNow is just used to call the GetData method

        String[] input = {String.valueOf("52")};
        new GetData().execute(input);
        Log.d("Vindsiden2" + "GetData", "GetData called");

    }


    class GetData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... data) {
            List<Measurement> measurements = null;
            String s = data.toString();
            Log.d("Vindsiden2 + Convert array to string", s);

            try {
                String urlString = WindWidgetConfig.getVindsidenUrlPrefix() + data[0].toString()
                        + WindWidgetConfig.getVindsidenUrlPostfix();
                Log.d("Vindsiden2", urlString);
                measurements = (new VindsidenWebXmlReader()).loadXmlFromNetwork(urlString);
                Log.d("Vindsiden2", measurements.toString());


            } catch (IOException e) {
                Log.d("Vindsiden2", "An IO exception occured. Stack follows: ");
                Log.d("Vindsiden2", e.getStackTrace().toString());
                // xmlRetrievalSuccessful = false;
                // not certain how robust throwing a runtime exception is, might break stuff with recurrence etc!
                // throw new RuntimeException(getResources().getString(R.string.connection_error));
            } catch (XmlPullParserException e) {
                Log.d("Vindsiden2", "An XmlPullParserException occured. Stack follows: ");
                Log.d("Vindsiden2", e.getStackTrace().toString());
                //xmlRetrievalSuccessful = false;
                // throw new RuntimeException(getResources().getString(R.string.xml_error));
            }
            Measurement mostRecentMeasurement;
            mostRecentMeasurement = measurements.get(0);
            String ss = mostRecentMeasurement.getDirectionAvg();
            Log.d("Vindsiden2 " + "Stattion ID er ", ss);
            return measurements.toString();
        }

        protected void onPostExecute(String result) {

            Log.d("Vindsiden2", result.toString());


        }

    }

}
