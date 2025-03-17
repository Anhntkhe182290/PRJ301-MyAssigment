package dal;

import data.AbsentRequest;
import data.RequestStatus;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbsentRequestDBContext extends DBContext<AbsentRequest> {

    public ArrayList<AbsentRequest> getAllRequests() {
        ArrayList<AbsentRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM AbsentRequest";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new AbsentRequest(
                    rs.getString("abid"),
                    rs.getString("title"),
                    rs.getString("reason"),
                    rs.getString("from"),
                    rs.getString("to"),
                    RequestStatus.fromCode(rs.getInt("status")),
                    rs.getString("createBy"),
                    rs.getString("createAt")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AbsentRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void updateRequestStatus(String abid, int status) {
        String sql = "UPDATE AbsentRequest SET status = ? WHERE abid = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, status);
            ps.setString(2, abid);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AbsentRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<AbsentRequest> list() {
        return getAllRequests();
    }

    @Override
    public AbsentRequest get(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insert(AbsentRequest model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(AbsentRequest model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(AbsentRequest model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
