package com.vindsiden.windwidget;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 28.11.13
 * Time: 21:47
 * To change this template use File | Settings | File Templates.
 */

import android.util.Xml;
import com.vindsiden.windwidget.model.Measurement;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


public class VindsidenXMLParser {

    // Tags for vindsiden XML
    private static final String LVL1_TAG = "Data";
    private static final String LVL2_TAG = "Measurement";
    private static final String DIRECTION_AVG_MEASUREMENT = "DirectionAvg";
    private static final String WIND_AVG_MEASUREMENT = "WindAvg";
    private static final String WIND_MAX_MEASUREMENT = "WindMax";
    private static final String WIND_MIN_MEASUREMENT = "WindMin";
    private static final String TEMPRATURE_MEASUREMENT = "Temperature1";
    private static final String MEASUREMENT_TIME = "Time";
    private static final String STATION_ID = "StationID";
    private static final String NAME_SPACE = null; // note: namespace usage is also set explicitly in the parse() method.

    public List<Measurement> parse(StringReader sr) throws XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser(); // ExpatPullParser
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false); // no namespaces

        parser.setInput(sr); // debug, worked fine: new StringReader
        // ("<Data><Measurement><com.vindsiden.windwidget.StationID>1</com.vindsiden.windwidget.StationID><Time>2013-08-14T12:35:45-07:00</Time>    <WindAvg>2.5</WindAvg>    <DirectionAvg>4</DirectionAvg></Measurement></Data>"));
        //parser.skipToTag(p, XmlPullParser.START_TAG);
        parser.nextTag();  // tror denne bug'et da vindsiden begnyte returnere <?xml version="1.0" encoding="utf-8"?> øverst.
        return readFeed(parser);
    }


    public List<Measurement> parse(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser(); // ExpatPullParser
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false); // no namespaces hardcode

        parser.setInput(inputStream, null); // NOTE: here we explicitly pass in null as the value for encoding
        System.out.println(parser.getInputEncoding()); // reports UTF-8 when vindsiden xml is prefixed <?xml version="1.0" encoding="utf-8"?>
        parser.nextTag();
        return readFeed(parser);
    }


    private List<Measurement> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Measurement> entries = new ArrayList<Measurement>();
        parser.require(XmlPullParser.START_TAG, NAME_SPACE, LVL1_TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(LVL2_TAG)) {
                entries.add(readMeasurement(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    // Parses the contents of an entry. If it encounters specific tags, hands them off
    // to their respective "CreateSuggestionList" methods for processing. Otherwise, skips the tag.
    private Measurement readMeasurement(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, NAME_SPACE, LVL2_TAG);
        String stationID = "", time = "", windAvg = "", windMax = "", windMin = "", directionAvg = "", temprature = "";

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals(STATION_ID)) {
                stationID = readStringTag(parser, STATION_ID);
            } else if (name.equals(MEASUREMENT_TIME)) {
                time = readStringTag(parser, MEASUREMENT_TIME);
            } else if (name.equals(WIND_AVG_MEASUREMENT)) {
                windAvg = readStringTag(parser, WIND_AVG_MEASUREMENT);
            } else if (name.equals(WIND_MAX_MEASUREMENT)) {
                windMax = readStringTag(parser, WIND_MAX_MEASUREMENT);
            } else if (name.equals(WIND_MIN_MEASUREMENT)) {
                windMin = readStringTag(parser, WIND_MIN_MEASUREMENT);
            } else if (name.equals(TEMPRATURE_MEASUREMENT)) {
                temprature = readStringTag(parser, TEMPRATURE_MEASUREMENT);
            } else if (name.equals(DIRECTION_AVG_MEASUREMENT)) {
                directionAvg = readStringTag(parser, DIRECTION_AVG_MEASUREMENT);
            } else {
                skip(parser);
            }
        }
        return new Measurement(stationID, time, windAvg, windMax, windMin, temprature, directionAvg);
    }


    // Processes a tag - specified by argument - in the single measurement.
    private String readStringTag(XmlPullParser parser, String tagName) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, NAME_SPACE, tagName);
        String result = readText(parser);
        parser.require(XmlPullParser.END_TAG, NAME_SPACE, tagName);
        return result;
    }

    // For a text tag, extracts its text value.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
