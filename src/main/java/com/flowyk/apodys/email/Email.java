package com.flowyk.apodys.email;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.prefs.Preferences;

public class Email {

    public void send() throws MessagingException {
        Preferences preferences = Preferences.userNodeForPackage(Email.class);
        Session session = Session.getDefaultInstance(extractProperties(preferences), null);

        MimeMessage msg = new MimeMessage(session);
        prepareMessage(msg);

        Transport.send(msg, "flowyk@gmail.com", "igltrmcldyqgoobr");
    }

    private Properties extractProperties(Preferences prefs) {
        Properties props = new Properties();
        props.put("mail.smtp.host", prefs.get("host", null));
        props.put("mail.smtp.port", prefs.get("port", null));
        props.put("mail.smtp.auth", prefs.get("auth", null));
        props.put("mail.smtp.starttls.enable", prefs.get("starttls", null));
        return props;
    }

    private void prepareMessage(MimeMessage msg) throws MessagingException {
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress("flowyk+test@gmail.com"));
        msg.setSubject("Just a msg");
        msg.setContent("This is the content of a message", "text/html");
    }

    private void sendMessage(MimeMessage msg, Preferences prefs) throws MessagingException {
        Transport.send(msg, prefs.get("user", null), prefs.get("password", null));
    }

}
