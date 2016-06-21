package com.flowyk.apodys.ui;

import com.flowyk.apodys.planovanie.RuleOffender;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ErrorsChangedListener implements InvalidationListener {

    private List<ShiftTableCell> listeningCells = new ArrayList<>();

    public void addListeningCell(ShiftTableCell tableCell) {
        listeningCells.add(tableCell);
    }

    @Override
    public void invalidated(Observable observable) {
        ObservableList<RuleOffender> errors = (ObservableList<RuleOffender>) observable;
        for (ShiftTableCell cell : listeningCells) {
            cell.removeErrors();
            errors.stream()
                    .filter(error -> error.getOffender().equals(cell.getItem()))
                    .forEach(cell::addError);
        }
    }
}
