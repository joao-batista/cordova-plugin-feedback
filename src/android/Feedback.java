package com.cordova.feedback;

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

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Feedback extends CordovaPlugin {

    public final String ACTION_SEND = "send";
    public final String ACTION_HAS_PERMISSION = "has_permission";
    private static final String INTENT_FILTER = "SMS_SENT";
    private static final int SEND_CODE = 0;
    private CallbackContext callbackContext;
    private JSONArray args;

    final static int PORTA = 587;
    final static String AUTH = "true";
    final static String START_TLS = "true";
    final static String HOST = "smtp.gmail.com";

    @Override
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
      this.callbackContext = callbackContext;
      this.args = args;
      if (action.equals(ACTION_SEND)) {
        if (hasPermission()) {
          send();
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
      return cordova.hasPermission(android.Manifest.permission.INTERNET);
    }

    private void requestPermission() {
      cordova.requestPermission(this, SEND_CODE, android.Manifest.permission.INTERNET);
    }

    public void send() {
      
      try{

        Properties props = new Properties();
        props.put("mail.smtp.auth", AUTH);
        props.put("mail.smtp.starttls.enable", START_TLS);
        String mensage = this.args.getString(0);
        String email = this.args.getString(1);
        String senha = this.args.getString(2);

        Session session = Session.getInstance(props);

        Message message = new MimeMessage(session);

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

        message.setSubject("Memsagem do Usuário");
        message.setContent(this.args, "text/plain");

        Transport transport = session.getTransport("smtp");
        transport.connect(HOST, PORTA, email, senha);
        transport.sendMessage(message, message.getAllRecipients());

      } catch (Exception e) {
        LOG.e("EmailComposer", "Error handling subject param: " + e.toString());
      }

    }
  
}
