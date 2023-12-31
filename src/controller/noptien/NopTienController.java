package controller.noptien;

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

public class NopTienController implements Initializable {
	@FXML
	private TableView<NopTienModel> tvNopTien;
	@FXML
	private TableColumn<NopTienModel, String> tbcTenNguoi;
	@FXML
	private TableColumn<NopTienModel, String> tbcTenKhoanThu;
	@FXML
	private TableColumn<NopTienModel, String> tbcSoTien;
	@FXML
	private TableColumn<NopTienModel, String> tbcNgayThu;
	@FXML
	private TableColumn<NopTienModel, Void> colAction;

	
	@FXML
    private Button deleteNopTienButton;

	
	ObservableList<NopTienModel> listValueTableView;
	private List<NopTienModel> listNopTien;
	private List<NopTienModel> listDeleteNopTien;
	private List<NhanKhauModel> listNhanKhau;
	private List<KhoanThuModel> listKhoanThu;
	Map<Integer, String> mapIdToTen;
	Map<Integer, String> mapIdToTenKhoanThu;
	
//	@FXML
//    private void handleDeleteNopTienButtonAction(ActionEvent event) {
//        try {
//            showNopTienDaXoa();
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace(); // Xử lý ngoại lệ theo ý bạn
//        }
//    }

	@FXML
	private void handleDeleteNopTienButtonAction(ActionEvent event) throws IOException {
	  try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/noptien/DeleteNopTien.fxml"));
	    Parent root = loader.load();
	    DeleteNopTienController deleteNopTienController = loader.getController();  // Lấy controller

	    // Gọi phương thức showNopTienDaXoa trên controller đã khởi tạo
	    deleteNopTienController.showNopTienDaXoa();

	    Stage stage = new Stage();
	    stage.setScene(new Scene(root));
	    stage.setTitle("Delete Nop Tien");
	    stage.show();
	  } catch (ClassNotFoundException | SQLException e) {
	    e.printStackTrace(); // Xử lý ngoại lệ theo ý bạn
	  }
	}


	
	public void showNopTien() throws ClassNotFoundException, SQLException {
		listNopTien = new NopTienService().getListNopTien();
		listKhoanThu = new KhoanThuService().getListKhoanThu();
		listNhanKhau = new NhanKhauService().getListNhanKhau();
		listValueTableView = FXCollections.observableArrayList(listNopTien);

		mapIdToTen = new HashMap<>();
		listNhanKhau.forEach(nhankhau -> {
			mapIdToTen.put(nhankhau.getId(), nhankhau.getTen());
		});
		mapIdToTenKhoanThu = new HashMap<>();
		listKhoanThu.forEach(khoanthu -> {
			mapIdToTenKhoanThu.put(khoanthu.getMaKhoanThu(), khoanthu.getTenKhoanThu());
		});

		try {
			tbcTenNguoi.setCellValueFactory((CellDataFeatures<NopTienModel, String> p) -> new ReadOnlyStringWrapper(
					mapIdToTen.get(p.getValue().getIdNopTien())));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			tbcTenKhoanThu.setCellValueFactory((CellDataFeatures<NopTienModel, String> p) -> new ReadOnlyStringWrapper(
					mapIdToTenKhoanThu.get(p.getValue().getMaKhoanThu())));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		tbcSoTien.setCellValueFactory(new PropertyValueFactory<>("soTien"));
		
		tbcNgayThu.setCellValueFactory(new PropertyValueFactory<>("ngayThu"));
		
		colAction.setCellFactory(param -> new TableCell<NopTienModel, Void>() {
//	       
			    private final HBox container = new HBox();
			    private final Button deleteButton = new Button("Xóa");

			    {
			        deleteButton.setOnAction(event -> {
			            try {
			            	delNopTien();
			            } catch (ClassNotFoundException | SQLException e) {
			                e.printStackTrace();
			            }
			        });

			      
			        container.setAlignment(Pos.CENTER);
			        container.getChildren().addAll(deleteButton);
			    }

			    @Override
			    protected void updateItem(Void item, boolean empty) {
			        super.updateItem(item, empty);

			        if (empty) {
			            setGraphic(null);
			        } else {
			            setGraphic(container);
			        }
			    }
			});
		
		tvNopTien.setItems(listValueTableView);
	}


	public void addNopTien(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		Parent home = FXMLLoader.load(getClass().getResource("/views/noptien/AddNopTien.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Thêm khoản phí");
		stage.setScene(new Scene(home, 400, 400));
		stage.setResizable(false);
		stage.showAndWait();
		showNopTien();
	}

	public void delNopTien() throws ClassNotFoundException, SQLException {
		NopTienModel nopTienModel = tvNopTien.getSelectionModel().getSelectedItem();

		if (nopTienModel == null) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy chọn khoản nộp bạn cần xóa!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		}
		
		Alert alert = new Alert(AlertType.WARNING, "Bạn chắc chắn muốn xóa khoản nộp này?", ButtonType.YES,
				ButtonType.NO);
		alert.setHeaderText(null);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.NO) {
			return;
		} else {
			String sql = "INSERT INTO delete_nop_tien(IDNopTien, MaKhoanThu, SoTien, NgayThu) VALUES (?, ? , ?, ?)";
	        try (Connection connection = MysqlConnection.getMysqlConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setInt(1, nopTienModel.getIdNopTien());
	            preparedStatement.setInt(2, nopTienModel.getMaKhoanThu());
	            preparedStatement.setDouble(3, nopTienModel.getSoTien());
	            preparedStatement.setDate(4, (Date) nopTienModel.getNgayThu());
	            preparedStatement.executeUpdate();
	        }
			new NopTienService().delete(nopTienModel.getIdNopTien(), nopTienModel.getMaKhoanThu());
		}
		
		showNopTien();
	}

	
	
//	public void showNopTienDaXoa() throws ClassNotFoundException, SQLException {
//		listDeleteNopTien = new NopTienService().getListDeleteNopTien();
//		listKhoanThu = new KhoanThuService().getListKhoanThu();
//		listNhanKhau = new NhanKhauService().getListNhanKhau();
//		listValueTableView = FXCollections.observableArrayList(listDeleteNopTien);
//
//		mapIdToTen = new HashMap<>();
//		listNhanKhau.forEach(nhankhau -> {
//			mapIdToTen.put(nhankhau.getId(), nhankhau.getTen());
//		});
//		mapIdToTenKhoanThu = new HashMap<>();
//		listKhoanThu.forEach(khoanthu -> {
//			mapIdToTenKhoanThu.put(khoanthu.getMaKhoanThu(), khoanthu.getTenKhoanThu());
//		});
//
//		try {
//			tbcTenNguoi.setCellValueFactory((CellDataFeatures<NopTienModel, String> p) -> new ReadOnlyStringWrapper(
//					mapIdToTen.get(p.getValue().getIdNopTien())));
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		try {
//			tbcTenKhoanThu.setCellValueFactory((CellDataFeatures<NopTienModel, String> p) -> new ReadOnlyStringWrapper(
//					mapIdToTenKhoanThu.get(p.getValue().getMaKhoanThu())));
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//		tbcSoTien.setCellValueFactory(new PropertyValueFactory<>("soTien"));
//		
//		tbcNgayThu.setCellValueFactory(new PropertyValueFactory<>("ngayThu"));
//		
//		
//		tvNopTien.setItems(listValueTableView);
//		
//		 try {
//		        // Tạo FXMLLoader
//		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DeleteNopTien.fxml"));
//
//		        // Load file FXML để tạo Parent
//		        Parent root = loader.load();
//
//		        // Hiển thị Stage mới
//		        Stage stage = new Stage();
//		        stage.setScene(new Scene(root));
//		        stage.setTitle("Delete Nop Tien");
//		        stage.show();
//		        
//		    } catch (IOException e) {
//		        e.printStackTrace(); // Xử lý ngoại lệ theo ý bạn
//		    }
//	}
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			showNopTien();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
