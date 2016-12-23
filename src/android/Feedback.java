package com.cordova.plugins.feedback;

import android.content.Context;
import android.content.Intent;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Feedback extends CordovaPlugin {

    public final String ACTION_SEND = "send";
    public final String ACTION_HAS_PERMISSION = "has_permission";
    private static final String INTENT_FILTER = "SMS_SENT";
    private static final int SEND_SMS_REQ_CODE = 0;
    private CallbackContext callbackContext;
    private JSONArray args;

    @Override
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
      this.callbackContext = callbackContext;
      this.args = args;
      if (action.equals(ACTION_SEND)) {
        if (hasPermission()) {
          sendSMS();
        } else {
          requestPermission();
        }
        return true;
      }
      else if (action.equals(ACTION_HAS_PERMISSION)) {
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, hasPermission()));
        return true;
      }
      return false;
    }

    private boolean hasPermission() {
      return cordova.hasPermission(android.Manifest.permission.SEND_SMS);
    }

    private void requestPermission() {
      cordova.requestPermission(this, SEND_SMS_REQ_CODE, android.Manifest.permission.SEND_SMS);
    }
  
}
