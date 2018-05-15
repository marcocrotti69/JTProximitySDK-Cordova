# Jointag Proximity Cordova plugin

## Table of Contents

1. [Installation and usage](#installation-and-usage)
2. [Requirements and compatibility](#requirements-and-compatibility)
3. [iOS specific settings](#ios-specific-settings)
4. [Android specific settings](#android-specific-settings)

## Installation and usage

### Installation

Make sure you have a Jointag Proximity / Kariboo client id (API key) and secret.

Run the following command to add the plugin to your cordova project:
```bash
$ cordova plugin add cordova-plugin-kariboo --save --variable KARIBOO_ID="598322107a5b646fd1785fd9" --variable KARIBOO_SECRET="qxUe5vECy5DPeXmeFhPHOerVYdVDg34/StHkV3IPNdA927v4"
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

Minimum cordova-ios version: `4.0`

## iOS specific settings

### Location usage description
You have to put in the `Info.plist` of your project the following settings:

```xml
<key>NSLocationAlwaysUsageDescription</key>
<string>Location usage description</string>
<key>NSLocationAlwaysAndWhenInUseUsageDescription</key>
<string>Location usage description</string>
<key>NSLocationWhenInUseUsageDescription</key>
<string>Location usage description</string>
```

### Debug Mode

During the development process it's possible to initialize the SDK in debug mode. This way all the data will be sent to a sandbox server, preventing to put test data in production databases.

Before enabling the debug mode, you must add the the following setting to your application `Info.plist` file:

```xml
<key>NSAppTransportSecurity</key>
<dict>
  <key>NSAllowsArbitraryLoads</key>
  <true/>
</dict>
```

To initialize the SDK in debug mode please modify the file karibooPlugin.m as follows:

##### Objective-C

```objc
- (void)pluginInitialize
{
    self.debugEnabled = YES;
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(finishLaunching:) name:UIApplicationDidFinishLaunchingNotification object:nil];
}
```

### Handling Notifications

To enable the SDK to correctly send and manager advertising notifications, you must implement the following method in your `UIApplicationDelegate`:

##### Objective-C

```objc
- (void)application:(UIApplication *)application didReceiveLocalNotification:(UILocalNotification *)notification {
    if ([[JTProximitySDK sharedInstance] application:application didReceiveLocalNotification:notification]) {
        return;
    }
    // Other application logics
}
```

##### Swift

```swift
func application(_ application: UIApplication, didReceive notification: UILocalNotification) {
    if (ProximitySDK.instance().application(application, didReceive: notification)) {
        return
    }
    // Other application logics
}
```

If you plan to support **iOS 10.0** or later, you must also add this code in your `UNUserNotificationCenterDelegate` methods:

##### Objective-C

```objc
- (void)userNotificationCenter:(UNUserNotificationCenter *)center willPresentNotification:(UNNotification *)notification withCompletionHandler:(void (^)(UNNotificationPresentationOptions))completionHandler __IOS_AVAILABLE(10.0) {
    if ([[JTProximitySDK sharedInstance] userNotificationCenter:center willPresentNotification:notification]) {
        completionHandler(UNNotificationPresentationOptionAlert|UNNotificationPresentationOptionBadge|UNNotificationPresentationOptionSound);
        return;
    }
    // Other application logics
}

- (void)userNotificationCenter:(UNUserNotificationCenter *)center didReceiveNotificationResponse:(UNNotificationResponse *)response withCompletionHandler:(void (^)(void))completionHandler __IOS_AVAILABLE(10.0) {
    if ([[JTProximitySDK sharedInstance] userNotificationCenter:center didReceiveNotificationResponse:response]) {
        completionHandler();
        return;
    }
    // Other application logics
}
```

##### Swift

```swift
@available(iOS 10.0, *)
func userNotificationCenter(_ center: UNUserNotificationCenter, willPresent notification: UNNotification, withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
    if ProximitySDK.instance().userNotificationCenter(center, willPresent: notification) {
        completionHandler([.alert, .badge, .sound])
        return
    }
    // Other application logics
}

@available(iOS 10.0, *)
func userNotificationCenter(_ center: UNUserNotificationCenter, didReceive response: UNNotificationResponse, withCompletionHandler completionHandler: @escaping () -> Void) {
    if ProximitySDK.instance().userNotificationCenter(center, didReceive: response) {
        completionHandler()
        return
    }
    // Other application logics
}
```

### Disable automatic permission requests

You can disable the SDK automatic location and notification permission requests during initialization by setting to NO the following properties on `JTProximitySDK.sharedInstance`:

- `promptForPushNotifications` : set to `NO` to disable the automatic request for user notifications permission
- `promptForLocationAuthorization` : set to `NO` to disable the automatic request for user location permission

Note: the properties must be set before calling any `initWithLaunchOptions:apiKey:apiSecret:` method.

### Receive custom events

You can receive custom advertising events (if configured in the backend) to integrate application-specific features by using the `customDelegate` property of `ProximitySDK` instance.

When the application user interacts with a custom-action notification, the `jtProximityDidReceiveCustomAction:` method is invoked by passing a `customAction` NSString object.

## Android specific settings

### Debug Mode

During the development process it's possible to initialize the SDK in debug mode. This way all the data will be sent to a sandbox server, preventing to put test data in production databases.
To initialize the SDK in debug mode please modify the file KaribooApplication.java as follows:

```java
ProximitySDK.setDebug(true);
```

### Customizing the notifications

It is possibile to to customize the look (icon and title) of the advertising notifications and the monitoring notification.

In order to customize the icon, include in your project a drawable named `ic_stat_jointag_default`.

We recommend using [Android Asset Studio](https://romannurik.github.io/AndroidAssetStudio/icons-notification.html) to quickly and easily generate small icons with the correct settings.

If you prefer to create your own icons, make sure to generate the icon for the following densities:

- mdpi
- hdpi
- xhdpi
- xxhdpi
- xxxhdpi

In order to customize the title for all notifications, include in your project a string resource named `jointag_notification_title`.

To customize the message of the monitoring notification, include in your project a string resource named `jointag_notification_message`.

---

> **Note**: with some versions of the android build tool a duplicate resource error may arise during the resource merging phase of the build. In this case it is sufficient to include the new drawable resources using a version qualifier. Eg:
>
> `drawable-hdpi-v7/ic_stat_jointag_default.png`, `drawable-mdpi-v7/ic_stat_jointag_default.png`, `drawable-xhdpi-v7/ic_stat_jointag_default.png`, etc…

### Receive custom events

You can receive custom advertising events (if configured in the backend) to integrate application-specific features by registering a `CustomActionListener` object using the `addCustomActionListener` method of `ProximitySDK`.

When the application user interacts with a custom-action notification, the `onCustomAction` method is invoked by passing a `payload` string object.

Since the `CustomActionListener` object is retained by `ProximitySDK`, remember to remove the listener when the owning instance is being deallocated to avoid unwanted retaining or NullPointerException. It is therefore good practice to use a long-life object as CustomActionListener, such as the Application object.
