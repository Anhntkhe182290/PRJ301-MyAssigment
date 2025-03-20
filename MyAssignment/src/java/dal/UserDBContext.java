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
                + "r.rid, r.rname AS role_name, d.depid, d.depname "
                + "FROM [User] u "
                + "INNER JOIN User_Role r ON u.username = r.username "
                + "INNER JOIN Role role ON r.rid = role.rid "
                + "INNER JOIN Department d ON u.depid = d.depid";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapUser(rs));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi truy vấn danh sách User", ex);
        }
        return users;
    }

    /**
     * Lấy User theo Username và Password (Dùng cho đăng nhập)
     */
    public User getUserByUsernameAndPassword(String username, String password) {
    String sql = "SELECT u.username, u.password, u.fullname, u.dob, u.gender, u.depid, "
            + "r.rid, role.rname AS role_name, d.depid, d.depname "
            + "FROM [User] u "
            + "INNER JOIN User_Role r ON u.username = r.username "
            + "INNER JOIN Role role ON r.rid = role.rid "
            + "INNER JOIN Department d ON u.depid = d.depid "
            + "WHERE u.username = ? AND u.password = ?";

    try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            User user = mapUser(rs);
            System.out.println("DEBUG: User lấy từ DB - Username: " + user.getUsername() +
                    ", Role: " + user.getRole().getRname());
            return user;
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    System.out.println("DEBUG: Không tìm thấy user trong DB!");
    return null;
}


    /**
     * Cập nhật mật khẩu
     */
    public void updatePassword(String username, String newPassword) {
        String sql = "UPDATE [User] SET password = ? WHERE username = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("DEBUG: Cập nhật mật khẩu thành công cho " + username);
            } else {
                System.out.println("DEBUG: Không tìm thấy người dùng để cập nhật mật khẩu.");
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi cập nhật mật khẩu", ex);
        }
    }

    @Override
    public void insert(User model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(User model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(User model) {
        throw new UnsupportedOperationException("Not supported yet.");
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
    role.setRname(rs.getString("role_name").trim());  // 🚀 Trim để tránh lỗi khoảng trắng
    user.setRole(role);

    System.out.println("DEBUG: mapUser() - Role name lấy từ DB: " + role.getRname());

    return user;
}


    @Override
    public User get(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
