package data;

public enum RequestStatus {
    IN_PROCESS(1, "In-Process"),
    APPROVED(2, "Approved"),
    REJECTED(0, "Rejected");

    private final int code;
    private final String description;

    RequestStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() { return code; }
    public String getDescription() { return description; }

    public static RequestStatus fromCode(int code) {
        for (RequestStatus status : RequestStatus.values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }

    @Override
    public String toString() {
        return description;
    }
}
