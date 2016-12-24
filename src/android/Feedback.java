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

import org.apache.commons.mail.SimpleEmail;


public class Feedback extends CordovaPlugin {

    private CallbackContext callbackContext;

/*    final static int PORTA = 587;
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

    public static void send(final JSONArray args, final CallbackContext callbackContext) {

        try {

            String mensage = args.getString(0);
            String user = args.getString(1);
            String senha = args.getString(2);
            
            SimpleEmail email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(465);
            email.addTo(user, "Pague Fácil");
            email.setFrom(user, "Pague Fácil");
            email.setSubject("Test message");
            email.setMsg(mensage);
            email.setSSL(true);
            email.setAuthentication(user, senha);
            email.send();
            System.out.println("Email enviado!");
            
        } catch (Exception e) {
            
        }
    }


/*    public void send(final JSONArray args, final CallbackContext callbackContext) {
      
      try{
        Context context = this.cordova.getActivity().getApplicationContext();

        Toast toast = Toast.makeText(context, "preparando", Toast.LENGTH_SHORT);
        toast.show();

        Properties props = new Properties();
        props.put("mail.smtp.auth", AUTH);
        props.put("mail.smtp.starttls.enable", START_TLS);

        toast = Toast.makeText(context, "setando", Toast.LENGTH_SHORT);
        toast.show();

        String mensage = args.getString(0);
        String email = args.getString(1);
        String senha = args.getString(2);

        String params = args.getString(0) + " " + args.getString(1) + "" + args.getString(2);

        toast = Toast.makeText(context, params, Toast.LENGTH_SHORT);
        toast.show();

        Session session = Session.getInstance(props);

        toast = Toast.makeText(context, "session", Toast.LENGTH_SHORT);
        toast.show();

        Message message = new MimeMessage(session);

        toast = Toast.makeText(context, "criando mensagem", Toast.LENGTH_SHORT);
        toast.show();

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

        toast = Toast.makeText(context, "endereço", Toast.LENGTH_SHORT);
        toast.show();

        message.setSubject("Memsagem do Usuário");

        toast = Toast.makeText(context, "assunto", Toast.LENGTH_SHORT);
        toast.show();

        message.setContent("mensage", "text/html");

        toast = Toast.makeText(context, "conteúdo", Toast.LENGTH_SHORT);
        toast.show();

        Transport transport = session.getTransport("smtp");
        transport.connect(HOST, PORTA, email, senha);

        toast = Toast.makeText(context, "transport", Toast.LENGTH_SHORT);
        toast.show();

        transport.sendMessage(message, message.getAllRecipients());

        toast = Toast.makeText(context, "email enviado", Toast.LENGTH_SHORT);
        toast.show();

      } catch (Exception e) {
        callbackContext.error(e.getMessage());
      }

    }*/
  
}
