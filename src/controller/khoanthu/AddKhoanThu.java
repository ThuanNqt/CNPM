package controller.khoanthu;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.KhoanThuModel;
import services.KhoanThuService;

public class AddKhoanThu implements Initializable {
    @FXML
    private TextField tfMaKhoanThu;
    @FXML
    private TextField tfTenKhoanThu;
    @FXML
    private ComboBox<String> cbLoaiKhoanThu;
    @FXML
    private TextField tfSoTien;

    private final KhoanThuService khoanThuService = new KhoanThuService();

    public void addKhoanThu(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (validateInput()) {
            saveKhoanThu();
            closeWindow(event);
        }
    }

    private boolean validateInput() throws ClassNotFoundException, SQLException {
        return validateMaKhoanThu() && validateDuplicateMaKhoanThu() && validateTenKhoanThu() && validateSoTien();
    }

    private boolean validateMaKhoanThu() {
        String maKhoanThuText = tfMaKhoanThu.getText();
        Pattern pattern = Pattern.compile("\\d{1,11}");
        if (!pattern.matcher(maKhoanThuText).matches()) {
            showAlert("Hãy nhập vào mã khoản thu hợp lệ!");
            return false;
        }
        return true;
    }

    private boolean validateDuplicateMaKhoanThu() throws ClassNotFoundException, SQLException {
        int maKhoanThu = Integer.parseInt(tfMaKhoanThu.getText());
        List<KhoanThuModel> existingKhoanThuList = khoanThuService.getListKhoanThu();
        if (existingKhoanThuList.stream().anyMatch(k -> k.getMaKhoanThu() == maKhoanThu)) {
            showAlert("Mã khoản thu bị trùng!");
            return false;
        }
        return true;
    }

    private boolean validateTenKhoanThu() {
        String tenKhoanThu = tfTenKhoanThu.getText();
        if (tenKhoanThu.length() >= 50 || tenKhoanThu.length() <= 1) {
            showAlert("Hãy nhập vào tên khoản thu hợp lệ!");
            return false;
        }
        return true;
    }

    private boolean validateSoTien() {
        String soTienText = tfSoTien.getText();
        Pattern pattern = Pattern.compile("^[1-9]\\d*(\\.\\d+)?$");
        if (!pattern.matcher(soTienText).matches()) {
            showAlert("Hãy nhập vào số tiền hợp lệ!");
            return false;
        }
        return true;
    }

    private void saveKhoanThu() throws ClassNotFoundException, SQLException {
        SingleSelectionModel<String> loaiKhoanThuSelection = cbLoaiKhoanThu.getSelectionModel();
        String loaiKhoanThu_tmp = loaiKhoanThuSelection.getSelectedItem();

        int maKhoanThu = Integer.parseInt(tfMaKhoanThu.getText());
        String tenKhoanThu = tfTenKhoanThu.getText();
        double soTien = Double.parseDouble(tfSoTien.getText());
        int loaiKhoanThu = loaiKhoanThu_tmp.equals("Bắt buộc") ? 1 : 0;

        khoanThuService.add(new KhoanThuModel(maKhoanThu, tenKhoanThu, soTien, loaiKhoanThu));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.WARNING, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initializeLoaiKhoanThuComboBox();
    }

    private void initializeLoaiKhoanThuComboBox() {
        ObservableList<String> listComboBox = FXCollections.observableArrayList("Tự nguyện", "Bắt buộc");
        cbLoaiKhoanThu.setValue("Bắt buộc");
        cbLoaiKhoanThu.setItems(listComboBox);
    }
}
