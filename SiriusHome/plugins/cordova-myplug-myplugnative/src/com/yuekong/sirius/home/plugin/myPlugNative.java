package com.yuekong.sirius.home.plugin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;
import com.yuekong.sirius.home.activity.ClockPickerActivity;
import com.yuekong.sirius.home.MainActivity;
import com.yuekong.sirius.home.jscallback.JsCallBack;
import com.yuekong.sirius.home.model.ClockModel;
import com.yuekong.sirius.home.model.ClockSetInfo;
import com.yuekong.sirius.home.utils.ExtendUtil;
import com.yuekong.sirius.home.utils.MianCallbackInstance;
import com.yuekong.sirius.home.utils.PackageUtils;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by zhangmeng on 16/9/28.
 */
public class myPlugNative extends CordovaPlugin {
    private static final String TAG = myPlugNative.class.getSimpleName();
    public static final String NAV_SIRIUS_VIDEO = "com.yuekong.entertainment";
    public static final String NAV_SIRIUS_PACKAGE_NAME = "com.yuekong.remote";
    public static final String NAV_SIRIUS_LAUNCHER = "com.yuekong.sirius.extension";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("remote"))
            navigateToRemote();
        else if (action.equals("video1")) {
            navigateToVideo(1);
        } else if (action.equals("video2")) {
            navigateToVideo(2);
        } else if (action.equals("video3")) {
            navigateToVideo(3);
        } else if (action.equals("extension"))
            navigateToExtensionApp();
        else if (action.equals("intohome")) {
            navigateHome(MainActivity.myContext);
        } else if (action.equals("myPlugIntoHome")) {
            Log.d(TAG, "navigation to myPlugIntoHome");
            //      if (JsCallBack.getInstance().weatherCtx == null)
            JsCallBack.getInstance().weatherCtx = callbackContext;
        }
        else if (action.equals("myPlugIntoJPush")) {
            Log.d(TAG, "navigation to myPlugIntoJPush");
            //     if (JsCallBack.getInstance().jpushinitCtx == null)
            JsCallBack.getInstance().jpushinitCtx = callbackContext;
        }

        else if (action.equals("externIntent")) {
            JsCallBack.getInstance().externCtx = callbackContext;
        } else if (action.equals("externIntentorder")) {
            JsCallBack.getInstance().externOrderCtx = callbackContext;
        } else if (action.equals("getlanguage")) {
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, Locale.getDefault().getLanguage());
            pluginResult.setKeepCallback(true);
            callbackContext.sendPluginResult(pluginResult);
        } else if (action.equals("leaveHomeAc")) {
            Log.d(TAG, "leaveMainAc");
            MianCallbackInstance.getInstance(MainActivity.myContext).mainAc.leaveMainAc();
        } else if (action.equals("backHomeAc")) {
            MianCallbackInstance.getInstance(MainActivity.myContext).mainAc.backMainAc();
        } else if (action.equals("getsiriusid")) {
            Log.d("getsiriusid", "getsiriusid");
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, ExtendUtil.getSiriusSn(290, 12));
            pluginResult.setKeepCallback(true);
            callbackContext.sendPluginResult(pluginResult);
        } else if (action.equals("systemversion")) {
            Log.d("systemversion", "systemversion");
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, ExtendUtil.get(MainActivity.myContext,"ro.sw.version"));
            pluginResult.setKeepCallback(true);
            callbackContext.sendPluginResult(pluginResult);
        }
        else if (action.equals("setalarm")) {
            Intent intent = new Intent(MainActivity.myContext, ClockPickerActivity.class);
            MainActivity.myContext.startActivity(intent);
        } else if (action.equals("deleteAlarm")) {
            ClockModel selectedClock = new ClockModel();
            selectedClock.timeValue = args.getLong(0);
            selectedClock.timeId = String.valueOf(selectedClock.timeValue);
            ClockSetInfo clockSetInfo = ClockSetInfo.getInstance(MainActivity.myContext);
            clockSetInfo.delete(selectedClock);
            //取消闹钟
            ExtendUtil.deleteAlarm(MainActivity.myContext, (int) selectedClock.timeValue);
        }
        else if(action.equals("installApk")){
            instllApk(args,callbackContext);
        }
        else if(action.equals("apkListinfo")){
            Log.d(TAG, "navigation to remote is apkListinfo");
            List<PackageInfo> pacInfo =  getInstallPackageList();
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            JSONObject tmpObj = null;
            int count = pacInfo.size();
            for(int i = 0; i < count; i++)
            {
                tmpObj = new JSONObject();
                tmpObj.put("versionName" , pacInfo.get(i).versionName);
                tmpObj.put("packageName", pacInfo.get(i).packageName);
                Log.d(TAG, "apkListinfo"+pacInfo.get(i).packageName);
                Log.d(TAG, "apkListinfo"+pacInfo.get(i).versionName);
                jsonArray.put(tmpObj);
                tmpObj = null;
            }
            String personInfos = jsonArray.toString();
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, personInfos);
            pluginResult.setKeepCallback(true);
            callbackContext.sendPluginResult(pluginResult);
        }
        else if(action.equals("deleteallApk")){
            Log.d(TAG, "deleteallApkdeleteallApk");
            String s = String.valueOf(args.getString(0));
            if(s.length() > 10) {
                final String filepath = String.valueOf(args.getString(0)).substring(7);
                RecursionDeleteFile(new File(filepath));
            }
        }
        else if(action.equals("uninstallApk")){
            String name = String.valueOf(args.getString(0));
            Log.d(TAG, "unstartInstallApk"+"  "+name);
            Log.d("unstartInstallApk",PackageUtils.uninstall(MainActivity.myContext,name)+"");
        }
        return true;
    }

    public void navigateToRemote() {
        Log.d(TAG, "navigation to remote is called");
        PackageManager manager = MainActivity.myContext.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(NAV_SIRIUS_PACKAGE_NAME);
            if (i == null) {
                throw new PackageManager.NameNotFoundException();
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            MainActivity.myContext.startActivity(i);
            return;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }
    public void navigateToVideo(int type) {
        Log.d(TAG, "navigation to video is called:" + type);
        PackageManager manager = MainActivity.myContext.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(NAV_SIRIUS_VIDEO);
            if (i == null) {
                throw new PackageManager.NameNotFoundException();
            }
            i.putExtra("type", type);
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            MainActivity.myContext.startActivity(i);
            return;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

    public void navigateToExtensionApp() {
        Log.d(TAG, "navigation to extension app is called");
        PackageManager manager = MainActivity.myContext.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(NAV_SIRIUS_LAUNCHER);
            if (i == null) {
                throw new PackageManager.NameNotFoundException();
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            MainActivity.myContext.startActivity(i);
            return;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }

    public static void navigateHome(Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public void instllApk(JSONArray args, final CallbackContext callbackContext) throws JSONException {
        Log.d("startInstallApk", String.valueOf(args.getString(0)));
        Log.d("startInstallApk", String.valueOf(args.getString(1)));
        final String fileName = String.valueOf(args.getString(0)).substring(7);
        final String filePath = String.valueOf(args.getString(2)).substring(7);
        Log.d("startInstallApk", fileName);
        Log.d("startInstallApk", filePath);
        try {
            String command = "chmod 777 " + fileName;
            Log.i("startInstallApk", "command = " + command);
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec(command);

            String command1 = "chmod 777 " + filePath;
            Log.i("startInstallApk", "command = " + command1);
            Runtime runtime1 = Runtime.getRuntime();
            Process proc1 = runtime1.exec(command1);

        } catch (IOException e) {
            Log.i("zyl","chmod fail!!!!");
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                CallbackContext callbackContext1 = callbackContext;
                Log.d("startInstallApk","startInstallApk");
//                    Toast.makeText(MainActivity.myContext,PackageUtils.install(MainActivity.myContext,fileName)+"",Toast.LENGTH_SHORT).show();
                int type = PackageUtils.install(MainActivity.myContext,fileName);
                Log.d("startInstallApk","startInstallApktype:"+type);
                PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, type+"");
                pluginResult.setKeepCallback(true);
                callbackContext1.sendPluginResult(pluginResult);
//                int tag = PackageUtils.installSilent(myContext,"file:///data/user/0/com.yuekong.sirius.home/files/apkpath/inc.apk");
            }
        }).start();
    }
    public List<PackageInfo>  getInstallPackageList(){
        PackageManager pm =  MainActivity.myContext.getPackageManager();
        List<ApplicationInfo> listAppcations = pm
                .getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
//        List<PackageInfo> listApck = pm.getInstalledPackages(PackageManager.GET_ACTIVITIES);
        List<PackageInfo> pacList = new ArrayList<PackageInfo>();
        for(ApplicationInfo info : listAppcations){

            PackageInfo pkin = null;
            try {
                pkin =  pm.getPackageInfo(info.packageName,0);
                pacList.add(pkin);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            Log.d("PackageManagerversionName: ",info.packageName +"  "+pkin.versionName);
//            //非系统程序
//            if ((info.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
//                PackageInfo pkin = null;
//                try {
//                    pkin =  pm.getPackageInfo(info.packageName,0);
//                    pacList.add(pkin);
//                } catch (PackageManager.NameNotFoundException e) {
//                    e.printStackTrace();
//                }
//                Log.d("PackageManagerversionName: ",info.packageName +"  "+pkin.versionName);
//            }
//            //本来是系统程序，被用户手动更新后，该系统程序也成为第三方应用程序了
//            else if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0){
//                PackageInfo pkin = null;
//                try {
//                    pkin =  pm.getPackageInfo(info.packageName,0);
//                    pacList.add(pkin);
//                } catch (PackageManager.NameNotFoundException e) {
//                    e.printStackTrace();
//                }
//                Log.d("PackageManagerversionName: ",info.packageName +"  "+pkin.versionName);
//            }
        }
        return pacList;
    }
    public void RecursionDeleteFile(File file){
        if(file.isFile()){
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile == null || childFile.length == 0){
                file.delete();
                return;
            }
            for(File f : childFile){
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }
}
