package controller.noptien;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
import services.NopTienService;

public class AddNopTien {
	@FXML
	private TextField tfTenKhoanThu;
	@FXML
	private TextField tfTenNguoiNop;
	@FXML
	private TextField tfSoTien;
	
	private KhoanThuModel khoanThuModel;
	private NhanKhauModel nhanKhauModel;
	
	public void chooseKhoanThu() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/noptien/ChooseKhoanNop.fxml"));
		Parent home = loader.load(); 
        Stage stage = new Stage();
        stage.setTitle("Chọn khoản thu");
        stage.setScene(new Scene(home,800,600));
        stage.setResizable(false);
        stage.showAndWait();
        
        ChooseKhoanNop chooseKhoanNop = loader.getController();
        khoanThuModel = chooseKhoanNop.getKhoanthuChoose();
        if(khoanThuModel == null) return;
        
        tfTenKhoanThu.setText(khoanThuModel.getTenKhoanThu());
	}
	
	public void chooseNguoiNop() throws IOException, ClassNotFoundException, SQLException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/noptien/ChooseNguoiNop.fxml"));
		Parent home = loader.load(); 
        Stage stage = new Stage();
        stage.setTitle("Chọn người nộp");
        stage.setScene(new Scene(home,800,600));
        stage.setResizable(false);
        stage.showAndWait();
        
        ChooseNguoiNop chooseNguoiNop = loader.getController();
        nhanKhauModel = chooseNguoiNop.getNhanKhauChoose();
        if(nhanKhauModel == null) return;
        
        tfTenNguoiNop.setText(nhanKhauModel.getTen());
        tfSoTien.setText(String.valueOf(soTienNop()));
	}
	public double soTienNop() throws ClassNotFoundException, SQLException {
	
		double soTienNop = 0;
		HoKhauModel hokhau = new HoKhauService().getHoKhaubyIdNhanKhau(nhanKhauModel.getId());
		
		if(khoanThuModel.getHinhThucThu().equals("Theo hộ")) {
			soTienNop = khoanThuModel.getSoTien();
		}
		if(khoanThuModel.getHinhThucThu().equals("Theo đầu người") ){
			soTienNop = hokhau.getSoThanhvien() * khoanThuModel.getSoTien();
		}
		
		return soTienNop;
	}
	
	public void addNopTien(ActionEvent event) throws ClassNotFoundException, SQLException {		
		if(tfTenKhoanThu.getText().length() == 0 || tfTenNguoiNop.getText().length() == 0) {
			Alert alert = new Alert(AlertType.WARNING, "Khoản nộp không hợp lệ!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		} else {
			List<NopTienModel> listNopTien = new NopTienService().getListNopTien();
			HoKhauModel hokhau = new HoKhauService().getHoKhaubyIdNhanKhau(nhanKhauModel.getId());
			for(NopTienModel nopTienModel : listNopTien) {
				HoKhauModel hokhaunoptien = new HoKhauService().getHoKhaubyIdNhanKhau(nopTienModel.getIdNopTien());
				if(hokhau.getMaHo()== hokhaunoptien.getMaHo() 
						&& nopTienModel.getMaKhoanThu() == khoanThuModel.getMaKhoanThu()) {
					Alert alert = new Alert(AlertType.WARNING, "Hộ đã đóng khoản phí này!", ButtonType.OK);
					alert.setHeaderText(null);
					alert.showAndWait();
					return;
				}
			}
			double soTienNop = soTienNop();
			
			
			Date currentDate = new Date();
			new NopTienService().add(new NopTienModel( nhanKhauModel.getId(),khoanThuModel.getMaKhoanThu(),soTienNop, currentDate));
		}
		
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		stage.setTitle("Thêm khoản phí");
		stage.setResizable(false);
        stage.close();
	}
}