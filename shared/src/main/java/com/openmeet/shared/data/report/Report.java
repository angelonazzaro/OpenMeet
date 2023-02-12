package com.openmeet.shared.data.report;

import com.openmeet.shared.data.storage.IEntity;

import java.sql.Timestamp;
import java.util.HashMap;

/**
 * This class represents the Report Entity.
 *
 * @see IEntity
 *
 * @author Angelo Nazzaro
 * @author Francesco Granozio
 * */
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

    public Report() {

    }

    /**
     * Returns the Report as an hashMap.
     *
     * @see IEntity
     *
     * @return the Ban as an hashMap.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public HashMap<String, ?> toHashMap() {

        return new HashMap<>() {{
            put("id", id);
            put("meeterReporter", meeterReporter);
            put("meeterReported", meeterReported);
            put("reason", reason);
            put("isArchived", isArchived);
            put("creationDate", creationDate.toString());
        }};
    }

    /**
     * Returns the Report as an hashMap.
     *
     * @see IEntity
     *
     * @param fields the fields to be returned.
     * @return the Ban as an hashMap.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    @Override
    public HashMap<String, ?> toHashMap(String... fields) {

        return new HashMap<>() {{
            for (String field : fields) {
                switch (field) {
                    case REPORT_ID:
                        put("id", id);
                        break;
                    case REPORT_MEETER_REPORTER:
                        put("meeterReporter", meeterReporter);
                        break;
                    case REPORT_MEETER_REPORTED:
                        put("meeterReported", meeterReported);
                        break;
                    case REPORT_REASON:
                        put("reason", reason);
                        break;
                    case REPORT_IS_ARCHIVED:
                        put("isArchived", isArchived);
                        break;
                    case REPORT_CREATION_DATE:
                        put("creationDate", creationDate.toString());
                        break;
                }
            }
        }};
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", meeterReporter=" + meeterReporter +
                ", meeterReported=" + meeterReported +
                ", reason='" + reason + '\'' +
                ", isArchived=" + isArchived +
                ", creationDate=" + creationDate +
                '}';
    }

    /**
     * Returns the id of the Report.
     *
     * @return the id of the Report.
     *
     * @author Angelo
     * @author Francesco
     * */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the Report.
     *
     * @param id the id of the Report.
     *
     * @author Angelo
     * @author Francesco
     * */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the id of the meeter who created the Report.
     *
     * @return the id of the meeter who created the Report.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public int getMeeterReporter() {
        return meeterReporter;
    }

    /**
     * Sets the id of the meeter who created the Report.
     *
     * @param meeterReporter the id of the meeter  who created the Report.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setMeeterReporter(int meeterReporter) {
        this.meeterReporter = meeterReporter;
    }

    /**
     * Returns the id of the meeter who got reported.
     *
     * @return the id of the meeter who got reported.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public int getMeeterReported() {
        return meeterReported;
    }

    /**
     * Sets the id of the meeter who got reported.
     *
     * @param meeterReported the id of the meeter who got reported.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setMeeterReported(int meeterReported) {
        this.meeterReported = meeterReported;
    }

    /**
     * Returns the reason of the Report.
     *
     * @return the reason of the Report.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the reason of the Report.
     *
     * @param reason the reason of the Report.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Returns the isArchived of the Report.
     *
     * @return the isArchived of the Report.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public boolean isArchived() {
        return isArchived;
    }

    /**
     * Sets the isArchived of the Report.
     *
     * @param isArchived the isArchived of the Report.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setIsArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

    /**
     * Returns the creationDate of the Report.
     *
     * @return the creationDate of the Report.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public Timestamp getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creationDate of the Report.
     *
     * @param creationDate the creationDate of the Report.
     *
     * @author Angelo Nazzaro
     * @author Francesco Granozio
     * */
    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
