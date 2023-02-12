package com.openmeet.shared.data.report;

import com.openmeet.shared.data.storage.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The extractor is used to convert the ResultSet returned by the database query into a Report object.
 *
 * @author Angelo Nazzaro
 * @author Francesco Granozio
 */
public class ReportExtractor implements ResultSetExtractor<Report> {

    /**
     * Returns a Report object from the result set.
     *
     * @param resultSet the result set deriving from the query statement.
     * @return the Report object contained in the resultSet.
     *
     * @author Francesco Granozio
     * @author Angelo Nazzaro
     */
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
