package com.vindsiden.windwidget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 02.12.13
 * Time: 20:08
 * To change this template use File | Settings | File Templates.
 */
public class About {



    static String VersionName(Context context){

        try{
            return context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionName;

        }   catch (PackageManager.NameNotFoundException e ){
            return "Unknown";
        }

    }


    public static void Show(Activity WidgetSettings){
        //Use a spannable to allow links highlighting
        SpannableString aboutText = new SpannableString("Version: "+  VersionName(WidgetSettings)+ "\n\n" + WidgetSettings.getString(R.string.about));

        //Generate views to pass AlertDialog.Builder and set the text

        View about;
        TextView tvAbout;

        try {
            //Inflate custom View
            LayoutInflater inflater = WidgetSettings.getLayoutInflater();
            about = inflater.inflate(R.layout.about, (ViewGroup) WidgetSettings.findViewById(R.id.Scroll));
            tvAbout = (TextView)about.findViewById(R.id.tv_about);

        }catch (InflateException e ){
            e.getStackTrace();
            about = tvAbout = new TextView(WidgetSettings);
        }

        tvAbout.setText(aboutText);
        Linkify.addLinks(tvAbout, Linkify.ALL);

        new AlertDialog.Builder(WidgetSettings)
                .setTitle("About me")
                .setCancelable(true)
                .setIcon(R.drawable.icon)
                .setPositiveButton("OK",null)
                .setView(about)
                .show();





    }
}


