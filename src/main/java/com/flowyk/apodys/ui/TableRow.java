package com.flowyk.apodys.ui;

import java.util.Collection;
import java.util.Map;

public class TableRow<ROW_KEY, COLUMN_KEY, CELL> {

    private ROW_KEY rowKey;
    private Map<COLUMN_KEY, CELL> cells;

    public TableRow(ROW_KEY rowKey, Map<COLUMN_KEY, CELL> cells) {
        this.rowKey = rowKey;
        this.cells = cells;
    }

    public ROW_KEY getKey() {
        return rowKey;
    }

    public CELL get(COLUMN_KEY key) {
        return cells.get(key);
    }

    public Collection<CELL> getCells() {
        return cells.values();
    }
}
