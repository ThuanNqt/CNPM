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
            String query = "INSERT INTO nhan_khau(ID, CMND, Ten, NgaySinh, SDT) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, nhanKhauModel.getId());
                preparedStatement.setString(2, nhanKhauModel.getCmnd());
                preparedStatement.setString(3, nhanKhauModel.getTen());
                preparedStatement.setDate(4, new java.sql.Date(nhanKhauModel.getNgaySinh().getTime()));
                preparedStatement.setString(5, nhanKhauModel.getSdt());
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

    public boolean update(int id, String cmnd, String ten, Date ngaySinh, String sdt)
            throws ClassNotFoundException, SQLException {
        try (Connection connection = MysqlConnection.getMysqlConnection()) {
            String query = "UPDATE nhan_khau SET CMND=?, Ten=?, NgaySinh=?, SDT=? WHERE ID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, cmnd);
                preparedStatement.setString(2, ten);
                preparedStatement.setDate(3, new java.sql.Date(ngaySinh.getTime()));
                preparedStatement.setString(4, sdt);
                preparedStatement.setInt(5, id);
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
                        NhanKhauModel nhanKhauModel = new NhanKhauModel(rs.getInt("ID"), rs.getString("CMND"),
                                rs.getString("Ten"), rs.getDate("NgaySinh"), rs.getString("SDT"));
                        list.add(nhanKhauModel);
                    }
                }
            }
        }
        return list;
    }
}
