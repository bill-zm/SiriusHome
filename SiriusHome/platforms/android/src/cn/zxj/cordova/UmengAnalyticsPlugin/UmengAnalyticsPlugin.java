package cn.zxj.cordova;

import android.content.Context;
import android.util.Log;
import com.umeng.analytics.MobclickAgent;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

public class UmengAnalyticsPlugin extends CordovaPlugin {
	private static Context mContext;
	private final  String mPageName = "index";
	private static String TAG = "UmengAnalyticsPlugin";

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		mContext = this.cordova.getActivity().getApplicationContext();
	}

	@Override
	public boolean execute(String action, JSONArray data,
			CallbackContext callbackContext) throws JSONException {
		if (action.equals("init")) {
			Log.d(TAG,"umeng init");
			init();
		}
		if (action.equals("setDebugMode")) {
			boolean mode = data.getBoolean(0);
			Log.d(TAG,"umeng init setDebugMode");
			setDebugMode(mode);
		}
		if (action.equals("onKillProcess")) {
			onKillProcess();
		}
		callbackContext.success();
		return true;
	}

	void init() {
		MobclickAgent.openActivityDurationTrack(false);
		MobclickAgent.setAutoLocation(true);
		MobclickAgent.updateOnlineConfig(mContext);
	}

	void setDebugMode(boolean mode) {
		MobclickAgent.setDebugMode(mode);
	}

    @Override
    public void onPause(boolean multitasking) {
        MobclickAgent.onPageEnd(mPageName);
        MobclickAgent.onPause(mContext);
    }

    @Override
    public void onResume(boolean multitasking) {
        MobclickAgent.onPageStart(mPageName);
        MobclickAgent.onResume(mContext);
    }

    void onKillProcess() {
    	MobclickAgent.onKillProcess(mContext);
    }
}