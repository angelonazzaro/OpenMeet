package com.openmeet.shared.data.report;

import com.openmeet.shared.data.storage.IEntity;

import java.sql.Timestamp;
import java.util.HashMap;

public class Report implements IEntity {

    public static final String REPORT = "Report";
    public static final String REPORT_ID = REPORT + ".id";
    public static final String REPORT_MEETER_REPORTER = REPORT + ".meeterReporter";
    public static final String REPORT_MEETER_REPORTED = REPORT + ".meeterReported";
    public static final String REPORT_REASON = REPORT + ".reason";
    public static final String REPORT_IS_ARCHIVED = REPORT + ".isArchived";
    public static final String REPORT_CREATION_DATE = REPORT + ".creationDate";
    private int id;
    private int meeterReporter;
    private int meeterReported;
    private String reason;
    private boolean isArchived;
    private Timestamp creationDate;

    public Report(){

    }

    public HashMap<String, ?> toHashMap() {

        return new HashMap<>() {
            {
                put("id", id);
                put("meeterReporter", meeterReporter);
                put("meeterReported", meeterReported);
                put("reason", reason);
                put("isArchived", isArchived);
                put("creationDate", creationDate.toString());
            }
        };
    }

    @Override
    public String toString() {
        return "Report.Report{" +
                "id=" + id +
                ", meeterReporter=" + meeterReporter +
                ", meeterReported=" + meeterReported +
                ", reason='" + reason + '\'' +
                ", isArchived=" + isArchived +
                ", creationDate=" + creationDate +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMeeterReporter() {
        return meeterReporter;
    }

    public void setMeeterReporter(int meeterReporter) {
        this.meeterReporter = meeterReporter;
    }

    public int getMeeterReported() {
        return meeterReported;
    }

    public void setMeeterReported(int meeterReported) {
        this.meeterReported = meeterReported;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isArchived() { return isArchived; }

    public void setIsArchived(boolean isArchived) { this.isArchived = isArchived; }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
