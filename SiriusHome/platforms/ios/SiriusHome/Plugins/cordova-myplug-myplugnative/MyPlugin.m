//
//  MyPlugin.m
//  yuekong_sirius_home
//
//  Created by aa on 2017/7/6.
//
//

#import "MyPlugin.h"

@implementation MyPlugin

- (void)myNativess:(CDVInvokedUrlCommand*)command
{

}
- (void)myPlugIntoHome:(CDVInvokedUrlCommand*)command
{
    
}
- (void)myPlugIntoJPush:(CDVInvokedUrlCommand*)command
{
    
}
- (void)leaveHomeAc:(CDVInvokedUrlCommand*)command
{
    
}
- (void)getSiriusId:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult *res = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"S21500000126"];
    [self.commandDelegate sendPluginResult:res callbackId:command.callbackId];
}
- (void)getLanguage:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult *res = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"en"];
    [self.commandDelegate sendPluginResult:res callbackId:command.callbackId];
}
- (void)deleteAlarm:(CDVInvokedUrlCommand*)command
{
    
}
- (void)clockregister:(CDVInvokedUrlCommand*)command
{
    
}
- (void)installApk:(CDVInvokedUrlCommand*)command
{
    
}
- (void)systemVersion:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult *res = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"UCON_SIRIUS_V0.7.5"];
    [self.commandDelegate sendPluginResult:res callbackId:command.callbackId];
}
- (void)uninstallApk:(CDVInvokedUrlCommand*)command
{
    
}
- (void)deleteallApk:(CDVInvokedUrlCommand*)command
{
    
}
- (void)getApkInfoList:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult *res = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"[]"];
    [self.commandDelegate sendPluginResult:res callbackId:command.callbackId];

}
- (void)setAlarm:(CDVInvokedUrlCommand*)command
{
    
}
- (void)externIntent:(CDVInvokedUrlCommand*)command
{
    
}
- (void)externOrderIntent:(CDVInvokedUrlCommand*)command
{
    
}

@end
