package com.modules.wifi;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.Promise;


import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.ArrayList;
import java.util.TimerTask;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

public class WifiManagerInterface extends ReactContextBaseJavaModule {

  private WifiManager wifiManager = null;


  private final static Long DEFAULT_INTERVAL = 2000L;
  private static final String DEFAULT_INTERVAL_KEY = "DEFAULT_INTERVAL";

  /**
   * Set static variables exported
   * */
  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put(DEFAULT_INTERVAL_KEY, DEFAULT_INTERVAL);
    return constants;
  }

  /**
   * Constuctor
   * */
  public WifiManagerInterface(ReactApplicationContext reactContext) {
    super(reactContext);
    if (wifiManager == null) {
      wifiManager = ((WifiManager) reactContext.getSystemService(Context.WIFI_SERVICE));
    }
  }

  /**
   * Set Module name in ReactNative
   * */
  @Override
  public String getName() {
    return "WifiManager";
  }

  @ReactMethod
  public void getScanResults(Promise promise) {
    try {
      List<ScanResult> scanResult = wifiManager.getScanResults();

      WritableArray writableArray = new WritableNativeArray();
      for (ScanResult s : scanResult) {
        writableArray.pusthString(s.toString());
      }

      promise.resolve(writableArray);

    } catch (Exception e) {
      promise.reject(e.toString());
    }
  }

}
