package com.jointag.cordova;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.widget.Toast;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import org.apache.cordova.*;


class CheckPermission {
  public static boolean checkPermission(Activity activity){
    int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
    if (result == PackageManager.PERMISSION_GRANTED) {
      return true;
    } else {
      return false;
    }
  }
  public static void requestPermission(Activity activity, final int code){
    if (ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.ACCESS_FINE_LOCATION)) {
      Toast.makeText(activity, "Questa applicazione ha bisogno di accedere alla tua posizione per fornirti un'esperienza ottimale.", Toast.LENGTH_LONG).show();
    } else {
      ActivityCompat.requestPermissions(activity,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},code);
    }
  }
}

public class KaribooActivity extends CordovaActivity {
  public static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle extras = getIntent().getExtras();
    if (extras != null && extras.getBoolean("cdvStartInBackground", false)) {
      moveTaskToBack(true);
    }
    loadUrl(launchUrl);
    if(!CheckPermission.checkPermission(KaribooActivity.this)) {
      CheckPermission.requestPermission(KaribooActivity.this,LOCATION_PERMISSION_REQUEST_CODE);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
      ProximitySDK.getInstance().checkPendingPermissions();
    }
  }
}
