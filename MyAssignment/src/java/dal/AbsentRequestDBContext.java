package dal;

import data.AbsentRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbsentRequestDBContext extends DBContext<AbsentRequest> {

    private static final Logger LOGGER = Logger.getLogger(AbsentRequestDBContext.class.getName());

    public ArrayList<AbsentRequest> getAbsentRequestsByUser(String username) {
        ArrayList<AbsentRequest> requests = new ArrayList<>();
        String sql = "SELECT abid, title, reason, [from], [to], status, createBy, createAt "
                + "FROM AbsentRequest WHERE createBy = ?";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AbsentRequest request = new AbsentRequest(
                        rs.getString("abid"),
                        rs.getString("title"),
                        rs.getString("reason"),
                        rs.getString("from"),
                        rs.getString("to"),
                        rs.getInt("status"),
                        rs.getString("createBy"),
                        rs.getString("createAt")
                );
                requests.add(request);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi truy vấn danh sách đơn xin nghỉ", ex);
        }
        return requests;
    }

    public void insertAbsentRequest(AbsentRequest request) {
        String sql = "INSERT INTO AbsentRequest (abid, title, reason, [from], [to], status, createBy, createAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, request.getAbsenceId());
            stmt.setString(2, request.getTitle());
            stmt.setString(3, request.getReason());
            stmt.setString(4, request.getFromDate());
            stmt.setString(5, request.getToDate());
            stmt.setInt(6, request.getStatus());
            stmt.setString(7, request.getCreatedBy());
            stmt.setString(8, request.getCreationDate());

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi thêm đơn xin nghỉ", ex);
        }
    }

    public void updateAbsentRequestStatus(String absenceId, int status) {
        String sql = "UPDATE AbsentRequest SET status = ? WHERE abid = ?";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, status);
            stmt.setString(2, absenceId);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi cập nhật trạng thái đơn xin nghỉ", ex);
        }
    }

    public ArrayList<AbsentRequest> list() {
        ArrayList<AbsentRequest> requests = new ArrayList<>();
        String sql = "SELECT abid, title, reason, [from], [to], status, createBy, createAt FROM AbsentRequest";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AbsentRequest request = new AbsentRequest(
                        rs.getString("abid"),
                        rs.getString("title"),
                        rs.getString("reason"),
                        rs.getString("from"),
                        rs.getString("to"),
                        rs.getInt("status"),
                        rs.getString("createBy"),
                        rs.getString("createAt")
                );
                requests.add(request);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy danh sách đơn xin nghỉ", ex);
        }
        return requests;
    }

    @Override
    public AbsentRequest get(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insert(AbsentRequest model) {
        insertAbsentRequest(model);
    }

    @Override
    public void update(AbsentRequest model) {
        updateAbsentRequestStatus(model.getAbsenceId(), model.getStatus());
    }

    @Override
    public void delete(AbsentRequest model) {
        String sql = "DELETE FROM AbsentRequest WHERE abid = ?";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, model.getAbsenceId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi xóa đơn xin nghỉ", ex);
        }
    }

    public void deleteById(String absenceId) {
        String sql = "DELETE FROM AbsentRequest WHERE abid = ? AND status = 1";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, absenceId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi xóa đơn xin nghỉ", e);
        }
    }

    public AbsentRequest getRequestById(String absenceId) {
        String sql = "SELECT * FROM AbsentRequest WHERE abid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, absenceId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new AbsentRequest(
                        rs.getString("abid"),
                        rs.getString("title"),
                        rs.getString("reason"),
                        rs.getString("from"),
                        rs.getString("to"),
                        rs.getInt("status"),
                        rs.getString("createBy"),
                        rs.getString("createAt")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public AbsentRequest getAbsentRequestById(String absenceId) {
        return getRequestById(absenceId);
    }

    public void updateAbsentRequestContent(AbsentRequest request) {
        String sql = "UPDATE AbsentRequest SET title = ?, reason = ?, [from] = ?, [to] = ? WHERE abid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, request.getTitle());
            stmt.setString(2, request.getReason());
            stmt.setString(3, request.getFromDate());
            stmt.setString(4, request.getToDate());
            stmt.setString(5, request.getAbsenceId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Lấy tất cả đơn xin nghỉ theo phòng ban
    public List<AbsentRequest> getRequestsByDepartment(int depId) {
        List<AbsentRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM AbsentRequest WHERE createBy IN "
                + "(SELECT username FROM [User] WHERE depid = ?) ORDER BY createAt DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, depId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapResultToAbsentRequest(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Lọc theo trạng thái + ngày
    public List<AbsentRequest> filterRequestsByStatusAndDate(int depId, Integer status, String date) {
        List<AbsentRequest> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM AbsentRequest WHERE createBy IN (SELECT username FROM [User] WHERE depid = ?)");
        if (status != null) {
            sql.append(" AND status = ?");
        }
        if (date != null && !date.isEmpty()) {
            sql.append(" AND CAST(createAt AS DATE) = ?");
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int idx = 1;
            stmt.setInt(idx++, depId);
            if (status != null) {
                stmt.setInt(idx++, status);
            }
            if (date != null && !date.isEmpty()) {
                stmt.setString(idx, date);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapResultToAbsentRequest(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private AbsentRequest mapResultToAbsentRequest(ResultSet rs) throws SQLException {
        return new AbsentRequest(
                rs.getString("abid"),
                rs.getString("title"),
                rs.getString("reason"),
                rs.getString("from"),
                rs.getString("to"),
                rs.getInt("status"),
                rs.getString("createBy"),
                rs.getString("createAt")
        );
    }
    
    //Hàm lọc
    public List<AbsentRequest> getRequestsByDepartmentWithFilter(int depId, String status, String date) {
        List<AbsentRequest> requests = new ArrayList<>();
        String sql = "SELECT ar.* FROM AbsentRequest ar "
                + "JOIN [User] u ON ar.createBy = u.username "
                + "WHERE u.depid = ? ";

        if (status != null && !status.isEmpty()) {
            sql += "AND ar.status = ? ";
        }
        if (date != null && !date.isEmpty()) {
            sql += "AND ar.createAt = ? ";
        }

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, depId);

            int paramIndex = 2;
            if (status != null && !status.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(status));
            }
            if (date != null && !date.isEmpty()) {
                stmt.setDate(paramIndex++, Date.valueOf(date));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                requests.add(mapResultToAbsentRequest(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return requests;
    }

}
