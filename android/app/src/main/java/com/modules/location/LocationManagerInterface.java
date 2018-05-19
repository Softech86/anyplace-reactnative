package com.modules.location;

import android.content.Context;
import android.widget.Toast;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;


public class LocationManagerInterface extends ReactContextBaseJavaModule {


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
  public LocationManagerInterface(ReactApplicationContext reactContext) {
    super(reactContext);

    context = reactContext;
  }

  /**
   * Set Module name in ReactNative
   */
  @Override
  public String getName() {
    return "LocationManager";
  }

  @ReactMethod
  public void startListening() {
    Toast.makeText(context, "startPositionListening", Toast.LENGTH_LONG).show();
    LocationManager manager = new LocationManager(context);
    manager.startListening();
  }

//  @ReactMethod
//  public void getLocation(Promise promise) {
//    Object om = context.getSystemService(Context.LOCATION_SERVICE);
//    LocationManager manager = (LocationManager)om;
//    manager.
//  }

}
