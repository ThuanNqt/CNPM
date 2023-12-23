package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.HoKhauModel;
import models.KhoanThuModel;
import services.ThongKeService;

public class ThongKe3Controller implements Initializable {

    @FXML
    private TableColumn<HoKhauModel, Integer> colMaHo;
    @FXML
    private TableColumn<HoKhauModel, String> colTenChuHo;
    @FXML
    private TableColumn<HoKhauModel, Integer> colSoThanhVien;
    @FXML
    private TableColumn<HoKhauModel, String> colDiaChi;
    @FXML
    private TableView<HoKhauModel> tvThongKe;

    private ObservableList<HoKhauModel> listValueTableView;

    private KhoanThuModel selectedKhoanThu;

    // Default constructor
    public ThongKe3Controller() {}

    // Method to set the selected KhoanThuModel
    public void setkhoanThuModel(KhoanThuModel khoanThuModel) {
        this.selectedKhoanThu = khoanThuModel;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            List<HoKhauModel> paidHouseholds = getpaidHouseholds();

            listValueTableView = FXCollections.observableArrayList(paidHouseholds);
            // Initialize the TableView columns with the corresponding fields of HoKhauModel
            colMaHo.setCellValueFactory(new PropertyValueFactory<>("maHo"));
            colTenChuHo.setCellValueFactory(new PropertyValueFactory<>("tenChuHo"));
            colSoThanhVien.setCellValueFactory(new PropertyValueFactory<>("soThanhvien"));
            colDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));

            // Populate the TableView with the list of unpaid households
            tvThongKe.getItems().addAll(listValueTableView);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<HoKhauModel> getpaidHouseholds() throws SQLException, ClassNotFoundException {
        ThongKeService thongKeService = new ThongKeService();
        return thongKeService.getpaidHouseholds(selectedKhoanThu);
    }
}
