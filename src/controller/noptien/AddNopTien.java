package controller.noptien;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.HoKhauModel;
import models.KhoanThuModel;
import models.NhanKhauModel;
import models.NopTienModel;
import services.HoKhauService;
import services.MysqlConnection;
import services.NopTienService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AddNopTien {

    @FXML
    private TextField tfTenKhoanThu;
    @FXML
    private TextField tfTenNguoiNop;
    @FXML
    private TextField tfSoTien;

    private KhoanThuModel khoanThuModel;
    private NhanKhauModel nhanKhauModel;

    @FXML
    public void chooseKhoanThu() throws IOException {
        FXMLLoader loader = loadFXML("/views/noptien/ChooseKhoanNop.fxml", "Chọn khoản thu");
        ChooseKhoanNop chooseKhoanNop = loader.getController();
        khoanThuModel = chooseKhoanNop.getKhoanthuChoose();
        if (khoanThuModel != null) {
            tfTenKhoanThu.setText(khoanThuModel.getTenKhoanThu());
        }
    }

    @FXML
    public void chooseNguoiNop() throws IOException, ClassNotFoundException, SQLException {
        FXMLLoader loader = loadFXML("/views/noptien/ChooseNguoiNop.fxml", "Chọn người nộp");
        ChooseNguoiNop chooseNguoiNop = loader.getController();
        nhanKhauModel = chooseNguoiNop.getNhanKhauChoose();
        if (nhanKhauModel != null) {
            tfTenNguoiNop.setText(nhanKhauModel.getTen());
            
            tfSoTien.setText(String.valueOf(soTienNop()));
            if(khoanThuModel.getLoaiKhoanThu() == 1) {
            tfSoTien.setEditable(false);}
        }
    }

    private FXMLLoader loadFXML(String path, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent home = loader.load();
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(home, 800, 600));
        stage.setResizable(false);
        stage.showAndWait();
        return loader;
    }

    private double soTienNop() throws ClassNotFoundException, SQLException {
        double soTienNop = 0;
        HoKhauModel hoKhau = new HoKhauService().getHoKhaubyIdNhanKhau(nhanKhauModel.getId());

        if ("Theo hộ".equals(khoanThuModel.getHinhThucThu())) {
            soTienNop = khoanThuModel.getSoTien();
        } else if ("Theo đầu người".equals(khoanThuModel.getHinhThucThu())) {
            soTienNop = hoKhau.getSoThanhvien() * khoanThuModel.getSoTien();
        }

        return soTienNop;
    }

    @FXML
    public void addNopTien(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (tfTenKhoanThu.getText().isEmpty() || tfTenNguoiNop.getText().isEmpty()) {
            showAlert(AlertType.WARNING, "Khoản nộp không hợp lệ!");
        } else {
            if (isAlreadyPaid()) {
                showAlert(AlertType.WARNING, "Một người nào đó trong hộ đã đóng khoản phí này!");
            } else {
                double soTienNop = Double.parseDouble(tfSoTien.getText());
                Date currentDate = new Date();
                new NopTienService().add(new NopTienModel(nhanKhauModel.getId(), khoanThuModel.getMaKhoanThu(), soTienNop, currentDate));
            }
        }
        closeStage(event);
    }

    private boolean isAlreadyPaid() throws ClassNotFoundException, SQLException {
        List<NopTienModel> listNopTien = new NopTienService().getListNopTien();
        HoKhauModel hoKhau = new HoKhauService().getHoKhaubyIdNhanKhau(nhanKhauModel.getId());

        for (NopTienModel nopTienModel : listNopTien) {
            HoKhauModel hoKhauNopTien = new HoKhauService().getHoKhaubyIdNhanKhau(nopTienModel.getIdNopTien());
            if (hoKhau.getMaHo() == hoKhauNopTien.getMaHo() && nopTienModel.getMaKhoanThu() == khoanThuModel.getMaKhoanThu()) {
            	return true;
            }
        }
        return false;
    }

    private void showAlert(AlertType alertType, String content) {
        Alert alert = new Alert(alertType, content, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Thêm khoản phí");
        stage.setResizable(false);
        stage.close();
    }
}
