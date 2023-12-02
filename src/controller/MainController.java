package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.HoKhauModel;
import models.KhoanThuModel;
import models.NhanKhauModel;
import services.HoKhauService;
import services.KhoanThuService;
import services.NhanKhauService;

public class MainController implements Initializable{
	@FXML
	private Label lbSoHoKhau;

	@FXML
	private Label lbSoKhoanThu;
	
	@FXML
	private Label lbSoNhanKhau;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			List<HoKhauModel> listHoKhau = new HoKhauService().getListHoKhau();
			long soHoKhau = listHoKhau.stream().count();
			lbSoHoKhau.setText(Long.toString(soHoKhau));
			
			List<KhoanThuModel> listKhoanThu = new KhoanThuService().getListKhoanThu();
			long soKhoanThu = listKhoanThu.stream().count();
			lbSoKhoanThu.setText(Long.toString(soKhoanThu));
			
			List<NhanKhauModel> listNhanKhau = new NhanKhauService().getListNhanKhau();
			long soNhanKhau = listNhanKhau.stream().count();
			lbSoNhanKhau.setText(Long.toString(soNhanKhau));
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
