package controller.hokhau;

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
import models.NhanKhauModel;
import services.NhanKhauService;

public class ChiTietHoKhauController implements Initializable {

    @FXML
    private TableColumn<NhanKhauModel, Integer> colId;
    @FXML
    private TableColumn<NhanKhauModel, String> colTen;
    @FXML
    private TableColumn<NhanKhauModel, Integer> colQuanHe;
    @FXML
    private TableView<NhanKhauModel> tvThongKe;

    private ObservableList<NhanKhauModel> listValueTableView;
    private HoKhauModel selectedHoKhau;
    private NhanKhauService nhanKhauService;

    // Default constructor
    public ChiTietHoKhauController() {
        nhanKhauService = new NhanKhauService();
    }

    // Method to set the selected KhoanThuModel
    public void setHoKhauModel(HoKhauModel hoKhauModel) {
        this.selectedHoKhau = hoKhauModel;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initializeTableColumns();
        loadNhanKhauData();
    }

    private void initializeTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTen.setCellValueFactory(new PropertyValueFactory<>("ten"));
        colQuanHe.setCellValueFactory(new PropertyValueFactory<>("quanHeChuHo"));
    }

    private void loadNhanKhauData() {
        try {
            List<NhanKhauModel> nhankhauModel = getNhanKhauByHoKhau();
            listValueTableView = FXCollections.observableArrayList(nhankhauModel);
            tvThongKe.setItems(listValueTableView);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<NhanKhauModel> getNhanKhauByHoKhau() throws SQLException, ClassNotFoundException {
        return nhanKhauService.getNhanKhauByHoKhau(selectedHoKhau);
    }
}
