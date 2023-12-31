package controller.hokhau;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.ChuHoModel;
import models.HoKhauModel;
import models.NhanKhauModel;
import services.ChuHoService;
import services.HoKhauService;
import services.NhanKhauService;
import services.QuanHeService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ChuyenThanhVienController implements  Initializable{
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
    private final NhanKhauService nhanKhauService;

    // Default constructor
    public ChuyenThanhVienController() {
        nhanKhauService = new NhanKhauService();
    }

    // Method to set the selected KhoanThuModel


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initializeTableColumns();
        loadNhanKhauData();
        try {
            showHoKhau();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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


    public void setSelectedHoKhau(HoKhauModel selectedHoKhau) {
        this.selectedHoKhau = selectedHoKhau;
    }

    // xu lí danh sách hộ khẩu


    @FXML
    TableColumn<HoKhauModel, String> colMaHoKhau;
    @FXML
    TableColumn<HoKhauModel, String> colMaChuHo;
    @FXML
    TableColumn<HoKhauModel, String> colSoThanhVien;
    @FXML
    TableColumn<HoKhauModel, String> colDiaChi;
    @FXML
    TableView<HoKhauModel> tvHoKhau;

    @FXML
    private TextField tfQuanHe;



    //



    ObservableList<HoKhauModel> listValueTableView1;
    private List<HoKhauModel> listHoKhau;

    // Hien thi thong tin ho khau
    public void showHoKhau() throws ClassNotFoundException, SQLException {
        listHoKhau = new HoKhauService().getListHoKhau();
        listValueTableView1 = FXCollections.observableArrayList(listHoKhau);
        List<NhanKhauModel> listNhanKhau = new NhanKhauService().getListNhanKhau();
        List<ChuHoModel> listChuHo = new ChuHoService().getListChuHo();

        if (listChuHo.isEmpty()) {
            return;
        }

        Map<Integer, Integer> mapMahoToId = new HashMap<>();
        listChuHo.forEach(chuho -> {
            mapMahoToId.put(chuho.getMaHo(), chuho.getIdChuHo());
        });

        Map<Integer, String> mapIdToTen = new HashMap<>();
        listNhanKhau.forEach(nhankhau -> {
            mapIdToTen.put(nhankhau.getId(), nhankhau.getTen());
        });

        // Thiet lap Table views
        colMaHoKhau.setCellValueFactory(new PropertyValueFactory<HoKhauModel, String>("maHo"));

        try {
            colMaChuHo.setCellValueFactory((TableColumn.CellDataFeatures<HoKhauModel, String> p) -> new ReadOnlyStringWrapper(
                    mapIdToTen.get(mapMahoToId.get(p.getValue().getMaHo())).toString()));
        }catch(Exception e) {
            e.printStackTrace();
        }
        colSoThanhVien.setCellValueFactory(new PropertyValueFactory<HoKhauModel, String>("soThanhvien"));
        colDiaChi.setCellValueFactory(new PropertyValueFactory<HoKhauModel, String>("diaChi"));




        tvHoKhau.setItems(listValueTableView1);

    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    @FXML
    void chuyenTV(ActionEvent event) throws SQLException, ClassNotFoundException {
        NhanKhauModel nhanKhauModel  = tvThongKe.getSelectionModel().getSelectedItem();
        HoKhauModel khauModel = tvHoKhau.getSelectionModel().getSelectedItem();
        if(khauModel == null){
            showAlert("chọn hộ khẩu muốn chuyển tới");
            return;
        }
        if(nhanKhauModel == null){
            showAlert("chọn nhân khẩu bạn muốn chuyển");

        }else{
            // kiểm tra điều kiện chủ hộ
            List<ChuHoModel> listChuHo = new ChuHoService().getListChuHo();
            for(ChuHoModel chuho : listChuHo) {
                if(chuho.getIdChuHo() == nhanKhauModel.getId()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Bạn không thể lựa chọn nhân khẩu này!", ButtonType.OK);
                    alert.setHeaderText("Nhân khẩu này là một chủ hộ!");
                    alert.showAndWait();
                    return;
                }
            }
        }
        if(validateQuanHe()){
            QuanHeService quanHeService = new QuanHeService();
            if(selectedHoKhau.getMaHo() == khauModel.getMaHo()){
                showAlert("Hộ khẩu mới không thể trùng hộ khẩu cũ");
            }else{
                String quanHe = tfQuanHe.getText().trim();
                quanHeService.changeRelationship(selectedHoKhau.getMaHo(),khauModel.getMaHo(),nhanKhauModel.getId(),quanHe);
                closeStage(event);
            }
        }
    }
    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    private boolean validateQuanHe(){
        if(tfQuanHe.getText().trim().length() == 0){
            showAlert("Hãy nhập vào quan hệ hợp lệ !");
            return false;
        }
        return true;
    }

}
