package com.vindsiden.windwidget;

import android.util.Log;
import com.vindsiden.windwidget.config.WindWidgetConfig;
import com.vindsiden.windwidget.model.Measurement;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 30.11.13
 * Time: 11:47
 * To change this template use File | Settings | File Templates.
 */
public class ManuellUpdate {

    public void upDate() {
        List<Measurement> measurements = null;

        try {
            String urlString = WindWidgetConfig.getVindsidenUrlPrefix() + /* stationID*/2
                    + WindWidgetConfig.getVindsidenUrlPostfix();
            Log.d("Vindsiden", urlString);
            Log.d("Vindsiden", "Update fra Main Class");
            measurements = (new VindsidenWebXmlReader()).loadXmlFromNetwork(urlString);
            Measurement m = null;
            String s = m.getWindAvg();

            Log.d("Vindsiden manuell hent", s);


        } catch (IOException e) {
            Log.d("Vindsiden", "An IO exception occured. Stack follows: ");
            Log.d("Vindsiden", e.getStackTrace().toString());
            //xmlRetrievalSuccessful = false;
            // not certain how robust throwing a runtime exception is, might break stuff with recurrence etc!
            // throw new RuntimeException(getResources().getString(R.string.connection_error));
        } catch (XmlPullParserException e) {
            Log.d("Vindsiden", "An XmlPullParserException occured. Stack follows: ");
            Log.d("Vindsiden", e.getStackTrace().toString());
            //xmlRetrievalSuccessful = false;
            // throw new RuntimeException(getResources().getString(R.string.xml_error));
        }

    }

}
