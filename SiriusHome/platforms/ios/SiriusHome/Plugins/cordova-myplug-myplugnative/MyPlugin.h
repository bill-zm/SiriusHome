//
//  MyPlugin.h
//  yuekong_sirius_home
//
//  Created by aa on 2017/7/6.
//
//

#import <Foundation/Foundation.h>
#import <Cordova/CDVPlugin.h>

@interface MyPlugin : CDVPlugin
- (void)myNativess:(CDVInvokedUrlCommand*)command;
- (void)myPlugIntoHome:(CDVInvokedUrlCommand*)command;
- (void)myPlugIntoJPush:(CDVInvokedUrlCommand*)command;
- (void)leaveHomeAc:(CDVInvokedUrlCommand*)command;
- (void)getSiriusId:(CDVInvokedUrlCommand*)command;
- (void)getLanguage:(CDVInvokedUrlCommand*)command;
- (void)deleteAlarm:(CDVInvokedUrlCommand*)command;
- (void)clockregister:(CDVInvokedUrlCommand*)command;
- (void)installApk:(CDVInvokedUrlCommand*)command;
- (void)systemVersion:(CDVInvokedUrlCommand*)command;
- (void)uninstallApk:(CDVInvokedUrlCommand*)command;
- (void)deleteallApk:(CDVInvokedUrlCommand*)command;
- (void)getApkInfoList:(CDVInvokedUrlCommand*)command;
- (void)setAlarm:(CDVInvokedUrlCommand*)command;
- (void)externIntent:(CDVInvokedUrlCommand*)command;
- (void)externOrderIntent:(CDVInvokedUrlCommand*)command;
@end
