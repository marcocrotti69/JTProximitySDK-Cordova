package com.jointag.cordova;

import android.app.Application;
import android.util.Log;

import com.jointag.proximity.ProximitySDK;
import com.jointag.proximity.listener.CustomActionListener;
import com.jointag.proximity.util.Logger;

public class KaribooApplication extends Application implements CustomActionListener {

    @Override
    public void onCreate() {
        super.onCreate();
        String apiKey = "@KARIBOO_ID@";
        String apiSecret = "@KARIBOO_SECRET@";
        Logger.setPrefix("KaribooApplication.");
        Logger.setLogLevel(Logger.VERBOSE);
        ProximitySDK.setDebug(true);
        ProximitySDK.init(this, apiKey, apiSecret);
        ProximitySDK.getInstance().addCustomActionListener(this);
    }

    @Override
    public void onCustomAction(String s) {
        Log.d("Kariboo", "Received custom action " + s);
    }
}
