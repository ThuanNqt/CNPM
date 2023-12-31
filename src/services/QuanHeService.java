package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.QuanHeModel;

public class QuanHeService {

    public boolean add(QuanHeModel quanHeModel) throws ClassNotFoundException, SQLException {
        try (Connection connection = MysqlConnection.getMysqlConnection();
             PreparedStatement insertStatement = connection.prepareStatement(
                     "INSERT INTO quan_he (MaHo, IDThanhVien, QuanHe) VALUES (?, ?, ?)")) {

            insertStatement.setInt(1, quanHeModel.getMaHo());
            insertStatement.setInt(2, quanHeModel.getIdThanhVien());
            insertStatement.setString(3, quanHeModel.getQuanHe());
            insertStatement.executeUpdate();
        }

        updateSoThanhVien(quanHeModel.getMaHo(), 1);
        return true;
    }

    public boolean del(int maHo, int idThanhVien) throws ClassNotFoundException, SQLException {
        updateSoThanhVien(maHo, -1);
        return true;
    }

    public List<QuanHeModel> getListQuanHe() throws ClassNotFoundException, SQLException {
        List<QuanHeModel> list = new ArrayList<>();

        try (Connection connection = MysqlConnection.getMysqlConnection();
             PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM quan_he");
             ResultSet rs = selectStatement.executeQuery()) {

            while (rs.next()) {
                QuanHeModel quanHeModel = new QuanHeModel();
                quanHeModel.setMaHo(rs.getInt("MaHo"));
                quanHeModel.setIdThanhVien(rs.getInt("IDThanhVien"));
                quanHeModel.setQuanHe(rs.getString("QuanHe"));
                list.add(quanHeModel);
            }
        }

        return list;
    }

    private void updateSoThanhVien(int maHo, int delta) throws ClassNotFoundException, SQLException {
        try (Connection connection = MysqlConnection.getMysqlConnection();
             PreparedStatement updateStatement = connection.prepareStatement(
                     "UPDATE ho_khau SET SoThanhVien = SoThanhVien + ? WHERE maho = ?")) {

            updateStatement.setInt(1, delta);
            updateStatement.setInt(2, maHo);
            updateStatement.executeUpdate();
        }
    }
    public boolean deleteRelationship(int maHo, int idThanhVien) throws ClassNotFoundException, SQLException {
        // Retrieve the QuanHeModel based on the maHo and idThanhVien, if needed

        try (Connection connection = MysqlConnection.getMysqlConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(
                     "DELETE FROM quan_he WHERE MaHo = ? AND IDThanhVien = ?")) {

            deleteStatement.setInt(1, maHo);
            deleteStatement.setInt(2, idThanhVien);
            int rowsAffected = deleteStatement.executeUpdate();

            // Check if any rows were affected (i.e., if the delete operation was successful)
            if (rowsAffected > 0) {
                updateSoThanhVien(maHo, -1); // Update SoThanhVien in ho_khau table
                return true; // Deletion successful
            }
        }

        return false; // Deletion failed or no matching record found
    }
    public boolean changeRelationship(int currentMaHo, int newMaHo, int idThanhVien, String newQuanHe) throws ClassNotFoundException, SQLException {
        try (Connection connection = MysqlConnection.getMysqlConnection();
             PreparedStatement updateStatement = connection.prepareStatement(
                     "UPDATE quan_he SET MaHo = ?, QuanHe = ? WHERE MaHo = ? AND IDThanhVien = ?")) {

            updateStatement.setInt(1, newMaHo);
            updateStatement.setString(2, newQuanHe);
            updateStatement.setInt(3, currentMaHo);
            updateStatement.setInt(4, idThanhVien);
            int rowsAffected = updateStatement.executeUpdate();

            // Kiểm tra xem có dòng nào bị ảnh hưởng không (có được cập nhật không)
            if (rowsAffected > 0) {
                // Cập nhật số thành viên cho currentMaHo và newMaHo
                updateSoThanhVien(currentMaHo, -1);
                updateSoThanhVien(newMaHo, 1);
                return true; // Cập nhật quan hệ thành công
            }
        }
        return false; // Nếu không thể cập nhật quan hệ
    }


}
