package controller.nhankhau;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.NhanKhauModel;
import services.NhanKhauService;

public class UpdateNhanKhau {
    private int maNhanKhau;

    @FXML
    private TextField tfMaNhanKhau;
    @FXML
    private DatePicker dpNgaySinh;
    @FXML
    private TextField tfTenNhanKhau;
    @FXML
    private TextField tfSoDienThoai;
    @FXML
    private TextField tfSoCMND;

    private NhanKhauModel nhanKhauModel;

    public NhanKhauModel getNhanKhauModel() {
        return nhanKhauModel;
    }

    public void setNhanKhauModel(NhanKhauModel nhanKhauModel) throws ClassNotFoundException, SQLException {
        // Set the NhanKhauModel and populate the form fields
        this.nhanKhauModel = nhanKhauModel;

        // Set the ID field as non-editable
        maNhanKhau = nhanKhauModel.getId();
        tfMaNhanKhau.setText(Integer.toString(maNhanKhau));
        tfMaNhanKhau.setEditable(false);

        java.util.Date ngaySinhUtilDate = new java.util.Date(nhanKhauModel.getNgaySinh().getTime());

        // Convert java.util.Date to LocalDate
        LocalDate localDate = ngaySinhUtilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dpNgaySinh.setValue(localDate);
        tfTenNhanKhau.setText(nhanKhauModel.getTen());
        tfSoDienThoai.setText(nhanKhauModel.getSdt());
        tfSoCMND.setText(nhanKhauModel.getCmnd());
    }

    @FXML
    public void updateNhanKhau(ActionEvent event) throws ClassNotFoundException, SQLException {
        // Validate form fields
        if (!validateFields()) {
            return; // Validation failed
        }

        // Convert LocalDate to java.sql.Date directly
        Date ngaySinhDate = java.sql.Date.valueOf(dpNgaySinh.getValue());

        // Get values from form fields
        String tenString = tfTenNhanKhau.getText();
        String cmndString = tfSoCMND.getText();
        String sdtString = tfSoDienThoai.getText();

        // Update the existing NhanKhau
        new NhanKhauService().update(maNhanKhau, cmndString, tenString, ngaySinhDate, sdtString);

        // Display success message
        showAlert("Cập nhật thông tin nhân khẩu thành công!");

        // Close the stage after update
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private boolean validateFields() {
        // Validate form fields here
        // Return true if all fields are valid, false otherwise

        // Example validation for ID
        Pattern idPattern = Pattern.compile("\\d{1,11}");
        if (!idPattern.matcher(tfMaNhanKhau.getText()).matches()) {
            showAlert("Hãy nhập vào mã nhân khẩu hợp lệ!");
            return false;
        }

        // Example validation for name
        if (tfTenNhanKhau.getText().length() >= 50 || tfTenNhanKhau.getText().length() <= 1) {
            showAlert("Hãy nhập vào tên hợp lệ!");
            return false;
        }

        // Example validation for CMND
        Pattern cmndPattern = Pattern.compile("\\d{1,20}");
        if (!cmndPattern.matcher(tfSoCMND.getText()).matches()) {
            showAlert("Hãy nhập vào CMND hợp lệ!");
            return false;
        }

        // Example validation for phone number
        Pattern phonePattern = Pattern.compile("\\d{1,15}");
        if (!phonePattern.matcher(tfSoDienThoai.getText()).matches()) {
            showAlert("Hãy nhập vào số điện thoại hợp lệ!");
            return false;
        }

        // Other validations...

        return true;
    }


    private void showAlert(String message) {
        // Display alert messages
        Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
