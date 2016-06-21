package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.boundary.Messages;
import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.planovanie.RuleOffender;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShiftTableCell extends TableCell<RosterTableRow, Shift> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    protected LocalDate columnHeader;
    private Messages messages;

    @Inject
    public ShiftTableCell(Messages messages) {
        super();
        this.messages = messages;
    }

    public void setColumnHeader(LocalDate columnHeader) {
        this.columnHeader = columnHeader;
    }

    @Override
    protected void updateItem(Shift item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || empty) {
            setText(null);
            setStyle("");
            setTooltip(null);
        } else {
            setText(item.getPredloha().getNazov());
        }
    }

    private void addTooltip(String text) {
        if (getTooltip() == null) {
            setTooltip(new Tooltip(text));
        } else {
            Tooltip tooltip = getTooltip();
            tooltip.setText(tooltip.getText() + "\n" + text);
        }
    }

    public void removeErrors() {
        getStyleClass().remove("rule-offender");
        setTooltip(null);
    }

    public void addError(RuleOffender error) {
        if (!getStyleClass().contains("rule-offender")) {
            logger.debug("Shift: {} have new error: {}", getItem(), error.getCrime());
            getStyleClass().add("rule-offender");
        }
        String errorMessage = messages.parse(error.getCrime());
        addTooltip(errorMessage);
    }
}
