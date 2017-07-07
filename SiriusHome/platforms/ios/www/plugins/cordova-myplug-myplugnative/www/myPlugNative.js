cordova.define("cordova-myplug-myplugnative.myPlugNative", function(require, exports, module) {
var exec = require('cordova/exec');
var platform = require('cordova/platform');
// //通过oncall调用本地代码
//   var oncall = function(oncalljs){    cordova.exec(oncalljs, null, "myPlugNative", "oncalljs", []);};
// //通过startCallJS调用本地代码，模拟后台任务
//   var startCallJS = function(){    cordova.exec(null, null, "myPlugNative", "startCallJS", []);};
//   window.plugins = window.plugins ||{};
//
//   window.plugins.oncalljs=oncall;
//
//   window.plugins.startCallJS = startCallJS;
module.exports = {
    myNativess: function (type) {
        exec(null, null, "myPlugNative", type,[]);
    },
    myPlugIntoHome: function (successFn) {
        exec(successFn,null,"myPlugNative","myPlugIntoHome",[]);
    },
    myPlugIntoJPush: function (successFn) {
        exec(successFn,null,"myPlugNative","myPlugIntoJPush",[]);
    },
    leaveHomeAc: function (type) {
        exec(null,null,"myPlugNative",type,[]);
    },
    getSiriusId: function (successFn) {
        exec(successFn,null,"myPlugNative","getsiriusid",[]);
    },
    getLanguage: function (successFn) {
        exec(successFn,null,"myPlugNative","getlanguage",[]);
    },
    deleteAlarm: function (idnum) {
        exec(null,null,"myPlugNative","deleteAlarm",[idnum]);
    },
    clockregister: function (time,title) {
        exec(null,null,"myPlugNative","clockregister",[time,title]);
    },
    installApk:function (apkpath,packageName,filepath,successFn) {
        exec(successFn,null,"myPlugNative","installApk",[apkpath,packageName,filepath]);
    },
    systemVersion: function (successFn) {
        exec(successFn, null, "myPlugNative", "systemVersion", []);
    },
    uninstallApk:function (packageName) {
        exec(null,null,"myPlugNative","uninstallApk",[packageName]);
    },
    deleteallApk:function (filepath) {
        exec(null,null,"myPlugNative","deleteallApk",[filepath]);
    },
    getApkInfoList: function (successFn) {
        exec(successFn,null,"myPlugNative","apkListinfo",[]);
    },
    setAlarm: function (successFn) {
        exec(successFn,null,"myPlugNative","setalarm",[]);
    },
    externIntent:function (successFn) {
        exec(successFn,null,"myPlugNative","externIntent",[]);
    },
    externOrderIntent:function (successFn) {
        exec(successFn,null,"myPlugNative","externIntentorder",[]);
    }
};
});
