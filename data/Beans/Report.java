import java.sql.Timestamp;

public class Report {

    private int id;
    private int meeterReporter;
    private int meeterReported;
    private String reason;
    private Timestamp creationDate;

    public Report(){

    }

    @Override
    public String toString() {
        return "Report{" +
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

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
