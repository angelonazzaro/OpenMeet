package com.openmeet.shared.data.report;

import com.openmeet.shared.data.storage.IEntity;

import java.sql.Timestamp;
import java.util.HashMap;

public class Report implements IEntity {

    public static final String REPORT = "Report";

    private int id;
    private int meeterReporter;
    private int meeterReported;
    private String reason;

    private int status;
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

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
