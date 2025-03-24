package data;

public class AbsentRequest {
    private String absenceId; // Đổi từ absenceID thành absenceId để thống nhất kiểu đặt tên
    private String title;
    private String reason;
    private String fromDate;
    private String toDate;
    private int status;
    private String createdBy;
    private String creationDate;

    // Constructor
    public AbsentRequest(String absenceId, String title, String reason, String fromDate, String toDate, int status, String createdBy, String creationDate) {
        this.absenceId = absenceId;
        this.title = title;
        this.reason = reason;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.status = status;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
    }

    //Getter cho `absenceId`
    public String getAbsenceId() {
        return absenceId;
    }

    public void setAbsenceId(String absenceId) {
        this.absenceId = absenceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
