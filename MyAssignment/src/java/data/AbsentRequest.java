package data;

public class AbsentRequest {

    private String abid;
    private String title;
    private String reason;
    private String fromDate;
    private String toDate;
    private RequestStatus status;
    private String createBy;
    private String createAt;

    public AbsentRequest(String abid, String title, String reason, String fromDate, String toDate, RequestStatus status, String createBy, String createAt) {
        this.abid = abid;
        this.title = title;
        this.reason = reason;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.status = status;
        this.createBy = createBy;
        this.createAt = createAt;
    }

    public String getAbid() {
        return abid;
    }

    public void setAbid(String abid) {
        this.abid = abid;
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

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getStatusString() {
        return status.toString();
    }
}
