package controller.hokhau;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.scene.control.Label;
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
import models.ChuHoModel;
import models.HoKhauModel;
import models.NhanKhauModel;
import models.QuanHeModel;
import services.ChuHoService;
import services.HoKhauService;
import services.NhanKhauService;
import services.QuanHeService;

public class HoKhauController implements Initializable {
	@FXML
	TableColumn<HoKhauModel, String> colMaHoKhau;
	@FXML
	TableColumn<HoKhauModel, String> colMaChuHo;
	@FXML
	TableColumn<HoKhauModel, String> colSoThanhVien;
	@FXML
	TableColumn<HoKhauModel, String> colDiaChi;
	@FXML
	TableView<HoKhauModel> tvHoKhau;
	@FXML
	TextField tfSearch;

	//
	@FXML
	private Label lbSoHoKhau;
	@FXML
	private Label lbSoNhanKhau;
	@FXML
	private Label lbSoNhanKhauNam;
	@FXML
	private Label lbSoNhanKhauNu;
	//
	
	@FXML
	private TableColumn<HoKhauModel, Void> colAction;
	
	@FXML
	ComboBox<String> cbChooseSearch;

	ObservableList<HoKhauModel> listValueTableView;
	private List<HoKhauModel> listHoKhau;

	// Hien thi thong tin ho khau
	public void showHoKhau() throws ClassNotFoundException, SQLException {
		listHoKhau = new HoKhauService().getListHoKhau();
		listValueTableView = FXCollections.observableArrayList(listHoKhau);
		List<NhanKhauModel> listNhanKhau = new NhanKhauService().getListNhanKhau();
		List<ChuHoModel> listChuHo = new ChuHoService().getListChuHo();
		
		if (listChuHo.isEmpty()) {
			return;
		}
		
		Map<Integer, Integer> mapMahoToId = new HashMap<>();
		listChuHo.forEach(chuho -> {
			mapMahoToId.put(chuho.getMaHo(), chuho.getIdChuHo());
		});

		Map<Integer, String> mapIdToTen = new HashMap<>();
		listNhanKhau.forEach(nhankhau -> {
			mapIdToTen.put(nhankhau.getId(), nhankhau.getTen());
		});

		// Thiet lap Table views
		colMaHoKhau.setCellValueFactory(new PropertyValueFactory<HoKhauModel, String>("maHo"));
		
		try {
		colMaChuHo.setCellValueFactory((CellDataFeatures<HoKhauModel, String> p) -> new ReadOnlyStringWrapper(
				mapIdToTen.get(mapMahoToId.get(p.getValue().getMaHo())).toString()));
		}catch(Exception e) {
			e.printStackTrace();
		}
		colSoThanhVien.setCellValueFactory(new PropertyValueFactory<HoKhauModel, String>("soThanhvien"));
		colDiaChi.setCellValueFactory(new PropertyValueFactory<HoKhauModel, String>("diaChi"));
		
		colAction.setCellFactory(param -> new TableCell<HoKhauModel, Void>() {
			    private final HBox container = new HBox(8);
			    private final Button deleteButton = new Button("Xóa");
			    private final Button showChiTietButton = new Button("Chi tiết");
			    private final Button editButton = new Button("Sửa");
				private final Button tachHoButton = new Button("Tách hộ");

			    {		
			    	deleteButton.setOnAction(event -> {
			            try {
			            	delHoKhau();
			            } catch (ClassNotFoundException | SQLException e) {
			                e.printStackTrace();
			            }
			        });
			        editButton.setOnAction(event -> {
			            try {
			                updateHoKhau();
			            } catch (IOException | ClassNotFoundException | SQLException e) {
			                e.printStackTrace();
			            }
			        });
			        showChiTietButton.setOnAction(event -> {
			            showChiTiet();
			        });
					tachHoButton.setOnAction(event -> {
						try {
							tachHo();
						} catch (SQLException e) {
							throw new RuntimeException(e);
						} catch (ClassNotFoundException e) {
							throw new RuntimeException(e);
						}
					});

			        container.setAlignment(Pos.CENTER);
			        container.getChildren().addAll(showChiTietButton, tachHoButton, editButton, deleteButton);
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
		
		
		tvHoKhau.setItems(listValueTableView);
		
		
		

		// Thiet lap Combo box
		ObservableList<String> listComboBox = FXCollections.observableArrayList("Mã hộ", "Tên chủ hộ", "Địa chỉ");
		cbChooseSearch.setValue("Tìm kiếm theo");
		cbChooseSearch.setItems(listComboBox);
	}

	public void addHoKhau() throws ClassNotFoundException, SQLException, IOException {
		Parent home = FXMLLoader.load(getClass().getResource("/views/hokhau/AddHoKhau.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Thêm hộ khẩu mới");
		stage.setScene(new Scene(home, 400, 600));
		stage.setResizable(false);
		stage.showAndWait();
		
		List<NhanKhauModel> listNhanKhau = new NhanKhauService().getListNhanKhau();
		long soNhanKhau = listNhanKhau.stream().count();
		lbSoNhanKhau.setText(Long.toString(soNhanKhau));
		
		List<NhanKhauModel> listNhanKhauNam = new NhanKhauService().getListNhanKhauNam();
		long soNhanKhauNam = listNhanKhauNam.stream().count();
		lbSoNhanKhauNam.setText(Long.toString(soNhanKhauNam));
		
		List<NhanKhauModel> listNhanKhauNu = new NhanKhauService().getListNhanKhauNu();
		long soNhanKhauNu = listNhanKhauNu.stream().count();
		lbSoNhanKhauNu.setText(Long.toString(soNhanKhauNu));
		
		List<HoKhauModel> listHoKhau = new HoKhauService().getListHoKhau();
		long soHoKhau = listHoKhau.stream().count();
		lbSoHoKhau.setText(Long.toString(soHoKhau));
		
		showHoKhau();
	}

	public void delHoKhau() throws ClassNotFoundException, SQLException {
		HoKhauModel hoKhauModel = tvHoKhau.getSelectionModel().getSelectedItem();

		if (hoKhauModel == null) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy chọn hộ khẩu muốn xóa!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.WARNING, "Bạn chắc chắn muốn xóa hộ khẩu này?",
					ButtonType.YES, ButtonType.NO);
			//alert.setHeaderText("Bạn chắc chắn muốn xóa hộ khẩu này?");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.NO) {
				return;
			} else {
				/// tao map anh xa gia tri Id sang maHo
				Map<Integer, Integer> mapIdToMaho = new HashMap<>();
				List<QuanHeModel> listQuanHe = new QuanHeService().getListQuanHe();
				try {
					listQuanHe.forEach(quanhe -> {
						mapIdToMaho.put(quanhe.getIdThanhVien(), quanhe.getMaHo());
					});
				}catch(Exception e) {
					e.printStackTrace();
				}

				// xoa toan bo nhan khau trong ho khau
				int idHoKhauDel = hoKhauModel.getMaHo(); // lay ra ma ho de so sanh
				List<NhanKhauModel> listNhanKhauModels = new NhanKhauService().getListNhanKhau();
				listNhanKhauModels.stream().filter(nhankhau -> mapIdToMaho.get(nhankhau.getId()) == idHoKhauDel)
						.forEach(nk -> {
							try {
								new NhanKhauService().del(nk.getId());
							} catch (ClassNotFoundException | SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						});

				// xoa ho khau
				new HoKhauService().delete(idHoKhauDel);
			}
		}

		List<NhanKhauModel> listNhanKhau = new NhanKhauService().getListNhanKhau();
		long soNhanKhau = listNhanKhau.stream().count();
		lbSoNhanKhau.setText(Long.toString(soNhanKhau));
		
		List<NhanKhauModel> listNhanKhauNam = new NhanKhauService().getListNhanKhauNam();
		long soNhanKhauNam = listNhanKhauNam.stream().count();
		lbSoNhanKhauNam.setText(Long.toString(soNhanKhauNam));
		
		List<NhanKhauModel> listNhanKhauNu = new NhanKhauService().getListNhanKhauNu();
		long soNhanKhauNu = listNhanKhauNu.stream().count();
		lbSoNhanKhauNu.setText(Long.toString(soNhanKhauNu));
		
		List<HoKhauModel> listHoKhau = new HoKhauService().getListHoKhau();
		long soHoKhau = listHoKhau.stream().count();
		lbSoHoKhau.setText(Long.toString(soHoKhau));
		
		showHoKhau();
	}

	public void searchHoKhau() throws ClassNotFoundException, SQLException {
		ObservableList<HoKhauModel> listValueTableView_tmp = null;
		String keySearch = tfSearch.getText();

		// lay lua chon tim kiem cua khach hang
		SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
		String typeSearchString = typeSearch.getSelectedItem();

		// tim kiem thong tin theo lua chon da lay ra
		switch (typeSearchString) {
		case "Tên chủ hộ": {
			// neu khong nhap gi -> thong bao loi
			if (keySearch.length() == 0) {
				tvHoKhau.setItems(listValueTableView);
				Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào tên chủ hộ!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}

			Map<Integer, Integer> mapMahoToId = new HashMap<>();
			List<ChuHoModel> listChuHo = new ChuHoService().getListChuHo();
			listChuHo.stream().forEach(chuho -> {
				mapMahoToId.put(chuho.getMaHo(), chuho.getIdChuHo());
			});
			Map<Integer, String> mapIdToTen = new HashMap<>();
			List<NhanKhauModel> listNhanKhau = new NhanKhauService().getListNhanKhau();
			listNhanKhau.stream().forEach(nhankhau -> {
				mapIdToTen.put(nhankhau.getId(), nhankhau.getTen());
			});

			int index = 0;
			List<HoKhauModel> listHoKhauModelsSearch = new ArrayList<>();
			for (HoKhauModel hoKhauModel : listHoKhau) {
				if (mapIdToTen.get(mapMahoToId.get(hoKhauModel.getMaHo())).contains(keySearch)) {
					listHoKhauModelsSearch.add(hoKhauModel);
					index++;
				}
			}
			listValueTableView_tmp = FXCollections.observableArrayList(listHoKhauModelsSearch);
			tvHoKhau.setItems(listValueTableView_tmp);

			// neu khong tim thay thong tin can tim kiem -> thong bao toi nguoi dung khong
			// tim thay
			if (index == 0) {
				tvHoKhau.setItems(listValueTableView); // hien thi toan bo thong tin
				Alert alert = new Alert(AlertType.INFORMATION, "Không tìm thấy!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
			}
			break;
		}
		case "Địa chỉ": {
			// neu khong nhap gi -> thong bao loi
			if (keySearch.length() == 0) {
				tvHoKhau.setItems(listValueTableView);
				Alert alert = new Alert(AlertType.WARNING, "Hãy nhập vào địa chỉ!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}

			int index = 0;
			List<HoKhauModel> listHoKhau_tmp = new ArrayList<>();
			for (HoKhauModel hoKhauModel : listHoKhau) {
				if (hoKhauModel.getDiaChi().contains(keySearch)) {
					listHoKhau_tmp.add(hoKhauModel);
					index++;
				}
			}
			listValueTableView_tmp = FXCollections.observableArrayList(listHoKhau_tmp);
			tvHoKhau.setItems(listValueTableView_tmp);

			// neu khong tim thay thong tin tim kiem -> thong bao toi nguoi dung
			if (index == 0) {
				tvHoKhau.setItems(listValueTableView); // hien thi toan bo thong tin
				Alert alert = new Alert(AlertType.INFORMATION, "Không tìm thấy!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
			}
			break;
		}
		default: { // truong hop con lai : tim theo id
			// neu khong nhap gi -> thong bao loi
			if (keySearch.length() == 0) {
				tvHoKhau.setItems(listValueTableView);
				Alert alert = new Alert(AlertType.INFORMATION, "Hãy nhập vào mã hộ!", ButtonType.OK);
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

			for (HoKhauModel hoKhauModel : listHoKhau) {
				if (hoKhauModel.getMaHo() == Integer.parseInt(keySearch)) {
					listValueTableView_tmp = FXCollections.observableArrayList(hoKhauModel);
					tvHoKhau.setItems(listValueTableView_tmp);
					return;
				}
			}

			// khong tim thay thong tin -> thong bao toi nguoi dung
			tvHoKhau.setItems(listValueTableView);
			Alert alert = new Alert(AlertType.WARNING, "Không tìm thấy!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		}
		}
	}

	public void updateHoKhau() throws ClassNotFoundException, SQLException, IOException {
		// lay ra nhan khau can update
		HoKhauModel hoKhauModel = tvHoKhau.getSelectionModel().getSelectedItem();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/hokhau/UpdateHoKhau.fxml"));
		Parent home = loader.load();
		Stage stage = new Stage();
		stage.setTitle("Sửa thông tin hộ khẩu");
		stage.setScene(new Scene(home, 400, 600));
		UpdateHoKhau updateHoKhau = loader.getController();

		// bat loi truong hop khong hop le
		if (updateHoKhau == null)
			return;
		if (hoKhauModel == null) {
			Alert alert = new Alert(AlertType.WARNING, "Chọn hộ khẩu cần sửa!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		updateHoKhau.setHoKhauModel(hoKhauModel);

		stage.setResizable(false);
		stage.showAndWait();
		showHoKhau();
	}
	private void showChiTiet() {
        
        HoKhauModel selectedHoKhau = tvHoKhau.getSelectionModel().getSelectedItem();
        
        if (selectedHoKhau != null) {
            try {
                
                ChiTietHoKhauController ctHoKhau = new ChiTietHoKhauController ();
                ctHoKhau.setHoKhauModel(selectedHoKhau);

                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/hokhau/ChiTietHoKhau.fxml"));
                loader.setController(ctHoKhau);
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	private void tachHo() throws SQLException, ClassNotFoundException {

		HoKhauModel selectedHoKhau = tvHoKhau.getSelectionModel().getSelectedItem();

		if (selectedHoKhau != null) {
			try {

				TachHoController tachHoController = new TachHoController();
				tachHoController.setHoKhauModel(selectedHoKhau);


				FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/hokhau/TachHoKhau.fxml"));
				loader.setController(tachHoController);
				Parent root = loader.load();
				Stage stage = new Stage();
				stage.setScene(new Scene(root));
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		showHoKhau();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			List<NhanKhauModel> listNhanKhau = new NhanKhauService().getListNhanKhau();
			long soNhanKhau = listNhanKhau.stream().count();
			lbSoNhanKhau.setText(Long.toString(soNhanKhau));
			
			List<NhanKhauModel> listNhanKhauNam = new NhanKhauService().getListNhanKhauNam();
			long soNhanKhauNam = listNhanKhauNam.stream().count();
			lbSoNhanKhauNam.setText(Long.toString(soNhanKhauNam));
			
			List<NhanKhauModel> listNhanKhauNu = new NhanKhauService().getListNhanKhauNu();
			long soNhanKhauNu = listNhanKhauNu.stream().count();
			lbSoNhanKhauNu.setText(Long.toString(soNhanKhauNu));
			
			List<HoKhauModel> listHoKhau = new HoKhauService().getListHoKhau();
			long soHoKhau = listHoKhau.stream().count();
			lbSoHoKhau.setText(Long.toString(soHoKhau));
			
			showHoKhau();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}