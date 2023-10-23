package io.lippia.reporter.ltm;

import gherkin.pickles.PickleCell;
import gherkin.pickles.PickleRow;
import gherkin.pickles.PickleTable;

import java.util.Iterator;
import java.util.List;

public class DataTableFormatter {
    private final List<PickleRow> list;

    public DataTableFormatter(final PickleTable dtArgument) {
        this.list = dtArgument.getRows();
    }

    public StringBuilder generateTabularFormat() {
        StringBuilder result = new StringBuilder();

        appendTable(result, this.list);

        return result;
    }

    private void appendTable(StringBuilder result, List<PickleRow> data) {
        if (data.isEmpty()) {
            return;
        }

        Iterator<PickleRow> rows = data.iterator();
        while (rows.hasNext()) {
            PickleRow row = rows.next();

            Iterator<PickleCell> cells = row.getCells().iterator();
            result.append("| ");
            while (cells.hasNext()) {
                PickleCell cell = cells.next();
                result.append(padString(cell.getValue(), cell.getValue().length())).append(" | ");
            }

            result.append("\n");
        }
    }

    private String padString(String value, int length) {
        return String.format("%-" + length + "s", value);
    }
}