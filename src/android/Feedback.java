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
import org.apache.commons.mail.SimpleEmail;


public class Feedback extends CordovaPlugin {

    private CallbackContext callbackContext;
/*    private SimpleEmail email;

    final static int PORTA = 587;
    final static String AUTH = "true";
    final static String START_TLS = "true";
    final static String HOST = "smtp.gmail.com";*/

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

    public void send(final JSONArray args, final CallbackContext callbackContext) throws EmailException {
    
       JSONObject props = args.getJSONObject(0);
       SimpleEmail email = setEmailProperties(props);
       /*email.setHostName("smtp.gmail.com");
       email.setSmtpPort(465);
       email.addTo("xxx@xxx.com", "Jose");
       email.setFrom("seuemail@seuprovedor.com", "Seu nome");
       email.setSubject("Test message");
       email.setMsg("This is a simple test of commons-email");
       email.setSSL(true);
       email.setAuthentication("username", "senha");*/
       email.send();
    }

    public SimpleEmail setEmailProperties (JSONObject params) throws JSONException {

        SimpleEmail email = new SimpleEmail();

        if (params.has("host"))
            email.setHostName(params.getString("host"));
        if (params.has("port"))
            email.setSmtpPort(params.getString("body"));
        if (params.has("to"))
            email.addTo(params.getString("to"), "Seu nome");
        if (params.has("from"))
            email.setFrom(params.getString("from"), "Seu nome");
        if (params.has("subject"))
            email.setSubject(params.getString("subject"));
        if (params.has("msg"))
            email.setMsg(params.getString("msg"));
        if(params.has("username") && params.has("password"))
            email.setAuthentication(params.getString("username"), params.getString("password"));

        return email;
    }


    /*public void send(final JSONArray args, final CallbackContext callbackContext) {
      
      try{
        Context context = this.cordova.getActivity().getApplicationContext();

        String mensage = args.getString(0);
        String email = args.getString(1);
        String senha = args.getString(2);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props);
        Message message = new MimeMessage(session);
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("Memsagem do Usuário");
        message.setContent(mensage, "text/html");

        Transport transport = session.getTransport("smtp");
        transport.connect(HOST, PORTA, email, senha);
        transport.sendMessage(message, message.getAllRecipients());

        Toast toast = Toast.makeText(context, "email enviado", Toast.LENGTH_SHORT);
        toast.show();

      } catch (Exception e) {
        callbackContext.error(e.getMessage());
      }

    }*/
  
}
