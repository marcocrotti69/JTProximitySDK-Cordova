package com.jointag.cordova;

import android.app.Application;
import android.util.Log;

import com.jointag.proximity.ProximitySDK;
import com.jointag.proximity.listener.CustomActionListener;
import com.jointag.proximity.util.Logger;

public class KaribooApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    String apiKey = "@KARIBOO_ID@";
    String apiSecret = "@KARIBOO_SECRET@";
    Logger.setPrefix("KaribooApplication.");
    Logger.setLogLevel(Logger.VERBOSE);
    ProximitySDK.init(this, apiKey, apiSecret);
  }
}
