package com.flowyk.apodys.ui;

import com.flowyk.apodys.*;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PlanController {
    private Logger logger = Logger.getLogger(getClass().getCanonicalName());

    private List<LocalDate> days;
    private List<Zamestnanec> employees;
    private List<Shift> shifts;

    @Inject
    private Context context;

    @FXML
    private GridPane planGrid;

    public PlanController() {
        days = new ArrayList<>();
        employees = new ArrayList<>();
        shifts = new ArrayList<>();
    }

    @FXML
    public void initialize() {

        for (Shift shift : getWorkplan()) {
            addShift(shift);
        }
        redraw();
    }

    public void redraw() {
        for (Shift shift : shifts) {
            planGrid.add(
                    new Text(shift.predloha().getNazov()),
                    days.indexOf(shift.zaciatok().toLocalDate()),
                    employees.indexOf(shift.vykonavatel()));
        }
        planGrid.autosize();
    }

    private void addShift(Shift shift) {
        shifts.add(shift);
        if (!days.contains(shift.zaciatok().toLocalDate())) {
            days.add(shift.zaciatok().toLocalDate());
        }
        if (!employees.contains(shift.vykonavatel())) {
            employees.add(shift.vykonavatel());
        }
    }

    private PlanSmien getWorkplan() {
        PlanSmien result = new PlanSmien();
        PredlohaSmeny predlohaR2P = new PredlohaSmeny("R2P", LocalTime.of(6, 0), LocalTime.of(18, 0), Duration.ofHours(12L));
        PredlohaSmeny predlohaP1C = new PredlohaSmeny("P1C", LocalTime.of(9, 0), LocalTime.of(21, 0), Duration.ofHours(12L));
        PredlohaSmeny predlohaO75 = new PredlohaSmeny("07,5", LocalTime.of(14, 0), LocalTime.of(22, 0), Duration.ofHours(8L));
        PredlohaSmeny predlohaN2P = new PredlohaSmeny("N2P", LocalTime.of(18, 0), LocalTime.of(6, 0), Period.ofDays(1), Duration.ofHours(12L));

        ZoneId testovanaZona = ZoneId.of("Europe/Bratislava");

        Shift smena = predlohaR2P.vygenerujOd(LocalDate.now(), testovanaZona);
        smena.setZamestnanec(new Zamestnanec("Papa Smurf", "flowyk+testPapaSmurf@gmail.com"));

        result.pridatPolozku(smena);
        return result;
    }
}
