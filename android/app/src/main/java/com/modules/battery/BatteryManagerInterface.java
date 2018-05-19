package com.modules.battery;

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
import java.lang.String;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.widget.Toast;

public class BatteryManagerInterface extends ReactContextBaseJavaModule {


    /**
     * Set static variables exported
     */
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        // constants.put(DEFAULT_INTERVAL_KEY, DEFAULT_INTERVAL);
        return constants;
    }

    private Context context = null;

    /**
     * Constuctor
     */
    public BatteryManagerInterface(ReactApplicationContext reactContext) {
        super(reactContext);

        context = reactContext;
    }

    /**
     * Set Module name in ReactNative
     */
    @Override
    public String getName() {
        return "BatteryManager";
    }

    public double getBatteryCapacity() {

        Object mPowerProfile_ = null;

        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";


        try {
            mPowerProfile_ = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class).newInstance(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            double batteryCapacity = (Double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getAveragePower", java.lang.String.class)
                    .invoke(mPowerProfile_, "battery.capacity");
            return batteryCapacity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Double.MAX_VALUE;
    }

    private BroadcastReceiver receiver;

    @ReactMethod
    public void getBatteryStatus(final Promise promise) {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent intent) {
                int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
                int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
                int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
                String technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
                int iconSmall = intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL, -1);
                int level = -1;
                if (rawlevel >= 0 && scale > 0) {
                    level = (rawlevel * 100) / scale;
                }
                double batteryCapacity = getBatteryCapacity();
                context.unregisterReceiver(receiver);

                /**
                 scale：最大电池电量值，通常100
                 level：当前电量值，从0到scale
                 status；当前充电状态
                 health：电池状态
                 present：bool值，如果有电池则值为true
                 icon-small:整型，该状态建议使用的icon。
                 plugged：0,设备未插入，1：AC适配器插入， 2， USB插入
                 voltage：当前电池电压mv
                 temperature：当前电池温度。
                 technology：电池类型，如：Li-ion
                 */

                WritableMap map = new WritableNativeMap();
                map.putInt("level", level);
                map.putInt("scale", scale);
                map.putBoolean("present", present);
                map.putString("status", new String[]{"UNKNOWN", "CHARGING", "DISCHARGING", "NOT_CHARGING", "FULL"}[status - 1]);
                map.putString("health", new String[]{"UNKNOWN", "GOOD", "OVERHEAT", "DEAD", "OVER_VOLTAGE", "UNSPECIFIED_FAILURE", "COLD"}[health - 1]);
                map.putString("plugged", new String[] {"UNPLUGGED", "AC", "USB", "UNKNOWN", "WIRELESS"}[plugged]);
                map.putInt("temperature", temperature);
                map.putInt("voltage", voltage);
                map.putString("technology", technology);
                map.putInt("iconSmall", iconSmall);
                map.putDouble("capacity", batteryCapacity);
                promise.resolve(map);
            }
        };
        context.registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

}
