package controller.khoanthu;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.KhoanThuModel;
import services.KhoanThuService;

public class ThemKhoanThu implements Initializable {

    @FXML private TextField tfMaKhoanThu;
    @FXML private TextField tfTenKhoanThu;
    @FXML private ComboBox<String> cbLoaiKhoanThu;
    @FXML private TextField tfSoTien;
    @FXML private ComboBox<String> cbHinhThucThu;
    @FXML private DatePicker dpNgayBatDauThu;
    @FXML private DatePicker dpNgayKetThucThu;

    private final KhoanThuService khoanThuService = new KhoanThuService();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initializeComboBoxes();
    }

    private void initializeComboBoxes() {
        initializeComboBox(cbLoaiKhoanThu, "Ủng hộ", "Bắt buộc đóng");
        initializeComboBox(cbHinhThucThu, "Theo hộ", "Theo đầu người");
    }

    private void initializeComboBox(ComboBox<String> comboBox, String... items) {
        ObservableList<String> listComboBox = FXCollections.observableArrayList(items);
        comboBox.setItems(listComboBox);
    }

    @FXML
    public void addKhoanThu(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (isValidInput()) {
            saveKhoanThu();
            closeWindow(event);
        }
    }
    
    private boolean isValidDateRange() {
        Date ngayBatDau = java.sql.Date.valueOf(dpNgayBatDauThu.getValue());
        Date ngayKetThuc = java.sql.Date.valueOf(dpNgayKetThucThu.getValue());
        Date ngayHienTai = new Date();
        
        if (ngayBatDau != null && ngayKetThuc != null) {
        	if(ngayBatDau.after(ngayKetThuc)) {
        		showAlert("Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc.");
        		return false;        		
        	}
        	if(ngayKetThuc.before(ngayHienTai)) {
        		 showAlert("Ngày kết thúc phải sau ngày hiện tại.");
        		 return false;
        	}
        }

        return true;
    }


    private boolean isValidInput() throws ClassNotFoundException, SQLException {
        return validateField("Mã khoản thu", tfMaKhoanThu, this::validateMaKhoanThu)
                && validateField("Mã khoản thu", tfMaKhoanThu, value -> {
					try {
						return validateDuplicateMaKhoanThu(value);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return false;
				})
                && validateField("Tên khoản thu", tfTenKhoanThu, this::validateTenKhoanThu)
                && validateField("Số tiền", tfSoTien, this::validateSoTien)
                && isValidDateRange();
    }

    private boolean validateField(String fieldName, TextField textField, Validator validator) {
        String fieldValue = textField.getText();
        if (!validator.isValid(fieldValue)) {
            showAlert("Hãy nhập vào " + fieldName.toLowerCase() + " hợp lệ!");
            return false;
        }
        return true;
    }

    private interface Validator {
        boolean isValid(String value);
    }

    private boolean validateMaKhoanThu(String value) {
        Pattern pattern = Pattern.compile("\\d{1,11}");
        return pattern.matcher(value).matches();
    }

    private boolean validateDuplicateMaKhoanThu(String value) throws ClassNotFoundException, SQLException {
        int maKhoanThu = Integer.parseInt(value);
        List<KhoanThuModel> existingKhoanThuList = khoanThuService.getListKhoanThu();
        return existingKhoanThuList.stream().noneMatch(k -> k.getMaKhoanThu() == maKhoanThu);
    }

    private boolean validateTenKhoanThu(String value) {
        return value.length() >= 1 && value.length() <= 50;
    }

    private boolean validateSoTien(String value) {
        Pattern pattern = Pattern.compile("^[1-9]\\d*(\\.\\d+)?$");
        return pattern.matcher(value).matches();
    }

    private void saveKhoanThu() throws ClassNotFoundException, SQLException {
        SingleSelectionModel<String> loaiKhoanThuSelection = cbLoaiKhoanThu.getSelectionModel();
        String loaiKhoanThu = loaiKhoanThuSelection.getSelectedItem();
        
        SingleSelectionModel<String> hinhThucThuSelection = cbHinhThucThu.getSelectionModel();
        String hinhThucThu = hinhThucThuSelection.getSelectedItem();
        
        int maKhoanThu = Integer.parseInt(tfMaKhoanThu.getText());
        String tenKhoanThu = tfTenKhoanThu.getText();
        double soTien = Double.parseDouble(tfSoTien.getText());
        int loaiKhoanThuValue = loaiKhoanThu.equals("Bắt buộc đóng") ? 1 : 0;

        Date ngayBatDauThu = java.sql.Date.valueOf(dpNgayBatDauThu.getValue());
        
        Date ngayKetThucThu = java.sql.Date.valueOf(dpNgayKetThucThu.getValue());
        
        khoanThuService.add(new KhoanThuModel(maKhoanThu, tenKhoanThu, soTien, loaiKhoanThuValue, hinhThucThu, ngayBatDauThu, ngayKetThucThu));
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

