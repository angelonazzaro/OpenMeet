package com.openmeet.shared.data.report;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportExtractor implements ResultSetExtractor<Report> {

    @Override
    public Report extract(ResultSet resultSet) throws SQLException {

        Report report = new Report();

        report.setId(resultSet.getInt(Report.REPORT_ID));
        report.setMeeterReporter(resultSet.getInt(Report.REPORT_MEETER_REPORTER));
        report.setMeeterReported(resultSet.getInt(Report.REPORT_MEETER_REPORTED));
        report.setReason(resultSet.getString(Report.REPORT_REASON));
        report.setIsArchived(resultSet.getBoolean(Report.REPORT_IS_ARCHIVED));
        report.setCreationDate(resultSet.getTimestamp(Report.REPORT_CREATION_DATE));

        return report;
    }
}
