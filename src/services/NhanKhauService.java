package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.NhanKhauModel;

public class NhanKhauService {

    // checked
    public boolean add(NhanKhauModel nhanKhauModel) throws ClassNotFoundException, SQLException {
        try (Connection connection = MysqlConnection.getMysqlConnection()) {
            String query = "INSERT INTO nhan_khau(ID, CCCD, Ten, GioiTinh, NgaySinh, SDT) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, nhanKhauModel.getId());
                preparedStatement.setString(2, nhanKhauModel.getCccd());
                preparedStatement.setString(3, nhanKhauModel.getTen());
                preparedStatement.setString(4, nhanKhauModel.getGioiTinh());
                preparedStatement.setDate(5, new java.sql.Date(nhanKhauModel.getNgaySinh().getTime()));
                preparedStatement.setString(6, nhanKhauModel.getSdt());
                preparedStatement.executeUpdate();
            }
        }
        return true;
    }

    // checked
    public boolean del(int ID) throws ClassNotFoundException, SQLException {
        try (Connection connection = MysqlConnection.getMysqlConnection()) {
            String query = "SELECT * FROM nop_tien WHERE IDNopTien=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, ID);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        query = "DELETE FROM nop_tien WHERE IDNopTien=?";
                        try (PreparedStatement deleteStatement = connection.prepareStatement(query)) {
                            deleteStatement.setInt(1, ID);
                            deleteStatement.executeUpdate();
                        }
                    }
                }
            }

            query = "SELECT * FROM chu_ho WHERE chu_ho.IDChuHo=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, ID);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        query = "DELETE FROM chu_ho WHERE IDChuHo=?";
                        try (PreparedStatement deleteStatement = connection.prepareStatement(query)) {
                            deleteStatement.setInt(1, ID);
                            deleteStatement.executeUpdate();
                        }
                    }
                }
            }

            query = "SELECT * FROM quan_he WHERE quan_he.IDThanhVien=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, ID);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        query = "DELETE FROM quan_he WHERE IDThanhVien=?";
                        try (PreparedStatement deleteStatement = connection.prepareStatement(query)) {
                            deleteStatement.setInt(1, ID);
                            deleteStatement.executeUpdate();
                        }
                    }
                }
            }

            query = "DELETE FROM nhan_khau WHERE ID=?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(query)) {
                deleteStatement.setInt(1, ID);
                deleteStatement.executeUpdate();
            }
        }
        return true;
    }

    public boolean update(int id, String cccd, String ten,String gioiTinh, Date ngaySinh, String sdt)
            throws ClassNotFoundException, SQLException {
        try (Connection connection = MysqlConnection.getMysqlConnection()) {
            String query = "UPDATE nhan_khau SET CCCD=?, Ten=?, GioiTinh = ?, NgaySinh=?, SDT=? WHERE ID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, cccd);
                preparedStatement.setString(2, ten);
                preparedStatement.setString(3, gioiTinh);
                preparedStatement.setDate(4, new java.sql.Date(ngaySinh.getTime()));
                preparedStatement.setString(5, sdt);
                preparedStatement.setInt(6, id);
                preparedStatement.executeUpdate();
            }
        }
        return true;
    }

    // checked
    public List<NhanKhauModel> getListNhanKhau() throws ClassNotFoundException, SQLException {
        List<NhanKhauModel> list = new ArrayList<>();

        try (Connection connection = MysqlConnection.getMysqlConnection()) {
            String query = "SELECT * FROM nhan_khau";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        NhanKhauModel nhanKhauModel = new NhanKhauModel(rs.getInt("ID"), rs.getString("CCCD"),
                                rs.getString("Ten"), rs.getString("GioiTinh"), rs.getDate("NgaySinh"), rs.getString("SDT"));
                        list.add(nhanKhauModel);
                    }
                }
            }
        }
        return list;
    }
    
    public List<NhanKhauModel> getListNhanKhauNam() throws ClassNotFoundException, SQLException {
        List<NhanKhauModel> list = new ArrayList<>();

        try (Connection connection = MysqlConnection.getMysqlConnection()) {
            String query = "SELECT * FROM nhan_khau WHERE GioiTinh = 'Nam'";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        NhanKhauModel nhanKhauModel = new NhanKhauModel(rs.getInt("ID"), rs.getString("CCCD"),
                                rs.getString("Ten"), rs.getString("GioiTinh"), rs.getDate("NgaySinh"), rs.getString("SDT"));
                        list.add(nhanKhauModel);
                    }
                }
            }
        }
        return list;
    }
    
    public List<NhanKhauModel> getListNhanKhauNu() throws ClassNotFoundException, SQLException {
        List<NhanKhauModel> list = new ArrayList<>();

        try (Connection connection = MysqlConnection.getMysqlConnection()) {
            String query = "SELECT * FROM nhan_khau WHERE GioiTinh = 'Nữ'";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        NhanKhauModel nhanKhauModel = new NhanKhauModel(rs.getInt("ID"), rs.getString("CCCD"),
                                rs.getString("Ten"), rs.getString("GioiTinh"), rs.getDate("NgaySinh"), rs.getString("SDT"));
                        list.add(nhanKhauModel);
                    }
                }
            }
        }
        return list;
    }
}
