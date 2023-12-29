package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.HoKhauModel;
import models.NhanKhauModel;

public class NhanKhauService {

	public boolean add(NhanKhauModel nhanKhauModel) throws ClassNotFoundException, SQLException {
	    try (Connection connection = MysqlConnection.getMysqlConnection()) {
	        String query = "INSERT INTO nhan_khau(ID, CCCD, Ten, GioiTinh, NgaySinh, SDT, BietDanh, DanToc, NoiThuongTru, NgheNghiep, NoiLamViec, NguyenQuan, NoiCapCCCD) " +
	                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	            preparedStatement.setInt(1, nhanKhauModel.getId());
	            preparedStatement.setString(2, nhanKhauModel.getCccd());
	            preparedStatement.setString(3, nhanKhauModel.getTen());
	            preparedStatement.setString(4, nhanKhauModel.getGioiTinh());
	            preparedStatement.setDate(5, new java.sql.Date(nhanKhauModel.getNgaySinh().getTime()));
	            preparedStatement.setString(6, nhanKhauModel.getSdt());
	            preparedStatement.setString(7, nhanKhauModel.getBietDanh());
	            preparedStatement.setString(8, nhanKhauModel.getDanToc());
	            preparedStatement.setString(9, nhanKhauModel.getNoiThuongTru());
	            preparedStatement.setString(10, nhanKhauModel.getNgheNghiep());
	            preparedStatement.setString(11, nhanKhauModel.getNoiLamViec());
	            preparedStatement.setString(12, nhanKhauModel.getNguyenQuan());
	            preparedStatement.setString(13, nhanKhauModel.getNoiCapCCCD());

	            preparedStatement.executeUpdate();
	        }
	    }
	    return true;
	}


    
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

    public boolean update(NhanKhauModel nhanKhauModel) throws ClassNotFoundException, SQLException {
        try (Connection connection = MysqlConnection.getMysqlConnection()) {
            String query = "UPDATE nhan_khau SET CCCD=?, Ten=?, GioiTinh=?, NgaySinh=?, SDT=?, BietDanh=?, DanToc=?, NoiThuongTru=?, NgheNghiep=?, NoiLamViec=?, NguyenQuan=?, NoiCapCCCD=? WHERE ID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, nhanKhauModel.getCccd());
                preparedStatement.setString(2, nhanKhauModel.getTen());
                preparedStatement.setString(3, nhanKhauModel.getGioiTinh());
                preparedStatement.setDate(4, new java.sql.Date(nhanKhauModel.getNgaySinh().getTime()));
                preparedStatement.setString(5, nhanKhauModel.getSdt());
                preparedStatement.setString(6, nhanKhauModel.getBietDanh());
                preparedStatement.setString(7, nhanKhauModel.getDanToc());
                preparedStatement.setString(8, nhanKhauModel.getNoiThuongTru());
                preparedStatement.setString(9, nhanKhauModel.getNgheNghiep());
                preparedStatement.setString(10, nhanKhauModel.getNoiLamViec());
                preparedStatement.setString(11, nhanKhauModel.getNguyenQuan());
                preparedStatement.setString(12, nhanKhauModel.getNoiCapCCCD());
                preparedStatement.setInt(13, nhanKhauModel.getId());

                preparedStatement.executeUpdate();
            }
        }
        return true;
    }


    public List<NhanKhauModel> getListNhanKhau() throws ClassNotFoundException, SQLException {
        List<NhanKhauModel> list = new ArrayList<>();

        try (Connection connection = MysqlConnection.getMysqlConnection()) {
            String query = "SELECT * FROM nhan_khau";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        NhanKhauModel nhanKhauModel = new NhanKhauModel();
                        nhanKhauModel.setId(rs.getInt("ID"));
                        nhanKhauModel.setCccd(rs.getString("CCCD"));
                        nhanKhauModel.setTen(rs.getString("Ten"));
                        nhanKhauModel.setGioiTinh(rs.getString("GioiTinh"));
                        nhanKhauModel.setNgaySinh(rs.getDate("NgaySinh"));
                        nhanKhauModel.setSdt(rs.getString("SDT"));
                        nhanKhauModel.setBietDanh(rs.getString("BietDanh"));
                        nhanKhauModel.setDanToc(rs.getString("DanToc"));
                        nhanKhauModel.setNoiThuongTru(rs.getString("NoiThuongTru"));
                        nhanKhauModel.setNgheNghiep(rs.getString("NgheNghiep"));
                        nhanKhauModel.setNoiLamViec(rs.getString("NoiLamViec"));
                        nhanKhauModel.setNguyenQuan(rs.getString("NguyenQuan"));
                        nhanKhauModel.setNoiCapCCCD(rs.getString("NoiCapCCCD"));

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
                        NhanKhauModel nhanKhauModel = new NhanKhauModel();
                        nhanKhauModel.setId(rs.getInt("ID"));
                        nhanKhauModel.setCccd(rs.getString("CCCD"));
                        nhanKhauModel.setTen(rs.getString("Ten"));
                        nhanKhauModel.setGioiTinh(rs.getString("GioiTinh"));
                        nhanKhauModel.setNgaySinh(rs.getDate("NgaySinh"));
                        nhanKhauModel.setSdt(rs.getString("SDT"));
                        nhanKhauModel.setBietDanh(rs.getString("BietDanh"));
                        nhanKhauModel.setDanToc(rs.getString("DanToc"));
                        nhanKhauModel.setNoiThuongTru(rs.getString("NoiThuongTru"));
                        nhanKhauModel.setNgheNghiep(rs.getString("NgheNghiep"));
                        nhanKhauModel.setNoiLamViec(rs.getString("NoiLamViec"));
                        nhanKhauModel.setNguyenQuan(rs.getString("NguyenQuan"));
                        nhanKhauModel.setNoiCapCCCD(rs.getString("NoiCapCCCD"));

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
                        NhanKhauModel nhanKhauModel = new NhanKhauModel();
                        nhanKhauModel.setId(rs.getInt("ID"));
                        nhanKhauModel.setCccd(rs.getString("CCCD"));
                        nhanKhauModel.setTen(rs.getString("Ten"));
                        nhanKhauModel.setGioiTinh(rs.getString("GioiTinh"));
                        nhanKhauModel.setNgaySinh(rs.getDate("NgaySinh"));
                        nhanKhauModel.setSdt(rs.getString("SDT"));
                        nhanKhauModel.setBietDanh(rs.getString("BietDanh"));
                        nhanKhauModel.setDanToc(rs.getString("DanToc"));
                        nhanKhauModel.setNoiThuongTru(rs.getString("NoiThuongTru"));
                        nhanKhauModel.setNgheNghiep(rs.getString("NgheNghiep"));
                        nhanKhauModel.setNoiLamViec(rs.getString("NoiLamViec"));
                        nhanKhauModel.setNguyenQuan(rs.getString("NguyenQuan"));
                        nhanKhauModel.setNoiCapCCCD(rs.getString("NoiCapCCCD"));

                        list.add(nhanKhauModel);
                    }
                }
            }
        }
        return list;
    }
    public boolean addnk(NhanKhauModel nhanKhauModel) throws ClassNotFoundException, SQLException {
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
    public List<NhanKhauModel> getNhanKhauByHoKhau(HoKhauModel hoKhauModel) throws ClassNotFoundException {
        List<NhanKhauModel> nhanKhauList = new ArrayList<>();

        try (Connection connection = MysqlConnection.getMysqlConnection()) {
            String sql = "SELECT nk.ID, nk.Ten, qh.QuanHe FROM nhan_khau nk " +
                         "JOIN quan_he qh ON nk.ID = qh.IDThanhVien " +
                         "WHERE qh.MaHo = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, hoKhauModel.getMaHo());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("ID");
                        String ten = resultSet.getString("Ten");
                        String quanHeChuHo = resultSet.getString("QuanHe");

                        
                        NhanKhauModel nhanKhauModel = new NhanKhauModel();
                        nhanKhauModel.setId(id);
                        nhanKhauModel.setTen(ten);
                        nhanKhauModel.setQuanHeChuHo(quanHeChuHo);

                        nhanKhauList.add(nhanKhauModel);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nhanKhauList;
    }
    


}
