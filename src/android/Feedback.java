package com.cordova.feedback;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
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

  private CallbackContext callbackContext;

  final static int PORTA = 587;
  final static String AUTH = "true";
  final static String START_TLS = "true";
  final static String HOST = "smtp.gmail.com";

  @Override
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
  }

  @Override
  public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
    this.callbackContext = callbackContext;
    if ("send".equals(action)) {
      send(args, callbackContext);
      return true;
    }
    return false;
  }


  public void send(final JSONArray args, final CallbackContext callbackContext) {
    
    try{

      Properties props = new Properties();
      props.put("mail.smtp.auth", AUTH);
      props.put("mail.smtp.starttls.enable", START_TLS);

      String titulo  = args.getString(0);
      String mensage = args.getString(1);
      String email   = args.getString(2);
      String senha   = args.getString(3);

      Session session = Session.getInstance(props);
      Message message = new MimeMessage(session);
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
      message.setSubject(titulo);
      message.setContent(mensage, "text/html");

      Transport transport = session.getTransport("smtp");
      transport.connect(HOST, PORTA, email, senha);
      transport.sendMessage(message, message.getAllRecipients());

      Context context = this.cordova.getActivity().getApplicationContext();
      Toast toast = Toast.makeText(context, "email enviado", Toast.LENGTH_SHORT);
      toast.show();
      
      callbackContext.success("enviado");

    } catch (Exception e) {
      Context context = this.cordova.getActivity().getApplicationContext();
      Toast toast = Toast.makeText(context, "erro ao enviar, tente mais tarde", Toast.LENGTH_SHORT);
      toast.show();
      callbackContext.error("não enviado");
    }

  }
  
}
