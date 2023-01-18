package com.openmeet.shared.data.report;

import com.openmeet.shared.data.storage.DAO;
import com.openmeet.shared.data.storage.SQLDAO;
import com.openmeet.shared.exceptions.InvalidPrimaryKeyException;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ReportDAO extends SQLDAO implements DAO<Report> {
    public ReportDAO(DataSource source) {
        super(source);
    }

    @Override
    public List<Report> doRetrieveByCondition(String condition) throws SQLException {
        return genericDoRetrieveByCondition(Report.REPORT, condition, new ReportExtractor(), source);
    }

    @Override
    public Report doRetrieveByKey(String key) throws SQLException, InvalidPrimaryKeyException {

        if (key == null) {
            throw new InvalidPrimaryKeyException(key);
        }
        List<Report> report = doRetrieveByCondition(
                String.format("%s = '%s'", Report.REPORT_ID, key)
        );
        return report.isEmpty() ? null : report.get(0);
    }

    @Override
    public List<Report> doRetrieveAll() throws SQLException {
        return doRetrieveByCondition("TRUE");
    }

    @Override
    public List<Report> doRetrieveAll(int row_count) throws SQLException {
        return doRetrieveByCondition("TRUE LIMIT " + row_count);
    }

    @Override
    public List<Report> doRetrieveAll(int offset, int row_count) throws SQLException {
        return doRetrieveByCondition("TRUE LIMIT " + offset + ", " + row_count);
    }

    @Override
    public boolean doSave(Report obj) throws SQLException {
        return genericDoSave(Report.REPORT, obj.toHashMap(), this.source);
    }

    @Override
    public boolean doSave(HashMap<String, ?> values) throws SQLException {
        return genericDoSave(Report.REPORT, values, this.source);
    }

    @Override
    public boolean doUpdate(HashMap<String, ?> values, String condition) throws SQLException {
        return genericDoUpdate(Report.REPORT, condition, values, this.source);
    }

    @Override
    public boolean doSaveOrUpdate(Report obj) throws SQLException {

        if (doRetrieveByKey(String.valueOf(obj.getId())) == null)
            return doSave(obj);

        return doUpdate(obj.toHashMap(),
                String.format("%s = '%s'", Report.REPORT_ID, obj.getId()));
    }

    @Override
    public boolean doDelete(String condition) throws SQLException {
        return genericDoDelete(Report.REPORT, condition, this.source);
    }
}
