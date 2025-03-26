package dal;

import data.Department;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDBContext extends DBContext<Department> {

    public List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT depid, depname FROM Department";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Department dep = new Department();
                dep.setDepid(rs.getInt("depid"));
                dep.setDepName(rs.getString("depname"));
                list.add(dep);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
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

    @Override
    public Department get(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
