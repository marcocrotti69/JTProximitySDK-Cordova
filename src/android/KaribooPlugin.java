package com.jointag.cordova;

import android.Manifest;
import android.content.pm.PackageManager;

import com.jointag.proximity.ProximitySDK;
import com.jointag.proximity.util.Logger;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;

public class KaribooPlugin extends CordovaPlugin {
  public static final int ACCESS_FINE_LOCATION_REQ_CODE = 0;
  public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

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

  @Override
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    Logger.setTag("JointagProximitySDK.");
    Logger.setLogLevel(Logger.VERBOSE);
    Logger.d("initialize Kariboo Plugin");
    if (!cordova.hasPermission(ACCESS_FINE_LOCATION)) {
      getAccessFineLocationPermission(ACCESS_FINE_LOCATION_REQ_CODE);
    }
    String apiKey = "@KARIBOO_ID@";
    String apiSecret = "@KARIBOO_SECRET@";
    ProximitySDK.init(cordova.getActivity().getApplication(), apiKey, apiSecret);
  }


  private void getAccessFineLocationPermission(int requestCode) {
    cordova.requestPermission(this, requestCode, ACCESS_FINE_LOCATION);
  }

  @Override
  public void onRequestPermissionResult(int requestCode, String[] permissions,
                                        int[] grantResults) {
    if (requestCode == ACCESS_FINE_LOCATION_REQ_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      ProximitySDK.getInstance().checkPendingPermissions();
    }
  }
}
