package com.flowyk.apodys.ui;

import java.util.Map;

public class TableRow<ROW_KEY, COLUMN_KEY, CELL> {

    private ROW_KEY rowKey;
    private Map<COLUMN_KEY, CELL> cells;

    public ROW_KEY getKey() {
        return rowKey;
    }

    public CELL getValue(COLUMN_KEY date) {
        return cells.get(date);
    }
}
