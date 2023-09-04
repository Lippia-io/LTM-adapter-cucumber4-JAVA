package io.lippia.reporter.ltm;

import gherkin.ast.DataTable;
import gherkin.ast.TableCell;
import gherkin.ast.TableRow;

import java.util.Iterator;
import java.util.List;

public class DataTableFormatter {
    private final List<TableRow> list;

    public DataTableFormatter(final DataTable dtArgument) {
        this.list = dtArgument.getRows();
    }

    public StringBuilder generateTabularFormat() {
        StringBuilder result = new StringBuilder();

        appendTable(result, this.list);

        return result;
    }

    private void appendTable(StringBuilder result, List<TableRow> data) {
        if (data.isEmpty()) {
            return;
        }

        Iterator<TableRow> rows = data.iterator();
        while (rows.hasNext()) {
            TableRow row = rows.next();

            Iterator<TableCell> cells = row.getCells().iterator();
            result.append("| ");
            while (cells.hasNext()) {
                TableCell cell = cells.next();
                result.append(padString(cell.getValue(), cell.getValue().length())).append(" | ");
            }
            result.append("\n");
        }
    }

    private String padString(String value, int length) {
        return String.format("%-" + length + "s", value);
    }
}