package dal;

import data.Role;
import data.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDBContext extends DBContext<User> {

    public User get(String username, String password) {
        try {
            String sql = "SELECT u.username, u.password, u.fullName, u.DOB, u.Gender, u.depid, r.rid, r.rname " +
                         "FROM [User] u " +
                         "LEFT JOIN User_Role ur ON u.username = ur.username " +
                         "LEFT JOIN [Role] r ON ur.rid = r.rid " +
                         "WHERE u.username = ? AND u.password = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();

            User user = null;
            ArrayList<Role> roles = new ArrayList<>();
            
            while (rs.next()) {
                if (user == null) {
                    user = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("fullName"),
                        rs.getString("DOB"),
                        rs.getInt("Gender"),
                        rs.getString("depid"),
                        roles
                    );
                }

                Role role = new Role(rs.getString("rid"), rs.getString("rname"));
                roles.add(role);
            }

            return user;
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<User> list() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User get(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
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
}
