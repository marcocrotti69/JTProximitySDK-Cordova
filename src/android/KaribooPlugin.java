package com.jointag.cordova;

import com.jointag.proximity.ProximitySDK;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import org.json.JSONArray;


public class KaribooPlugin extends CordovaPlugin  {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
       if (action.equals("getInstallationId")) {
            this.getInstallationId(callbackContext);
            return true;
        }
        return false;
    }

    private void getInstallationId(CallbackContext callbackContext) {
        String installationId = null;
        installationId = ProximitySDK.getInstance().getInstallationId();
        callbackContext.success(installationId);
    }
}