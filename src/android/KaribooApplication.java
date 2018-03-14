package com.jointag.cordova;

import android.app.Application;
import android.util.Log;

import com.jointag.proximity.listener.CustomActionListener;
import com.jointag.proximity.util.Logger;

public class KaribooApplication extends Application implements CustomActionListener {
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.setPrefix("Kariboo.");
        Logger.setLogLevel(Logger.VERBOSE);
    }

    @Override
    public void onCustomAction(String s) {
        Log.d("Kariboo", "Received custom action " + s);
    }
}
