
package com.company.campaign.reporting.dto;

import java.util.List;
import java.util.Map;

public class FlattenedReportDTO {

    // Column headers in order
    private List<String> columns;

    // Each row is a map: columnName -> value
    private List<Map<String, String>> rows;

    public List<String> getColumns() { return columns; }
    public void setColumns(List<String> columns) { this.columns = columns; }

    public List<Map<String, String>> getRows() { return rows; }
    public void setRows(List<Map<String, String>> rows) { this.rows = rows; }
}
