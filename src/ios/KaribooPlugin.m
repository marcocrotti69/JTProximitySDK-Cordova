/********* cordova-plugin-jointag.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>

@import JTProximitySDK;
@import UserNotifications;

@interface KaribooPlugin : CDVPlugin <UNUserNotificationCenterDelegate,JTProximityCustomDelegate> {
    // Member variables go here.
}

@property NSDictionary *launchOptions;

- (void)init:(CDVInvokedUrlCommand*)command;
- (void)getInstallationId:(CDVInvokedUrlCommand*)command;

@end

@implementation KaribooPlugin

NSDictionary *launchOptions;

- (void)pluginInitialize {
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(finishLaunching:) name:UIApplicationDidFinishLaunchingNotification object:nil];
}

- (void)finishLaunching:(NSNotification *)notification {
    self.launchOptions = notification.userInfo;
    NSString* apiKey = [[NSBundle mainBundle] objectForInfoDictionaryKey:@"KaribooID"];
    NSString* apiSecret = [[NSBundle mainBundle] objectForInfoDictionaryKey:@"KaribooSecret"];
    if (apiKey != nil && [apiKey length] > 0 && apiSecret != nil && [apiSecret length] > 0) {
        [[JTProximitySDK sharedInstance] setLogLevel:JTPLogLevelVerbose];
        [[JTProximitySDK sharedInstance] initWithLaunchOptions:self.launchOptions apiKey:apiKey apiSecret:apiSecret];
        if (@available(iOS 10.0, *)) {
            [UNUserNotificationCenter currentNotificationCenter].delegate = self;
        }
        NSLog(@"Kariboo SDK initialized");
    }
}

- (void)getInstallationId:(CDVInvokedUrlCommand*)command {
    CDVPluginResult* pluginResult = nil;
    NSString *installationId = nil;
    installationId = [JTProximitySDK sharedInstance].installationId;
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:installationId];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)application:(UIApplication *)application didReceiveLocalNotification:(UILocalNotification *)notification {
    if ([[JTProximitySDK sharedInstance] application:application didReceiveLocalNotification:notification]) {
        return;
    }
}

- (void)userNotificationCenter:(UNUserNotificationCenter *)center willPresentNotification:(UNNotification *)notification withCompletionHandler:(void (^)(UNNotificationPresentationOptions))completionHandler __IOS_AVAILABLE(10.0) {
    if ([[JTProximitySDK sharedInstance] userNotificationCenter:center willPresentNotification:notification]) {
        completionHandler(UNNotificationPresentationOptionAlert|UNNotificationPresentationOptionBadge|UNNotificationPresentationOptionSound);
        return;
    }
    completionHandler(UNNotificationPresentationOptionAlert);
}

- (void)userNotificationCenter:(UNUserNotificationCenter *)center didReceiveNotificationResponse:(UNNotificationResponse *)response withCompletionHandler:(void (^)(void))completionHandler __IOS_AVAILABLE(10.0) {
    if ([[JTProximitySDK sharedInstance] userNotificationCenter:center didReceiveNotificationResponse:response]) {
        
    }
    completionHandler();
}

- (void)jtProximityDidReceiveCustomAction:(NSString *)customAction {
    NSLog(@"Received custom action %@", customAction);
}

@end
