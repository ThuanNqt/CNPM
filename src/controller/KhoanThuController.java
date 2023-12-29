package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.regex.Pattern;

import controller.khoanthu.UpdateKhoanThu;
import controller.nhankhau.UpdateNhanKhau;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.KhoanThuModel;
import models.NhanKhauModel;
import services.KhoanThuService;
import services.NhanKhauService;

public class KhoanThuController implements Initializable {
	@FXML
	private TableView<KhoanThuModel> tvKhoanPhi;
	@FXML
	private TableColumn<KhoanThuModel, String> colMaKhoanPhi;
	@FXML
	private TableColumn<KhoanThuModel, String> colTenKhoanThu;
	@FXML
	private TableColumn<KhoanThuModel, String> colSoTien;
	@FXML
	private TableColumn<KhoanThuModel, String> colLoaiKhoanThu;
	@FXML
	private TableColumn<KhoanThuModel, String> colHinhThucThu;
	@FXML
	private TableColumn<KhoanThuModel, String> colNgayBatDauThu;
	@FXML
	private TableColumn<KhoanThuModel, String> colNgayKetThucThu;
	@FXML
	private TableColumn<KhoanThuModel, Void> colAction;
	@FXML
	private TextField tfSearch;
	@FXML
	private ComboBox<String> cbChooseSearch;
	private List<KhoanThuModel> listKhoanThu;
	private ObservableList<KhoanThuModel> listValueTableView;

	public void showKhoanThu() throws ClassNotFoundException, SQLException {
		listKhoanThu = new KhoanThuService().getListKhoanThu();
		listValueTableView = FXCollections.observableArrayList(listKhoanThu);

		// thiet lap cac cot cho table views
		colMaKhoanPhi.setCellValueFactory(new PropertyValueFactory<KhoanThuModel, String>("maKhoanThu"));
		colTenKhoanThu.setCellValueFactory(new PropertyValueFactory<KhoanThuModel, String>("tenKhoanThu"));
		colSoTien.setCellValueFactory(new PropertyValueFactory<KhoanThuModel, String>("soTien"));
		
		colAction.setCellFactory(param -> new TableCell<KhoanThuModel, Void>() {
//	        
			    private final HBox container = new HBox(8);
			    private final Button deleteButton = new Button("Xóa");
			    private final Button editButton = new Button("Sửa");

			    {
			        deleteButton.setOnAction(event -> {
			            try {
			            	delKhoanThu();
			            } catch (ClassNotFoundException | SQLException e) {
			                e.printStackTrace();
			            }
			        });

			        editButton.setOnAction(event -> {
			            try {
			            	updateKhoanThu();
			            } catch (IOException | ClassNotFoundException | SQLException e) {
			                e.printStackTrace();
			            }
			        });
			        container.setAlignment(Pos.CENTER);
			        container.getChildren().addAll(editButton, deleteButton);
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

		Map<Integer, String> mapLoaiKhoanThu = new TreeMap();
		mapLoaiKhoanThu.put(1, "Bắt buộc đóng");
		mapLoaiKhoanThu.put(0, "Ủng hộ");

		try {
			colLoaiKhoanThu
					.setCellValueFactory((CellDataFeatures<KhoanThuModel, String> p) -> new ReadOnlyStringWrapper(
							mapLoaiKhoanThu.get(p.getValue().getLoaiKhoanThu())));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		colHinhThucThu.setCellValueFactory(new PropertyValueFactory<KhoanThuModel, String>("hinhThucThu"));
		
		colNgayBatDauThu.setCellValueFactory(new PropertyValueFactory<KhoanThuModel, String>("ngayBatDauThu"));
		
		colNgayKetThucThu.setCellValueFactory(new PropertyValueFactory<KhoanThuModel, String>("ngayKetThucThu"));
		
		tvKhoanPhi.setItems(listValueTableView);

		// thiet lap gia tri cho combobox
		ObservableList<String> listComboBox = FXCollections.observableArrayList("Tên khoản thu", "Mã khoản thu");
		cbChooseSearch.setValue("Tìm kiếm theo");
		cbChooseSearch.setItems(listComboBox);
	}

	// Tim kiem khoan thu
	public void searchKhoanThu() {
		ObservableList<KhoanThuModel> listValueTableView_tmp = null;
		String keySearch = tfSearch.getText();

		// lay lua chon tim kiem cua khach hang
		SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
		String typeSearchString = typeSearch.getSelectedItem();

		// tim kiem thong tin theo lua chon da lay ra
		switch (typeSearchString) {
		case "Tên khoản thu": {
			// neu khong nhap gi -> thong bao loi
			if (keySearch.length() == 0) {
				tvKhoanPhi.setItems(listValueTableView);
				Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào tên khoản thu!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}

			int index = 0;
			List<KhoanThuModel> listKhoanThuModelsSearch = new ArrayList<>();
			for (KhoanThuModel khoanThuModel : listKhoanThu) {
				if (khoanThuModel.getTenKhoanThu().contains(keySearch)) {
					listKhoanThuModelsSearch.add(khoanThuModel);
					index++;
				}
			}
			listValueTableView_tmp = FXCollections.observableArrayList(listKhoanThuModelsSearch);
			tvKhoanPhi.setItems(listValueTableView_tmp);

			// neu khong tim thay thong tin can tim kiem -> thong bao toi nguoi dung khong
			// tim thay
			if (index == 0) {
				tvKhoanPhi.setItems(listValueTableView); // hien thi toan bo thong tin
				Alert alert = new Alert(AlertType.INFORMATION, "Không tìm thấy!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
			}
			break;
		}
		default: { // truong hop con lai : tim theo ma khoan thu
			// neu khong nhap gi -> thong bao loi
			if (keySearch.length() == 0) {
				tvKhoanPhi.setItems(listValueTableView);
				Alert alert = new Alert(AlertType.INFORMATION, "Bạn cần nhập vào thông tin cần tìm!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}

			// kiem tra thong tin tim kiem co hop le hay khong
			Pattern pattern = Pattern.compile("\\d{1,}");
			if (!pattern.matcher(keySearch).matches()) {
				Alert alert = new Alert(AlertType.WARNING, "Bạn phải nhập vào một số!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				return;
			}

			for (KhoanThuModel khoanThuModel : listKhoanThu) {
				if (khoanThuModel.getMaKhoanThu() == Integer.parseInt(keySearch)) {
					listValueTableView_tmp = FXCollections.observableArrayList(khoanThuModel);
					tvKhoanPhi.setItems(listValueTableView_tmp);
					return;
				}
			}

			// khong tim thay thong tin -> thong bao toi nguoi dung
			tvKhoanPhi.setItems(listValueTableView);
			Alert alert = new Alert(AlertType.WARNING, "Không tìm thấy!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		}
		}
	}

	public void addKhoanThu() throws IOException, ClassNotFoundException, SQLException {
		Parent home = FXMLLoader.load(getClass().getResource("/views/khoanthu/AddKhoanThu.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Thêm khoản thu mới");
		stage.setScene(new Scene(home, 400, 600));
		stage.setResizable(false);
		stage.showAndWait();
		showKhoanThu();
	}

	public void delKhoanThu() throws ClassNotFoundException, SQLException {
		KhoanThuModel khoanThuModel = tvKhoanPhi.getSelectionModel().getSelectedItem();

		if (khoanThuModel == null) {
			Alert alert = new Alert(AlertType.WARNING, "Chọn khoản thu bạn muốn xóa!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.WARNING, "Bạn có chắc chắn muốn xóa khoản thu này!", ButtonType.YES,
					ButtonType.NO);
			alert.setHeaderText(null);
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.NO) {
				return;
			} else {
				new KhoanThuService().del(khoanThuModel.getMaKhoanThu());
			}
		}

		showKhoanThu();
	}

	public void updateKhoanThu() throws ClassNotFoundException, SQLException, IOException {
		// lay ra nhan khau can update
		KhoanThuModel khoanThuModel = tvKhoanPhi.getSelectionModel().getSelectedItem();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/khoanthu/UpdateKhoanThu.fxml"));
		Parent home = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Sửa đổi khoản thu");
		stage.setScene(new Scene(home, 400, 600));
		UpdateKhoanThu updateKhoanThu = loader.getController();

		// bat loi truong hop khong hop le
		if (updateKhoanThu == null) return;
		if (khoanThuModel == null) {
			Alert alert = new Alert(AlertType.WARNING, "Chọn khoản thu muốn sửa!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		updateKhoanThu.setKhoanThuModel(khoanThuModel);

		stage.setResizable(false);
		stage.showAndWait();
		showKhoanThu();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			showKhoanThu();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
