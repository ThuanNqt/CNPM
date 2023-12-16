package controller.hokhau;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.ChuHoModel;
import models.HoKhauModel;
import models.NhanKhauModel;
import services.ChuHoService;
import services.HoKhauService;
import services.NhanKhauService;

public class UpdateHoKhau {

    @FXML
    private TextField tfSoThanhVien;
    @FXML
    private TextField tfSDT;
    @FXML
    private TextField tfTenChuHo;
    @FXML
    private TextField tfDiaChi;
    @FXML
    private TextField tfMaHo;

    private HoKhauModel hoKhauModel;
    private Map<Integer, String> mapIDToTenNhanKhau = new TreeMap<>();
    private Map<Integer, String> mapIDToSDT = new TreeMap<>();

    public void setHoKhauModel(HoKhauModel hoKhauModel) throws ClassNotFoundException, SQLException {
        this.hoKhauModel = hoKhauModel;
        initializeDropdownData();
        populateFields();
    }

    private void initializeDropdownData() throws ClassNotFoundException, SQLException {
        Map<Integer, Integer> mapMahoToID = new TreeMap<>();
        List<ChuHoModel> listChuHo = new ChuHoService().getListChuHo();
        listChuHo.forEach(chuho -> mapMahoToID.put(chuho.getMaHo(), chuho.getIdChuHo()));

        List<NhanKhauModel> listNhanKhau = new NhanKhauService().getListNhanKhau();
        listNhanKhau.forEach(nhankhau -> {
            mapIDToTenNhanKhau.put(nhankhau.getId(), nhankhau.getTen());
            mapIDToSDT.put(nhankhau.getId(), nhankhau.getSdt());
        });
    }

    private void populateFields() {
        tfDiaChi.setText(hoKhauModel.getDiaChi());
        tfMaHo.setText(String.valueOf(hoKhauModel.getMaHo()));
        tfSoThanhVien.setText(String.valueOf(hoKhauModel.getSoThanhvien()));
        int idChuHo = getIdChuHoByMaHo(hoKhauModel.getMaHo());
        tfTenChuHo.setText(mapIDToTenNhanKhau.get(idChuHo));
        tfSDT.setText(mapIDToSDT.get(idChuHo));
    }

    private int getIdChuHoByMaHo(int maHo) {
        for (ChuHoModel chuHo : new ChuHoService().getListChuHo()) {
            if (chuHo.getMaHo() == maHo) {
                return chuHo.getIdChuHo();
            }
        }
        return -1; // Handle not found case
    }

    @FXML
    public void updateHoKhau(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (!isAddressValid() || !isPhoneNumberValid()) {
            return;
        }

        String diaChiString = tfDiaChi.getText();
        String sdtString = tfSDT.getText();

        new HoKhauService().update(hoKhauModel.getMaHo(), diaChiString, sdtString);
        closeCurrentStage(event);
    }

    private boolean isAddressValid() {
        if (tfDiaChi.getText().length() < 2 || tfDiaChi.getText().length() >= 200) {
            showAlert("Hãy nhập vào địa chỉ hợp lệ!");
            return false;
        }
        return true;
    }

    private boolean isPhoneNumberValid() {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        if (!pattern.matcher(tfSDT.getText()).matches()) {
            showAlert("Hãy nhập vào số điện thoại hợp lệ!");
            return false;
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.WARNING, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void closeCurrentStage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
