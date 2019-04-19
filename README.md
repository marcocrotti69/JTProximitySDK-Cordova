# Jointag Proximity Cordova plugin

## Table of Contents

1. [Installation and usage](#installation-and-usage)
2. [Requirements and compatibility](#requirements-and-compatibility)

## Installation and usage

### Installation

Make sure you have a Jointag Proximity / Kariboo client id (API key) and secret.

Run the following command to add the plugin to your cordova project:
```bash
$ cordova plugin add @jointag/cordova-plugin-jointag-proximity --save --variable KARIBOO_ID="YOUR_API_KEY" --variable KARIBOO_SECRET="YOUR_API_SECRET"
```

If you need to change your `KARIBOO_ID` or `KARIBOO_SECRET` after installation of the plugin, it's recommended that you remove and then re-add the plugin as above.
Note that changes to the `KARIBOO_ID` or `KARIBOO_SECRET` value in your `config.xml` file will *not* be propagated to the individual platform builds.

### Tracking users

The SDK associates each tracked request with the *advertisingId*. If the *advertisingId* is not available due to a user permission denial, the device can be identified by the *installationId*. The *installationId* identifies in particular a specific installation of the SDK in a certain app on a certain device. If the app containing the SDK is uninstalled and then installed again the *installationId* will be a different one. You can retrieve the *installationId* after the initialization of the SDK anywhere in your code with the following line:

```javascript
Kariboo.getInstallationId(function (value) {
  alert("installationId: " + value);
});
```

## Requirements and compatibility

### Cordova requirements

Minimum cordova CLI version: `7.0.1`

### Android requrements

Minimum cordova-android version: `7.0.0`
Minimum API level: `14` (Android 4.0)
> **Note**: to use functionalities that rely on BLE, the minimum API level is `18` (Android 4.3). If the device API level is between `14` and `17` the SDK won't be able to access BLE and therefore it will be not possible to obtain data from BLE devices.

### iOS requirements

Minimum cordova-ios version: `4.3.0`