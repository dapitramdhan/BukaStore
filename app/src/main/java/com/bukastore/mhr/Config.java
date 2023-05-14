package com.bukastore.mhr;

import android.app.Application;
import android.util.Log;
import com.bukastore.mhr.utils.NetworkMonitoringUtil;

public class Config extends Application {

	public static final String TAG = Config.class.getSimpleName();
	public NetworkMonitoringUtil mNetworkMonitoringUtil;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate() called");

        mNetworkMonitoringUtil = new NetworkMonitoringUtil(getApplicationContext());
        mNetworkMonitoringUtil.checkNetworkState();
        mNetworkMonitoringUtil.registerNetworkCallbackEvents();
	}
}