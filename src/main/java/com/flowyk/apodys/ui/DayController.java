package com.flowyk.apodys.ui;

import com.flowyk.apodys.PolozkaPlanu;
import javafx.fxml.FXML;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.ResourceBundle;

public class DayController {

    @Inject
    private Context context;

    LocalDate day;
    List<PolozkaPlanu> shifts;

}
