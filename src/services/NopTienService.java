package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.NopTienModel;

public class NopTienService {

    // Add a money collection transaction
    public boolean add(NopTienModel nopTienModel) throws ClassNotFoundException, SQLException {
        try (Connection connection = MysqlConnection.getMysqlConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO nop_tien(IDNopTien, MaKhoanThu,SoTien, NgayThu) VALUES (?, ?,?, NOW())",
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, nopTienModel.getIdNopTien());
            preparedStatement.setInt(2, nopTienModel.getMaKhoanThu());
            preparedStatement.setDouble(3, nopTienModel.getSoTien());
            preparedStatement.executeUpdate();

        } // Auto-closes connection and preparedStatement

        return true;
    }

    // Delete a money collection transaction
    public boolean delete(int idNopTien, int maKhoanThu) throws ClassNotFoundException, SQLException {
        String sql = "DELETE FROM nop_tien WHERE IDNopTien = ? AND MaKhoanThu = ?";
        try (Connection connection = MysqlConnection.getMysqlConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, idNopTien);
            preparedStatement.setInt(2, maKhoanThu);
            preparedStatement.executeUpdate();
        }

        return true;
    }

    // Get a list of all money collection transactions
    public List<NopTienModel> getListNopTien() throws ClassNotFoundException, SQLException {
        List<NopTienModel> list = new ArrayList<>();

        try (Connection connection = MysqlConnection.getMysqlConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM nop_tien");
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                NopTienModel nopTienModel = new NopTienModel();
                nopTienModel.setIdNopTien(rs.getInt("IDNopTien"));
                nopTienModel.setMaKhoanThu(rs.getInt("MaKhoanThu"));
                nopTienModel.setSoTien(rs.getDouble("SoTien"));
                nopTienModel.setNgayThu(rs.getDate("NgayThu"));
                list.add(nopTienModel);
            }
        } // Auto-closes connection, preparedStatement, and resultSet

        return list;
    }
    
    public double getTongNopTien() throws ClassNotFoundException, SQLException {
        List<NopTienModel> list = new ArrayList<>();
        double tongSoTien = 0;
        try (Connection connection = MysqlConnection.getMysqlConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM nop_tien");
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                NopTienModel nopTienModel = new NopTienModel();
                nopTienModel.setIdNopTien(rs.getInt("IDNopTien"));
                nopTienModel.setMaKhoanThu(rs.getInt("MaKhoanThu"));
                nopTienModel.setSoTien(rs.getDouble("SoTien"));
                nopTienModel.setNgayThu(rs.getDate("NgayThu"));
                list.add(nopTienModel);
                
                tongSoTien += nopTienModel.getSoTien();
            }
        } // Auto-closes connection, preparedStatement, and resultSet

        return tongSoTien;
    }
}
