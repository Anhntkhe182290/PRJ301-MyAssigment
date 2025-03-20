package data;

public class Role {
    private int roleId;   // 🟢 ID vai trò (1 = Boss, 2 = Manager, 3 = Staff)
    private String rname; // 🟢 Tên vai trò ("boss", "manager", "staff")

    // 🟢 Constructor không tham số
    public Role() {}

    // 🟢 Constructor có tham số
    public Role(int roleId, String rname) {
        this.roleId = roleId;
        this.rname = rname;
    }

    // 🟢 Getter & Setter
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    // 🟢 Thêm phương thức toString() để debug nhanh
    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", rname='" + rname + '\'' +
                '}';
    }
}
