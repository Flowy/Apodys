package com.flowyk.apodys.email;

import org.junit.Test;

import java.util.prefs.Preferences;

import static org.junit.Assert.*;

public class EmailTest {
    @Test
    public void send() throws Exception {
        Preferences prefs = Preferences.userNodeForPackage(Email.class);
        prefs.put("host", "smtp.gmail.com");
        prefs.put("port", "587");
        prefs.put("auth", "true");
        prefs.put("starttls", "true");
        prefs.put("user", "flowyk@gmail.com");
        prefs.put("password", null);
        (new Email()).send();
    }

}