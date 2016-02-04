package com.flowyk.apodys.ui;

import com.flowyk.apodys.*;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PlanController {
    private Logger logger = Logger.getLogger(getClass().getCanonicalName());

    private List<LocalDate> days;

    @Inject
    private Context context;

    @FXML
    private GridPane planGrid;

    @Inject
    private Stage stage;

    public PlanController() {
        days = new ArrayList<>();
    }

    @FXML
    public void initialize() {
        redraw();
        context.addListener(observable -> redraw());
    }

    public void redraw() {
        planGrid.getChildren().clear();
        days.clear();
        List<Zamestnanec> employees = context.getEmployees();
        for (Zamestnanec employee: employees) {
            planGrid.add(
                    new Text(employee.getName()),
                    0,
                    employees.indexOf(employee) + 1
            );
        }
        for (Shift shift : getWorkplan()) {
            if (!days.contains(shift.zaciatok().toLocalDate())) {
                days.add(shift.zaciatok().toLocalDate());
                planGrid.add(
                        new Text(shift.zaciatok().toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))),
                        days.indexOf(shift.zaciatok().toLocalDate()) + 1,
                        0
                );
            }
            planGrid.add(
                    new Text(shift.predloha().getNazov()),
                    days.indexOf(shift.zaciatok().toLocalDate()) + 1,
                    employees.indexOf(shift.vykonavatel()) + 1);
        }
        planGrid.setGridLinesVisible(true);
        stage.sizeToScene();
//        planGrid.autosize();
    }

    private PlanSmien getWorkplan() {
        return context.getWorkplan();
//        PlanSmien result = new PlanSmien();
//        PredlohaSmeny predlohaR2P = new PredlohaSmeny("R2P", LocalTime.of(6, 0), LocalTime.of(18, 0), Duration.ofHours(12L));
//        PredlohaSmeny predlohaP1C = new PredlohaSmeny("P1C", LocalTime.of(9, 0), LocalTime.of(21, 0), Duration.ofHours(12L));
//        PredlohaSmeny predlohaO75 = new PredlohaSmeny("07,5", LocalTime.of(14, 0), LocalTime.of(22, 0), Duration.ofHours(8L));
//        PredlohaSmeny predlohaN2P = new PredlohaSmeny("N2P", LocalTime.of(18, 0), LocalTime.of(6, 0), Period.ofDays(1), Duration.ofHours(12L));
//
//        ZoneId testovanaZona = ZoneId.of("Europe/Bratislava");
//
//        Shift smena = predlohaR2P.vygenerujOd(LocalDate.now(), testovanaZona);
//        smena.setZamestnanec(new Zamestnanec("Papa Smurf", "flowyk+testPapaSmurf@gmail.com"));
//
//        result.pridatPolozku(smena);
//        return result;
    }
}
