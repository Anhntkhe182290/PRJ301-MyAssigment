package data;

public class Department {
    private int depid;
    private String depName;

    public Department() {}

    public Department(int depid, String depName) {
        this.depid = depid;
        this.depName = depName;
    }

    public int getDepid() {
        return depid;
    }

    public void setDepid(int depid) {
        this.depid = depid;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }
}
