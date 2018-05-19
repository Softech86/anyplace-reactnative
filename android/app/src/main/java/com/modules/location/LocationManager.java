package com.modules.location;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

/**
 * Created by LiaoShanhe on 2017/07/18/018.
 */

public class LocationManager {
    private static final String TAG = "LocationManager";
    private final static float LOCATION_REFRESH_DISTANCE = 0;
    private final static long LOCATION_REFRESH_TIME = 0;
    private final android.location.LocationListener mLocationListener = new android.location.LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Toast.makeText(mContext, "Location changed", Toast.LENGTH_LONG).show();
            Log.v(TAG, "Location changed...");
            Log.v(TAG, "Latitude :        " + location.getLatitude());
            Log.v(TAG, "Longitude :       " + location.getLongitude());
//            mLocationOnSubscribe.onNext(location);
//            mLocationOnSubscribe.onCompleted();
//            stopListening();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.v(TAG, "changed status location : " + status);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.v(TAG, "provider enabled : " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.v(TAG, "provider disabled : " + provider);
            //stopListening();
            //mLocationOnSubscribe.onCompleted();
        }

    };

    private android.location.LocationManager mLocationManager;
    private Context mContext;

    public LocationManager(Context context) {
        mContext = context;
        mLocationManager = (android.location.LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    }

    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    public void startListening() {
        Log.v(TAG, "start request listener");

        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            String provider = android.location.LocationManager.GPS_PROVIDER;
            if (!mLocationManager.isProviderEnabled(provider)) {
                Log.v(TAG, "Provider not available = " + provider);
                sendEvent((ReactContext) mContext, "locationChanged", null);
                return;
            }
//            mLocationManager.requestSingleUpdate(provider, mLocationListener, null);
            mLocationManager.requestLocationUpdates(provider, 1000, 5, mLocationListener);
            Location lastKnownLocation = mLocationManager.getLastKnownLocation(provider);
            if (lastKnownLocation != null) {
                Log.v(TAG, lastKnownLocation.getLongitude() + ", " + lastKnownLocation.getLatitude());

                WritableMap map = new WritableNativeMap();

                map.putString("provider", lastKnownLocation.getProvider());
                map.putDouble("time", lastKnownLocation.getTime());
                map.putDouble("elapsedRealtimeNanos", lastKnownLocation.getElapsedRealtimeNanos());
                map.putDouble("latitude", lastKnownLocation.getLatitude());
                map.putDouble("longitude", lastKnownLocation.getLongitude());
                map.putBoolean("hasAltitude", lastKnownLocation.hasAltitude());
                map.putDouble("altitude", lastKnownLocation.getAltitude());
                map.putBoolean("hasSpeed", lastKnownLocation.hasSpeed());
                map.putDouble("speed", lastKnownLocation.getSpeed());
                map.putBoolean("hasBearing", lastKnownLocation.hasBearing());
                map.putDouble("bearing", lastKnownLocation.getBearing());
                map.putBoolean("hasAccuracy", lastKnownLocation.hasAccuracy());
                map.putDouble("accuracy", lastKnownLocation.getAccuracy());
                map.putBoolean("isFromMockProvider", lastKnownLocation.isFromMockProvider());

                sendEvent((ReactContext) mContext, "locationChanged", map);
            } else {
                Log.e(TAG, "lastKnownLocation is null");
                sendEvent((ReactContext) mContext, "locationChanged", null);
            }
        } else {
            Log.v(TAG, "Check permission location failed");
            sendEvent((ReactContext) mContext, "locationChanged", null);
        }
    }

    public void stopListening() {
        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }
}
