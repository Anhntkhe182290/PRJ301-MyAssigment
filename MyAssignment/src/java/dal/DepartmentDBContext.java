package dal;

import data.Department;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartmentDBContext extends DBContext<Department> {

    public ArrayList<Department> getAllDepartments() {
        ArrayList<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM Department";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                departments.add(new Department(rs.getString("depid"), rs.getString("depname")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return departments;
    }

    @Override
    public ArrayList<Department> list() {
        return getAllDepartments();
    }

    @Override
    public Department get(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insert(Department model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Department model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Department model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
