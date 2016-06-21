package com.flowyk.apodys.bussiness.boundary;

import com.flowyk.apodys.bussiness.entity.LocalizationUnit;

import javax.inject.Singleton;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@Singleton
public class Messages {

    private ResourceBundle resourceBundle;

    public Messages() {
        resourceBundle = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());
    }

    public String parse(LocalizationUnit localizationUnit) {
        return MessageFormat.format(getString(localizationUnit.getKey()), localizationUnit.getArguments());
//        (new MessageFormat(getString(localizationUnit.getKey()))).format(localizationUnit.getArguments(), new StringBuffer(), null).toString()
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public String getString(String key) {
        if (resourceBundle.containsKey(key)) {
            return resourceBundle.getString(key);
        } else {
            return "???" + key + "???";
        }
    }
}
