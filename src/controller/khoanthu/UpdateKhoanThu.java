package controller.khoanthu;

import java.sql.SQLException;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.KhoanThuModel;
import services.KhoanThuService;

public class UpdateKhoanThu {
    @FXML
    private TextField tfMaKhoanThu;
    @FXML
    private TextField tfTenKhoanThu;
    @FXML
    private TextField tfLoaiKhoanThu;
    @FXML
    private TextField tfSoTien;
    @FXML
    private TextField tfHinhThucThu;

    private KhoanThuModel khoanThuModel;

    public void setKhoanThuModel(KhoanThuModel khoanThuModel) {
        this.khoanThuModel = khoanThuModel;

        // Set data from KhoanThuModel to respective fields
        tfTenKhoanThu.setText(khoanThuModel.getTenKhoanThu());
        tfMaKhoanThu.setText(String.valueOf(khoanThuModel.getMaKhoanThu()));
        tfLoaiKhoanThu.setText((khoanThuModel.getLoaiKhoanThu() == 1) ? "Bắt buộc" : "Tự nguyện");
        tfSoTien.setText(String.valueOf(khoanThuModel.getSoTien()));
        tfHinhThucThu.setText(khoanThuModel.getHinhThucThu());
    }

    public void updateKhoanThu(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (!isValidInput()) {
            return;
        }

        // Extract information after validation
        int maKhoanThuInt = khoanThuModel.getMaKhoanThu();
        String tenKhoanThuString = tfTenKhoanThu.getText();
        int loaiKhoanThuInt = khoanThuModel.getLoaiKhoanThu();
        double soTienDouble = Double.parseDouble(tfSoTien.getText());
        String hinhThucThuString = tfHinhThucThu.getText();

        // Update data
        new KhoanThuService().update(maKhoanThuInt, tenKhoanThuString, soTienDouble, loaiKhoanThuInt,hinhThucThuString);

        // Close the window after successful update
        closeWindow(event);
    }

    private boolean isValidInput() {
        // Validate the name input
        if (tfTenKhoanThu.getText().length() < 1 || tfTenKhoanThu.getText().length() > 50) {
            showAlert("Hãy nhập vào tên khoản thu hợp lệ!");
            return false;
        }

        // Validate the amount input
        Pattern amountPattern = Pattern.compile("\\d{1,11}");
        if (!amountPattern.matcher(tfSoTien.getText()).matches()) {
            showAlert("Hãy nhập vào số tiền hợp lệ!");
            return false;
        }

        return true;
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
}
