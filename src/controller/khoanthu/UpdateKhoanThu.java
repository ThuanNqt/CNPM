package controller.khoanthu;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.KhoanThuModel;
import services.KhoanThuService;

public class UpdateKhoanThu implements Initializable{
	@FXML
	private ComboBox<String> tfHinhThucThu;

	@FXML
	private ComboBox<String> tfLoaiKhoanThu;
    @FXML
    private TextField tfMaKhoanThu;
    @FXML
    private TextField tfTenKhoanThu;
    
    //private TextField tfLoaiKhoanThu;
    @FXML
    private TextField tfSoTien;
    
    @FXML private DatePicker dpNgayBatDauThu;
    
    @FXML private DatePicker dpNgayKetThucThu;
 
    //private TextField tfHinhThucThu;

    private KhoanThuModel khoanThuModel;

    public void setKhoanThuModel(KhoanThuModel khoanThuModel) {
        this.khoanThuModel = khoanThuModel;
        populateFields();
    }

    private void populateFields() {
        tfTenKhoanThu.setText(khoanThuModel.getTenKhoanThu());
        tfMaKhoanThu.setText(String.valueOf(khoanThuModel.getMaKhoanThu()));
        tfLoaiKhoanThu.setValue((khoanThuModel.getLoaiKhoanThu() == 1) ? "Bắt buộc đóng" : "Ủng hộ");
        //tfLoaiKhoanThu.setValue(hoanThuModel.getLoaiKhoanThu());
        tfLoaiKhoanThu.setEditable(false);
        tfSoTien.setText(String.valueOf(khoanThuModel.getSoTien()));
        //tfHinhThucThu.setText(khoanThuModel.getHinhThucThu());
        tfHinhThucThu.setValue(khoanThuModel.getHinhThucThu());
        tfHinhThucThu.setEditable(false);
        
        java.util.Date ngayBatDauThuUtilDate = new java.util.Date(khoanThuModel.getNgayBatDauThu().getTime());
        // Convert java.util.Date to LocalDate
        LocalDate localDateNgayThu = ngayBatDauThuUtilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dpNgayBatDauThu.setValue(localDateNgayThu);
        
        
        java.util.Date ngayKetThucThuThuUtilDate = new java.util.Date(khoanThuModel.getNgayKetThucThu().getTime());
        // Convert java.util.Date to LocalDate
        LocalDate localDateNgayKetThucThu = ngayKetThucThuThuUtilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dpNgayKetThucThu.setValue(localDateNgayKetThucThu);
    }

    public void updateKhoanThu(ActionEvent event) {
        try {
            if (isValidInput()) {
                extractAndUpdateData();
                closeWindow(event);
            } 
        } catch (Exception e) {
            e.printStackTrace(); // Handle or log the exception as needed
            //showAlert("Đã xảy ra lỗi khi cập nhật khoản thu.");
        }
    }

    private void extractAndUpdateData() throws SQLException, ClassNotFoundException {
        int maKhoanThuInt = khoanThuModel.getMaKhoanThu();
        String tenKhoanThuString = tfTenKhoanThu.getText();
        String a = tfLoaiKhoanThu.getSelectionModel().getSelectedItem().trim();
        int loaiKhoanThuInt = a.equals("Bắt buộc đóng") ? 1 : 0;
        double soTienDouble = Double.parseDouble(tfSoTien.getText());
        String hinhThucThuString = tfHinhThucThu.getSelectionModel().getSelectedItem().trim();
        Date ngayBatDauThuDate = java.sql.Date.valueOf(dpNgayBatDauThu.getValue());
        Date ngayKetThucThuDate = java.sql.Date.valueOf(dpNgayKetThucThu.getValue());

        new KhoanThuService().update(maKhoanThuInt, tenKhoanThuString, soTienDouble, loaiKhoanThuInt, hinhThucThuString, ngayBatDauThuDate, ngayKetThucThuDate);
    }

    private boolean isValidInput() {
        return isInvalidAmount() && isInvalidName() && isValidDateRange();
    }

    private boolean isInvalidName() {
        if (tfTenKhoanThu.getText().length() < 1 || tfTenKhoanThu.getText().length() > 150) {
            showAlert("Hãy nhập vào tên khoản thu hợp lệ!");
            return false;
        }
        return true;
    }

    private boolean isInvalidAmount() {
        Pattern amountPattern = Pattern.compile("\\d{1,11}");
        if (!amountPattern.matcher(tfSoTien.getText()).matches()) {
            showAlert("Hãy nhập vào số tiền hợp lệ!");
            return false;
        }
        return true;
    }
    
    private boolean isValidDateRange() {
        Date ngayBatDau = java.sql.Date.valueOf(dpNgayBatDauThu.getValue());
        Date ngayKetThuc = java.sql.Date.valueOf(dpNgayKetThucThu.getValue());

        if (ngayBatDau != null && ngayKetThuc != null && ngayBatDau.after(ngayKetThuc)) {
            showAlert("Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc.");
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
    public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//tfGioiTinh.setItems(FXCollections.observableArrayList("Nam", "Nữ"));
    	tfHinhThucThu.setItems(FXCollections.observableArrayList("Theo hộ", "Theo đầu người"));
    	tfLoaiKhoanThu.setItems(FXCollections.observableArrayList("Ủng hộ", "Bắt buộc đóng"));
	}
}
