package services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.HoKhauModel;
import models.KhoanThuModel;

public class ThongKeService {

	public List<HoKhauModel> getUnpaidHouseholds(KhoanThuModel khoanThuModel) throws ClassNotFoundException, SQLException {
	    List<HoKhauModel> list = new ArrayList<>();
	    try (Connection connection = MysqlConnection.getMysqlConnection()) {
	        String query = "SELECT hk.MaHo, nk.Ten, hk.SoThanhVien, hk.DiaChi "
	                + "FROM ho_khau hk "
	                + "JOIN chu_ho ch ON hk.MaHo = ch.MaHo "
	                + "JOIN nhan_khau nk ON ch.IDChuHo = nk.ID "
	                + "LEFT JOIN nop_tien nt ON nk.ID = nt.IDNopTien AND nt.MaKhoanThu = ? "
	                + "WHERE nt.IDNopTien IS NULL";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	            preparedStatement.setInt(1,khoanThuModel.getMaKhoanThu());
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                while (resultSet.next()) {
	                    HoKhauModel hoKhau = new HoKhauModel();
	                    hoKhau.setMaHo(resultSet.getInt("MaHo"));
	                    hoKhau.setTenChuHo(resultSet.getString("Ten"));
	                    hoKhau.setSoThanhvien(resultSet.getInt("SoThanhVien"));
	                    hoKhau.setDiaChi(resultSet.getString("DiaChi"));
	                    list.add(hoKhau);
	                }
	            }
	        }
	    }
	    return list;
	}



}