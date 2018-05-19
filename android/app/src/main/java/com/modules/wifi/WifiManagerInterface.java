package com.modules.wifi;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableNativeMap;


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

  private Context context = null;

  /**
   * Constuctor
   * */
  public WifiManagerInterface(ReactApplicationContext reactContext) {
    super(reactContext);

    context = reactContext;
    // if (wifiManager == null) {
    //   wifiManager = ((WifiManager) reactContext.getSystemService(Context.WIFI_SERVICE));
    // }
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
      // List<ScanResult> scanResult = wifiManager.getScanResults();
      //
      // WritableArray writableArray = new WritableNativeArray();
      // for (ScanResult s : scanResult) {
      //   writableArray.pusthString(s.toString());
      // }
      List<ScanResult> scanResult = SimpleWifiManager.getInstance(context).getScanResults();

      WritableArray writableArray = new WritableNativeArray();
      for (ScanResult s : scanResult) {
        WritableMap map = new WritableNativeMap();
//        s.BSSID
//        sb.append("SSID: ").
//                append(wifiSsid == null ? WifiSsid.NONE : wifiSsid).
//
//        sb.append(", distance: ").append((distanceCm != UNSPECIFIED ? distanceCm : "?")).
//                append("(cm)");
//        sb.append(", distanceSd: ").append((distanceSdCm != UNSPECIFIED ? distanceSdCm : "?")).
//                append("(cm)");
//
//        sb.append(", passpoint: ");
//        sb.append(((flags & FLAG_PASSPOINT_NETWORK) != 0) ? "yes" : "no");
//        if (autoJoinStatus != 0) {
//          sb.append(", status: ").append(autoJoinStatus);
//        }
//        sb.append(", ChannelBandwidth: ").append(channelWidth);
//        sb.append(", centerFreq0: ").append(centerFreq0);
//        sb.append(", centerFreq1: ").append(centerFreq1);
//        sb.append(", 80211mcResponder: ");
//        sb.append(((flags & FLAG_80211mc_RESPONDER) != 0) ? "is supported" : "is not supported");

        map.putString("SSID", s.SSID);
        map.putString("BSSID", s.BSSID);
        map.putString("capabilities", s.capabilities);
        map.putInt("level", s.level);
        map.putInt("frequency", s.frequency);
        map.putString("timestamp", Long.toString(s.timestamp));
//        map.putInt("distance", s.distanceCm);
//        map.putBoolean("passpoint", ((s.flags & s.FLAG_PASSPOINT_NETWORK) != 0));
//        map.putInt("status", s.autoJoinStatus);
        map.putInt("ChannelBandwidth", s.channelWidth);



        writableArray.pushMap(map);
      }
      promise.resolve(writableArray);

    } catch (Exception e) {
      promise.reject(e.toString());
    }
  }

}
