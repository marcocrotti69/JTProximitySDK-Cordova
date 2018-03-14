package com.jointag.cordova;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import com.jointag.cordova.KaribooApplication;
import com.jointag.proximity.ProximitySDK;
import com.jointag.proximity.util.Logger;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

public class KaribooPlugin extends CordovaPlugin  {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("init")) {
            String apiKey = args.getString(0);
            String apiSecret = args.getString(1);
            this.init(apiKey, apiSecret, callbackContext);
            return true;
        }
        else if (action.equals("setDebug")) {
            Boolean debug = args.getBoolean(0);
            this.setDebug(debug, callbackContext);
            return true;
        }
        else if (action.equals("getInstallationId")) {
            this.getInstallationId(callbackContext);
            return true;
        }
        return false;
    }

    private void init(String apiKey, String apiSecret, CallbackContext callbackContext) {
        KaribooApplication application = ((KaribooApplication) this.cordova.getActivity().getApplicationContext());
        ProximitySDK.init(application, apiKey, apiSecret);
        ProximitySDK.getInstance().addCustomActionListener(application);
        callbackContext.success("Kariboo SDK initialized");
    }

    private void setDebug(Boolean debug, CallbackContext callbackContext) {
        ProximitySDK.setDebug(debug);
        callbackContext.success(debug ? "1" : "0");
    }

    private void getInstallationId(CallbackContext callbackContext) {
        String installationId = null;
        installationId = ProximitySDK.getInstance().getInstallationId();
        callbackContext.success(installationId);
    }
}
