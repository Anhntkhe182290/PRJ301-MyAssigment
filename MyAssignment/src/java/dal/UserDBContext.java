package dal;

import data.Role;
import data.User;
import data.Department;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDBContext extends DBContext<User> {

    private static final Logger LOGGER = Logger.getLogger(UserDBContext.class.getName());

    public ArrayList<User> list() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT u.username, u.password, u.fullname, u.dob, u.gender, u.depid, "
                + "r.rid, role.rname AS role_name, d.depid, d.depname "
                + "FROM [User] u "
                + "INNER JOIN User_Role r ON u.username = r.username "
                + "INNER JOIN Role role ON r.rid = role.rid "
                + "INNER JOIN Department d ON u.depid = d.depid";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = mapUser(rs);
                users.add(user);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi truy vấn danh sách User", ex);
        }
        return users;
    }

    /**
     * Lấy User theo ID
     */
    public User getUserByUsernameAndPassword(String username, String password) {
    String sql = "SELECT u.username, u.password, u.fullname, u.dob, u.gender, u.depid, "
            + "r.rid, r.rname AS role_name, d.depid, d.depname "
            + "FROM [User] u "
            + "INNER JOIN User_Role ur ON u.username = ur.username "
            + "INNER JOIN Role r ON ur.rid = r.rid "
            + "INNER JOIN Department d ON u.depid = d.depid "
            + "WHERE u.username = ? AND u.password = ?";

    try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            User user = mapUser(rs);
            System.out.println("DEBUG: User lấy từ DB - " + user.getUsername() + " - Role: " + user.getRole().getRname());
            return user;
        }
    } catch (SQLException ex) {
        System.out.println("DEBUG: Lỗi truy vấn SQL trong UserDBContext");
        ex.printStackTrace();
    }
    return null;
}

    /**
     * Lấy User theo username & password (Dùng cho đăng nhập)
     */
    public User get(String username, String password) {
        User user = null;
        String sql = "SELECT u.username, u.password, u.fullname, u.dob, u.gender, u.depid, "
                + "r.rid, role.rname AS role_name, d.depid, d.depname "
                + "FROM [User] u "
                + "INNER JOIN User_Role r ON u.username = r.username "
                + "INNER JOIN Role role ON r.rid = role.rid "
                + "INNER JOIN Department d ON u.depid = d.depid "
                + "WHERE u.username = ? AND u.password = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = mapUser(rs);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy User theo username và password", ex);
        }
        return user;
    }

    @Override
    public void insert(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private User mapUser(ResultSet rs) throws SQLException {
    User user = new User();
    user.setUsername(rs.getString("username"));
    user.setPassword(rs.getString("password"));
    user.setFullName(rs.getString("fullname"));
    user.setDob(rs.getDate("dob"));
    user.setGender(rs.getInt("gender"));

    // Department
    Department department = new Department();
    department.setDepid(rs.getInt("depid"));
    department.setDepName(rs.getString("depname"));
    user.setDepartment(department);

    // Role
    Role role = new Role();
    role.setRoleId(rs.getInt("rid"));
    role.setRname(rs.getString("role_name"));
    user.setRole(role);

    return user;
}

    @Override
    public User get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
