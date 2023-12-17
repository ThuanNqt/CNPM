package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.ChuHoModel;

public class ChuHoService {

    public boolean add(ChuHoModel chuHoModel) {
        try (Connection connection = MysqlConnection.getMysqlConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO chu_ho(MaHo, IDChuHo) VALUES (?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, chuHoModel.getMaHo());
            preparedStatement.setInt(2, chuHoModel.getIdChuHo());
            preparedStatement.executeUpdate();

            // Additional logic if needed

            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Log or handle the exception appropriately
            return false;
        }
    }

    public boolean delete(int maHo, int idChuHo) {
        String sql = "DELETE FROM chu_ho WHERE MaHo = ? AND IDChuHo = ?";
        try (Connection connection = MysqlConnection.getMysqlConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, maHo);
            preparedStatement.setInt(2, idChuHo);
            preparedStatement.executeUpdate();

            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Log or handle the exception appropriately
            return false;
        }
    }

    public List<ChuHoModel> getListChuHo() {
        List<ChuHoModel> list = new ArrayList<>();
        String query = "SELECT * FROM chu_ho";

        try (Connection connection = MysqlConnection.getMysqlConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                ChuHoModel chuHoModel = new ChuHoModel();
                chuHoModel.setMaHo(rs.getInt("MaHo"));
                chuHoModel.setIdChuHo(rs.getInt("IDChuHo"));
                list.add(chuHoModel);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Log or handle the exception appropriately
        }

        return list;
    }
}
