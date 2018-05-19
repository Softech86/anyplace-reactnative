package com.modules.signal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

/**
 * Created by LiaoShanhe on 2017/07/18/018.
 */

public class SignalManager extends PhoneStateListener {
    private static final String TAG = "SignalObserver";

    private Context mContext;
    private TelephonyManager mTelephonyManager;

    public SignalManager(Context context) {
        mContext = context;
        mTelephonyManager = (TelephonyManager) mContext.getSystemService(mContext.TELEPHONY_SERVICE);
//        mTelephonyManager.listen(this, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }

    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    private void signalMeasuring(SignalStrength signalStrength) {
        String ssignal = signalStrength.toString();
        Log.e(TAG, ssignal);
        String[] parts = ssignal.split(" ");

        Log.v(TAG, ssignal);
        WritableMap map = new WritableNativeMap();

        int dB = -120;
        if (mTelephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE) {
            // For Lte SignalStrength: dbm = ASU - 140.
            dB = Integer.parseInt(parts[8]) - 140;
            /*int ltesignal = Integer.parseInt(parts[9]);
            if (ltesignal < -2) {
                dB = ltesignal;
            }*/
        } else {
            if (signalStrength.getGsmSignalStrength() != 99) {
                // For GSM Signal Strength: dbm =  (2*ASU)-113.
                int strengthInteger = -113 + 2 * signalStrength.getGsmSignalStrength();
                dB = strengthInteger;
                Log.e(TAG, "getEvdoDbm: " + signalStrength.getEvdoDbm());
                Log.e(TAG, "getCdmaDbm: " + signalStrength.getCdmaDbm());
                map.putInt("evdoDbm", signalStrength.getEvdoDbm());
                map.putInt("cdmaDbm", signalStrength.getCdmaDbm());
            }
        }
        Log.e(TAG, "dB: " + dB);
        map.putInt("dB", dB);


        sendEvent((ReactContext) mContext, "signalStrengthsChanged", map);
    }

    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
        Log.e(TAG, "信号强度变化");
        signalMeasuring(signalStrength);
    }

    public void startListening() {
        mTelephonyManager.listen(this, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }

    public void stopListening() {
        mTelephonyManager.listen(this, PhoneStateListener.LISTEN_NONE);
    }

}
