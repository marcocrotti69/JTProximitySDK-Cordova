package com.jointag.cordova;

import android.app.Application;

import com.jointag.proximity.ProximitySDK;
import com.jointag.proximity.util.Logger;

public class KaribooApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        String apiKey = "@KARIBOO_ID@";
        String apiSecret = "@KARIBOO_SECRET@";
        Logger.setTag("KaribooApplication.");
        Logger.setLogLevel(Logger.VERBOSE);
        ProximitySDK.init(this, apiKey, apiSecret);
    }
}
