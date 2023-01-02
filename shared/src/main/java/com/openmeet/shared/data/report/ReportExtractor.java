package com.openmeet.shared.data.report;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportExtractor implements ResultSetExtractor<Report> {

    @Override
    public Report extract(ResultSet resultSet) throws SQLException {

        Report report = new Report();

        report.setId(resultSet.getInt(Report.REPORT + ".id"));
        report.setMeeterReporter(resultSet.getInt(Report.REPORT + ".meeterReporter"));
        report.setMeeterReported(resultSet.getInt(Report.REPORT + ".meeterReported"));
        report.setReason(resultSet.getString(Report.REPORT + ".reason"));
        report.setCreationDate(resultSet.getTimestamp(Report.REPORT + ".creationDate"));

        return report;
    }
}
