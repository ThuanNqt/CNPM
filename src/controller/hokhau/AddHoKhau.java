package controller.hokhau;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.ChuHoModel;
import models.HoKhauModel;
import models.NhanKhauModel;
import models.QuanHeModel;
import services.ChuHoService;
import services.HoKhauService;
import services.NhanKhauService;
import services.QuanHeService;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class AddHoKhau {

    @FXML private TextField tfMaHoKhau;
    @FXML private TextField tfDiaChi;
    @FXML private TextField tfMaChuHo;
    @FXML private TextField tfTenChuHo;
    @FXML private TextField tfGioiTinhChuHo;
    @FXML private DatePicker dpNgaySinh;
    @FXML private TextField tfCMND;
    @FXML private TextField tfSoDienThoai;

    @FXML
    public void addHoKhau(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (validateInput()) {
            int maHo = Integer.parseInt(tfMaHoKhau.getText());
            String diaChi = tfDiaChi.getText();
            int maChuHo = Integer.parseInt(tfMaChuHo.getText());
            String tenChuHo = tfTenChuHo.getText();
            String gioiTinhChuHo = tfGioiTinhChuHo.getText();
            Date ngaySinhDate = java.sql.Date.valueOf(dpNgaySinh.getValue());
            String cccdChuHo = tfCMND.getText();
            String sdtChuHo = tfSoDienThoai.getText();

            HoKhauModel hoKhauModel = new HoKhauModel(maHo, 0, diaChi);
            NhanKhauModel nhanKhauModel = new NhanKhauModel(maChuHo, cccdChuHo, tenChuHo,gioiTinhChuHo, ngaySinhDate, sdtChuHo);

            addHoKhauToDatabase(hoKhauModel, nhanKhauModel);
            closeStage(event);
        }
    }

    private boolean validateInput() throws ClassNotFoundException, SQLException {
        return validateMaHo() && validateDiaChi() && validateMaChuHo() && validateTenChuHo()
                && validateCMND() && validateSoDienThoai();
    }

    private boolean validateMaHo() throws ClassNotFoundException, SQLException {
        Pattern pattern = Pattern.compile("\\d{1,11}");
        if (!pattern.matcher(tfMaHoKhau.getText()).matches()) {
            showAlert("Hãy nhập vào mã hộ khẩu hợp lệ!");
            return false;
        }

        List<HoKhauModel> listHoKhauModels = new HoKhauService().getListHoKhau();
        for (HoKhauModel hokhau : listHoKhauModels) {
            if (hokhau.getMaHo() == Integer.parseInt(tfMaHoKhau.getText())) {
                showAlert("Mã hộ khẩu bị trùng!");
                return false;
            }
        }
        return true;
    }

    private boolean validateDiaChi() {
        if (tfDiaChi.getText().length() >= 50 || tfDiaChi.getText().length() <= 1) {
            showAlert("Hãy nhập vào địa chỉ hợp lệ!");
            return false;
        }
        return true;
    }

    private boolean validateMaChuHo() throws ClassNotFoundException, SQLException {
        Pattern pattern = Pattern.compile("\\d{1,11}");
        if (!pattern.matcher(tfMaChuHo.getText()).matches()) {
            showAlert("Hãy nhập vào mã nhân khẩu hợp lệ!");
            return false;
        }

        List<NhanKhauModel> listNhanKhauModels = new NhanKhauService().getListNhanKhau();
        for (NhanKhauModel nhankhau : listNhanKhauModels) {
            if (nhankhau.getId() == Integer.parseInt(tfMaChuHo.getText())) {
                showAlert("Mã nhân khẩu bị trùng!");
                return false;
            }
        }
        return true;
    }

    private boolean validateTenChuHo() {
        if (tfTenChuHo.getText().length() >= 50 || tfTenChuHo.getText().length() <= 1) {
            showAlert("Hãy nhập vào tên hợp lệ!");
            return false;
        }
        return true;
    }

    private boolean validateCMND() {
        Pattern pattern = Pattern.compile("\\d{1,20}");
        if (!pattern.matcher(tfCMND.getText()).matches()) {
            showAlert("Hãy nhập vào CCCD hợp lệ!");
            return false;
        }
        return true;
    }

    private boolean validateSoDienThoai() {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        if (!pattern.matcher(tfSoDienThoai.getText()).matches()) {
            showAlert("Hãy nhập vào số điện thoại hợp lệ!");
            return false;
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void addHoKhauToDatabase(HoKhauModel hoKhauModel, NhanKhauModel nhanKhauModel) throws ClassNotFoundException, SQLException {
        new HoKhauService().add(hoKhauModel);
        new NhanKhauService().addnk(nhanKhauModel);
        new QuanHeService().add(new QuanHeModel(hoKhauModel.getMaHo(), nhanKhauModel.getId(), "Là chủ hộ"));
        new ChuHoService().add(new ChuHoModel(hoKhauModel.getMaHo(), nhanKhauModel.getId()));
    }

    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
