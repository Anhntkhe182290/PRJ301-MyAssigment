package dal;

import data.AbsentRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbsentRequestDBContext extends DBContext<AbsentRequest> {

    private static final Logger LOGGER = Logger.getLogger(AbsentRequestDBContext.class.getName());

    // ✅ Lấy danh sách đơn xin nghỉ của một nhân viên cụ thể
    public ArrayList<AbsentRequest> getAbsentRequestsByUser(String username) {
        ArrayList<AbsentRequest> requests = new ArrayList<>();
        String sql = "SELECT absence_id, title, reason, from_date, to_date, status, created_by, creation_date "
                   + "FROM AbsentRequest WHERE created_by = ?";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AbsentRequest request = new AbsentRequest(
                    rs.getString("absence_id"),
                    rs.getString("title"),
                    rs.getString("reason"),
                    rs.getString("from_date"),
                    rs.getString("to_date"),
                    rs.getInt("status"),
                    rs.getString("created_by"),
                    rs.getString("creation_date")
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

    // ✅ Thêm một đơn xin nghỉ mới
    public void insertAbsentRequest(AbsentRequest request) {
        String sql = "INSERT INTO AbsentRequest (absence_id, title, reason, from_date, to_date, status, created_by, creation_date) "
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

    // ✅ Cập nhật trạng thái đơn xin nghỉ (Manager/Boss duyệt đơn)
    public void updateAbsentRequestStatus(String absenceId, int status) {
        String sql = "UPDATE AbsentRequest SET status = ? WHERE absence_id = ?";

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
        String sql = "SELECT absence_id, title, reason, from_date, to_date, status, created_by, creation_date FROM AbsentRequest";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AbsentRequest request = new AbsentRequest(
                    rs.getString("absence_id"),
                    rs.getString("title"),
                    rs.getString("reason"),
                    rs.getString("from_date"),
                    rs.getString("to_date"),
                    rs.getInt("status"),
                    rs.getString("created_by"),
                    rs.getString("creation_date")
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
        String sql = "DELETE FROM AbsentRequest WHERE absence_id = ?";

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, model.getAbsenceId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi xóa đơn xin nghỉ", ex);
        }
    }
}
