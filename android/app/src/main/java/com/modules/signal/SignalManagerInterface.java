package com.modules.signal;

import android.content.Context;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;


public class SignalManagerInterface extends ReactContextBaseJavaModule {


  /**
   * Set static variables exported
   * */
  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    // constants.put(DEFAULT_INTERVAL_KEY, DEFAULT_INTERVAL);
    return constants;
  }

  private Context context = null;

  /**
   * Constuctor
   * */
  public SignalManagerInterface(ReactApplicationContext reactContext) {
    super(reactContext);

    context = reactContext;
  }

  /**
   * Set Module name in ReactNative
   * */
  @Override
  public String getName() {
    return "SignalManager";
  }
  
  @ReactMethod
  public void startListening() {
      SignalManager manager = new SignalManager(context);
      manager.startListening();
  }

}
