package controller.nhankhau;

import java.net.URL;

import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.NhanKhauModel;
import models.QuanHeModel;
import services.NhanKhauService;
import services.QuanHeService;


public class AddNhanKhau implements Initializable{

    @FXML
    private TextField tfId;
    @FXML
    private TextField tfTen;
    @FXML
    private ComboBox<String> tfGioiTinh;
    
    @FXML
    private DatePicker dpNgaySinh;
    @FXML
    private TextField tfCccd;
    @FXML
    private TextField tfSdt;
    @FXML
    private TextField tfMaHoKhau;
    @FXML
    private TextField tfQuanHe;
    @FXML
    private TextField tfBietDanh;
    @FXML
    private TextField tfNguyenQuan;
    @FXML
    private TextField tfDanToc;
    @FXML
    private TextField tfNoiThuongTru;
    @FXML
    private TextField tfNgheNghiep;
    @FXML
    private TextField tfNoiLamViec;
    @FXML
    private TextField tfNoiCap;
    

    public void addNhanKhau(ActionEvent event) {
        try {
            validateInput();
            performDatabaseOperations();
            closeStage(event);
        } catch (Exception e) {
            showAlert("Error", "Không thể thực hiện", AlertType.ERROR);
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }

    private void validateInput() throws Exception {
        Pattern pattern;

        // Validate ID
        pattern = Pattern.compile("\\d{1,11}");
        if (!pattern.matcher(tfId.getText()).matches()) {
            throw new Exception("Invalid ID. Please enter a valid ID (up to 11 digits).");
        }

        // Validate Name
        if (tfTen.getText().length() >= 50 || tfTen.getText().length() <= 1) {
            throw new Exception("Invalid name. Please enter a name with 1 to 50 characters.");
        }

     // Validate GioiTinh
        /*if (tfGioiTinh.getSelectionModel().getSelectedItem().trim().length() >= 4 || tfGioiTinh.getSelectionModel().getSelectedItem().trim().length() <= 1) {
            throw new Exception("Invalid gender. Please enter a name with 1 to 4 characters.");
        }*/
     // Validate GioiTinh
       
        

        
        // Validate CCCD
        pattern = Pattern.compile("\\d{1,20}");
        if (!pattern.matcher(tfCccd.getText()).matches()) {
            throw new Exception("Invalid CCCD. Please enter a valid CCCD (up to 20 digits).");
        }

        // Validate SDT
        pattern = Pattern.compile("\\d{1,15}");
        if (!pattern.matcher(tfSdt.getText()).matches()) {
            throw new Exception("Invalid phone number. Please enter a valid phone number (up to 15 digits).");
        }

        // Validate MaHoKhau
        pattern = Pattern.compile("\\d{1,11}");
        if (!pattern.matcher(tfMaHoKhau.getText()).matches()) {
            throw new Exception("Invalid MaHoKhau. Please enter a valid MaHoKhau (up to 11 digits).");
        }

        // Validate QuanHe
        if (tfQuanHe.getText().length() >= 30 || tfQuanHe.getText().length() <= 1) {
            throw new Exception("Invalid relationship. Please enter a valid relationship with 1 to 30 characters.");
        }
    }
    
    

    private void performDatabaseOperations() throws ClassNotFoundException, SQLException {
        int idInt = Integer.parseInt(tfId.getText());
        String tenString = tfTen.getText();
        String gioiTinhString = tfGioiTinh.getSelectionModel().getSelectedItem().trim();
        String cccdString = tfCccd.getText();
        Date ngaySinhDate = java.sql.Date.valueOf(dpNgaySinh.getValue());
        String sdtString = tfSdt.getText();
        int mahokhauInt = Integer.parseInt(tfMaHoKhau.getText());
        String quanheString = tfQuanHe.getText();
        String bietdanhString = tfBietDanh.getText();
        String nguyenquanString = tfNguyenQuan.getText();
        String dantocString = tfDanToc.getText();
        String noithuongtruString = tfNoiThuongTru.getText();
        String nghenghiepString = tfNgheNghiep.getText();
        String noilamviecString = tfNoiLamViec.getText();
        String noicapString = tfNoiCap.getText();

        NhanKhauService nhanKhauService = new NhanKhauService();
        QuanHeService quanHeService = new QuanHeService();

        NhanKhauModel nhanKhauModel = new NhanKhauModel(idInt, cccdString, tenString, gioiTinhString, ngaySinhDate, sdtString,bietdanhString,dantocString,noithuongtruString,nghenghiepString,noilamviecString,nguyenquanString,noicapString);
        QuanHeModel quanHeModel = new QuanHeModel(mahokhauInt, idInt, quanheString);

        nhanKhauService.add(nhanKhauModel);
        quanHeService.add(quanHeModel);
    }

    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content, AlertType alertType) {
        Alert alert = new Alert(alertType, content, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		tfGioiTinh.setItems(FXCollections.observableArrayList("Nam", "Nữ"));
	}
}
