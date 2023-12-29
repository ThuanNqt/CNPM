package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.ChuHoModel;
import models.KhoanThuModel;
import models.NhanKhauModel;
import models.NopTienModel;
import services.ChuHoService;
import services.KhoanThuService;
import services.MysqlConnection;
import services.NhanKhauService;
import services.NopTienService;

public class DeleteNopTienController implements Initializable {
	@FXML
	private TableView<NopTienModel> tvDeleteNopTien;
	@FXML
	private TableColumn<NopTienModel, String> tbcTenNguoiDaNop;
	@FXML
	private TableColumn<NopTienModel, String> tbcTenKhoanThuDaNop;
	@FXML
	private TableColumn<NopTienModel, String> tbcSoTienDaNop;
	@FXML
	private TableColumn<NopTienModel, String> tbcNgayThuTien;


	
	ObservableList<NopTienModel> listValueTableView;
	private List<NopTienModel> listDeleteNopTien;
	private List<NhanKhauModel> listNhanKhau;
	private List<KhoanThuModel> listKhoanThu;
	Map<Integer, String> mapIdToTen;
	Map<Integer, String> mapIdToTenKhoanThu;
	

	public void showNopTienDaXoa() throws ClassNotFoundException, SQLException {
		listDeleteNopTien = new NopTienService().getListDeleteNopTien();
		listKhoanThu = new KhoanThuService().getListKhoanThu();
		listNhanKhau = new NhanKhauService().getListNhanKhau();
		listValueTableView = FXCollections.observableArrayList(listDeleteNopTien);

		mapIdToTen = new HashMap<>();
		listNhanKhau.forEach(nhankhau -> {
			mapIdToTen.put(nhankhau.getId(), nhankhau.getTen());
		});
		mapIdToTenKhoanThu = new HashMap<>();
		listKhoanThu.forEach(khoanthu -> {
			mapIdToTenKhoanThu.put(khoanthu.getMaKhoanThu(), khoanthu.getTenKhoanThu());
		});

		try {
			tbcTenNguoiDaNop.setCellValueFactory((CellDataFeatures<NopTienModel, String> p) -> new ReadOnlyStringWrapper(
					mapIdToTen.get(p.getValue().getIdNopTien())));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			tbcTenKhoanThuDaNop.setCellValueFactory((CellDataFeatures<NopTienModel, String> p) -> new ReadOnlyStringWrapper(
					mapIdToTenKhoanThu.get(p.getValue().getMaKhoanThu())));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		tbcSoTienDaNop.setCellValueFactory(new PropertyValueFactory<>("soTien"));
		
		tbcNgayThuTien.setCellValueFactory(new PropertyValueFactory<>("ngayThu"));
		
		
		tvDeleteNopTien.setItems(listValueTableView);
			
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			showNopTienDaXoa();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
