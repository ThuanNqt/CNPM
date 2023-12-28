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
import models.NopTienModel;
import services.HoKhauService;
import services.KhoanThuService;
import services.NhanKhauService;
import services.NopTienService;

public class MainController implements Initializable{
	@FXML
	private Label lbSoHoKhau;

	@FXML
	private Label lbSoKhoanThu;
	
	@FXML
	private Label lbSoNhanKhau;
	
	@FXML
	private Label lbSoNhanKhauNam;
	
	@FXML
	private Label lbSoNopTien;
	
	@FXML
	private Label lbSoTienThu;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			//Tổng số nhân khẩu
			List<NhanKhauModel> listNhanKhau = new NhanKhauService().getListNhanKhau();
			long soNhanKhau = listNhanKhau.stream().count();
			lbSoNhanKhau.setText(Long.toString(soNhanKhau));
			
			//Tổng số nhân khẩu nam
			List<NhanKhauModel> listNhanKhauNam = new NhanKhauService().getListNhanKhauNam();
			long soNhanKhauNam = listNhanKhauNam.stream().count();
			lbSoNhanKhauNam.setText(Long.toString(soNhanKhauNam));
			
			//Tổng số hộ khẩu
			List<HoKhauModel> listHoKhau = new HoKhauService().getListHoKhau();
			long soHoKhau = listHoKhau.stream().count();
			lbSoHoKhau.setText(Long.toString(soHoKhau));
			
			//Tổng số khoản thu
			List<KhoanThuModel> listKhoanThu = new KhoanThuService().getListKhoanThu();
			long soKhoanThu = listKhoanThu.stream().count();
			lbSoKhoanThu.setText(Long.toString(soKhoanThu));
			
			//Tổng số tiền đã đóng
			double tongSoTienThu = new NopTienService().getTongNopTien();
			lbSoTienThu.setText( Double.toString((Double)tongSoTienThu));
			
			//Số khoản phí đã đóng
			List<NopTienModel> listNopTien = new NopTienService().getListNopTien();
			long soNopTien = listNopTien.stream().count();
			lbSoNopTien.setText(Long.toString(soNopTien));
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
