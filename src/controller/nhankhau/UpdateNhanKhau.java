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
    private TextField tfGioiTinh;
    @FXML
    private TextField tfSoDienThoai;
    @FXML
    private TextField tfSoCCCD;
    @FXML
    private TextField tfDanToc;
    @FXML
    private TextField tfNoiThuongTru;
    @FXML
    private TextField tfNgheNghiep;
    @FXML
    private TextField tfNoiLamViec;
    @FXML
    private TextField tfNguyenQuan;
    @FXML
    private TextField tfNoiCap;
    @FXML
    private TextField tfBietDanh;

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
        tfGioiTinh.setText(nhanKhauModel.getGioiTinh());
        tfSoDienThoai.setText(nhanKhauModel.getSdt());
        tfSoCCCD.setText(nhanKhauModel.getCccd());
        tfBietDanh.setText(nhanKhauModel.getBietDanh());
        tfDanToc.setText(nhanKhauModel.getDanToc());
        tfNoiThuongTru.setText(nhanKhauModel.getNoiThuongTru());
        tfNgheNghiep.setText(nhanKhauModel.getNgheNghiep());
        tfNoiLamViec.setText(nhanKhauModel.getNoiLamViec());
        tfNguyenQuan.setText(nhanKhauModel.getNguyenQuan());
        tfNoiCap.setText(nhanKhauModel.getNoiCapCCCD());
        
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
        String cccdString = tfSoCCCD.getText();
        String gioiTinhString = tfGioiTinh.getText();
        String sdtString = tfSoDienThoai.getText();
        String bietdanhString = tfBietDanh.getText();
        String nguyenquanString = tfNguyenQuan.getText();
        String dantocString = tfDanToc.getText();
        String noithuongtruString = tfNoiThuongTru.getText();
        String nghenghiepString = tfNgheNghiep.getText();
        String noilamviecString = tfNoiLamViec.getText();
        String noicapString = tfNoiCap.getText();
        

        // Update the existing NhanKhau
        NhanKhauModel nhankhauModel = new NhanKhauModel(maNhanKhau, cccdString, tenString, gioiTinhString, ngaySinhDate, sdtString,bietdanhString,dantocString,noithuongtruString,nghenghiepString,noilamviecString,nguyenquanString,noicapString);
        new NhanKhauService().update(nhankhauModel);

        // Display success message
        showAlert("Cập nhật thông tin nhân khẩu thành công!");

        // Close the stage after update
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private boolean validateFields() {
        // Example validation for ID
        Pattern idPattern = Pattern.compile("\\d{1,11}");
        if (!idPattern.matcher(tfMaNhanKhau.getText()).matches()) {
            showAlert("Mã nhân khẩu không hợp lệ!");
            return false;
        }

        // Example validation for name
        if (tfTenNhanKhau.getText().length() >= 50 || tfTenNhanKhau.getText().length() <= 1) {
            showAlert("Tên không hợp lệ!");
            return false;
        }

        // Example validation for gioiTinh
        if (tfGioiTinh.getText().length() >= 4 || tfGioiTinh.getText().length() <= 1) {
            showAlert("Giới tính không hợp lệ!");
            return false;
        }
        
        // Example validation for CCCD
        Pattern cccdPattern = Pattern.compile("\\d{1,20}");
        if (!cccdPattern.matcher(tfSoCCCD.getText()).matches()) {
            showAlert("CCCD không hợp lệ!");
            return false;
        }

        // Example validation for phone number
        Pattern phonePattern = Pattern.compile("\\d{1,15}");
        if (!phonePattern.matcher(tfSoDienThoai.getText()).matches()) {
            showAlert("Số điện thoại không hợp lệ!");
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
