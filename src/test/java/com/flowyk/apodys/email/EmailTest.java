package com.flowyk.apodys.email;

import org.junit.Test;

import java.util.prefs.Preferences;

import static org.junit.Assert.*;

public class EmailTest {

    @Test
    public void send() throws Exception {
        Preferences prefs = Preferences.userNodeForPackage(Email.class);
        prefs.put("host", "mailtrap.io");
        prefs.put("port", "2525");
        prefs.put("auth", "true");
        prefs.put("starttls", "true");
        prefs.put("user", "9b140533cb555d");
        prefs.put("password", "096f6588e33685");
        (new Email()).send();
    }

}