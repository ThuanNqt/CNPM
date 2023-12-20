package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.KhoanThuModel;

public class KhoanThuService {

    public boolean add(KhoanThuModel khoanThuModel) throws ClassNotFoundException, SQLException {
        try (Connection connection = MysqlConnection.getMysqlConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO khoan_thu(MaKhoanThu, TenKhoanThu, SoTien, LoaiKhoanThu) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, khoanThuModel.getMaKhoanThu());
            preparedStatement.setString(2, khoanThuModel.getTenKhoanThu());
            preparedStatement.setDouble(3, khoanThuModel.getSoTien());
            preparedStatement.setInt(4, khoanThuModel.getLoaiKhoanThu());

            preparedStatement.executeUpdate();
        }

        return true;
    }

    public boolean del(int maKhoanThu) throws ClassNotFoundException, SQLException {
        try (Connection connection = MysqlConnection.getMysqlConnection();
             PreparedStatement checkStatement = connection.prepareStatement(
                     "SELECT * FROM nop_tien WHERE MaKhoanThu = ?");
             PreparedStatement deleteStatement = connection.prepareStatement(
                     "DELETE FROM nop_tien WHERE MaKhoanThu = ?");
             PreparedStatement mainDeleteStatement = connection.prepareStatement(
                     "DELETE FROM khoan_thu WHERE MaKhoanThu = ?")) {

            checkStatement.setInt(1, maKhoanThu);
            ResultSet rs = checkStatement.executeQuery();

            while (rs.next()) {
                deleteStatement.setInt(1, maKhoanThu);
                deleteStatement.executeUpdate();
            }

            mainDeleteStatement.setInt(1, maKhoanThu);
            mainDeleteStatement.executeUpdate();
        }

        return true;
    }

    public boolean update(int maKhoanThu, String tenKhoanThu, double soTien, int loaiKhoanThu)
            throws ClassNotFoundException, SQLException {
        try (Connection connection = MysqlConnection.getMysqlConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE khoan_thu SET TenKhoanThu = ?, SoTien = ?, LoaiKhoanThu = ? WHERE MaKhoanThu = ?")) {

            preparedStatement.setString(1, tenKhoanThu);
            preparedStatement.setDouble(2, soTien);
            preparedStatement.setInt(3, loaiKhoanThu);
            preparedStatement.setInt(4, maKhoanThu);

            preparedStatement.executeUpdate();
        }

        return true;
    }

    public List<KhoanThuModel> getListKhoanThu() throws ClassNotFoundException, SQLException {
        List<KhoanThuModel> list = new ArrayList<>();

        try (Connection connection = MysqlConnection.getMysqlConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM khoan_thu");
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                KhoanThuModel khoanThuModel = new KhoanThuModel(rs.getInt("MaKhoanThu"),
                        rs.getString("TenKhoanThu"), rs.getDouble("SoTien"), rs.getInt("LoaiKhoanThu"));
                list.add(khoanThuModel);
            }
        }

        return list;
    }
    
    public double getSoTienThu() throws ClassNotFoundException, SQLException {
        List<KhoanThuModel> list = new ArrayList<>();
        double totalMoney = 0;
        try (Connection connection = MysqlConnection.getMysqlConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM khoan_thu");
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                KhoanThuModel khoanThuModel = new KhoanThuModel(rs.getInt("MaKhoanThu"),
                        rs.getString("TenKhoanThu"), rs.getDouble("SoTien"), rs.getInt("LoaiKhoanThu"));
         
                list.add(khoanThuModel);    
                totalMoney += khoanThuModel.getSoTien();
            }
        }
        
        return totalMoney;
    }
}
