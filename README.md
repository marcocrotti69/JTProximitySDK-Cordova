# cordova-plugin-kariboo

## Installation

Make sure you have a Kariboo client id (API key) and secret:

```bash
$ cordova plugin add cordova-plugin-kariboo --save --variable KARIBOO_ID="598322107a5b646fd1785fd9" --variable KARIBOO_SECRET="qxUe5vECy5DPeXmeFhPHOerVYdVDg34/StHkV3IPNdA927v4"
```

If you need to change your `KARIBOO_ID` or `KARIBOO_SECRET` after installation, it's recommended that you remove and then re-add the plugin as above. Note that changes to the `KARIBOO_ID` or `KARIBOO_SECRET` value in your `config.xml` file will *not* be propagated to the individual platform builds.

## Compatibility

 * cordova cli >= 7.0.1
 * cordova-android >= 7.0.0
 * cordova-ios >= 4.0
