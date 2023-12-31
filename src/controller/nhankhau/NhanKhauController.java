package controller.nhankhau;

import javafx.event.ActionEvent;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
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

public class NhanKhauController implements Initializable {
	@FXML
	private TableColumn<NhanKhauModel, String> colMaNhanKhau;
	@FXML
	private TableColumn<NhanKhauModel, String> colTen;
	@FXML
	private TableColumn<NhanKhauModel, String> colGioiTinh;
	@FXML
	private TableColumn<NhanKhauModel, String> colTuoi;
	@FXML
	private TableColumn<NhanKhauModel, String> colCCCD;
	@FXML
	private TableColumn<NhanKhauModel, String> colSDT;
	@FXML
	private TableColumn<NhanKhauModel, String> colMaHo;
	@FXML
	private TableView<NhanKhauModel> tvNhanKhau;
	@FXML
	private TextField tfSearch;
	@FXML
	private ComboBox<String> cbChooseSearch;
	
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
	private TableColumn<NhanKhauModel, Void> colAction;

	
	private ObservableList<NhanKhauModel> listValueTableView;
	private List<NhanKhauModel> listNhanKhau;

	
	public TableView<NhanKhauModel> getTvNhanKhau() {
		return tvNhanKhau;
	}

	public void setTvNhanKhau(TableView<NhanKhauModel> tvNhanKhau) {
		this.tvNhanKhau = tvNhanKhau;
	}

	// hien thi thong tin nhan khau
	public void showNhanKhau() throws ClassNotFoundException, SQLException {
		listNhanKhau = new NhanKhauService().getListNhanKhau();
		listValueTableView = FXCollections.observableArrayList(listNhanKhau);
		
		// tao map anh xa gia tri Id sang maHo
		Map<Integer, Integer> mapIdToMaho = new HashMap<>();
		List<QuanHeModel> listQuanHe = new QuanHeService().getListQuanHe();
		listQuanHe.forEach(quanhe -> {
			mapIdToMaho.put(quanhe.getIdThanhVien(), quanhe.getMaHo());
		});
		
		// thiet lap cac cot cho tableviews
		colMaNhanKhau.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("id"));
		colTen.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("ten"));
		colGioiTinh.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("gioiTinh"));
		colTuoi.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("ngaySinh"));
		colCCCD.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("cccd"));
		colSDT.setCellValueFactory(new PropertyValueFactory<NhanKhauModel, String>("sdt"));
		colAction.setCellFactory(param -> new TableCell<NhanKhauModel, Void>() {
      
			    private final HBox container = new HBox(8);
			    private final Button deleteButton = new Button("Khai tử");
			    private final Button editButton = new Button("Sửa");
			    private final Button chiTietButton = new Button("Chi tiết");

			    {
			        deleteButton.setOnAction(event -> {
			            try {
			                delNhanKhau();
			            } catch (IOException | ClassNotFoundException | SQLException e) {
			                e.printStackTrace();
			            }
			        });

			        editButton.setOnAction(event -> {
			            try {
			                updateNhanKhau();
			            } catch (IOException | ClassNotFoundException | SQLException e) {
			                e.printStackTrace();
			            }
			        });
			        chiTietButton.setOnAction(event -> {
			            try {
			            	showChiTiet();
			            } catch (IOException | ClassNotFoundException | SQLException e) {
			                e.printStackTrace();
			            }
			        });
			        container.setAlignment(Pos.CENTER);
			        container.getChildren().addAll(chiTietButton,editButton, deleteButton);
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


		try {
//			colMaHo.setCellValueFactory(
//					(CellDataFeatures<NhanKhauModel, String> p) -> new ReadOnlyStringWrapper(mapIdToMaho.get(p.getValue().getId()).toString())
//			);
			colMaHo.setCellValueFactory(
				    (CellDataFeatures<NhanKhauModel, String> p) -> {
				        Integer maHo = mapIdToMaho.get(p.getValue().getId());
				        return new ReadOnlyStringWrapper(maHo != null ? maHo.toString() : "");
				    }
				);

		} catch (Exception e) {
			// TODO: handle exception
		}
		
		tvNhanKhau.setItems(listValueTableView);

		// thiet lap gia tri cho combobox
		ObservableList<String> listComboBox = FXCollections.observableArrayList("Tên", "Tuổi", "Id");
		cbChooseSearch.setValue("Tìm kiếm theo");
		cbChooseSearch.setItems(listComboBox);
	}

	// Tìm kiếm nhân khẩu theo tên, tuổi, ID
	public void searchNhanKhau() {
		ObservableList<NhanKhauModel> listValueTableView_tmp = null;
		String keySearch = tfSearch.getText();

		// lay lua chon tim kiem cua khach hang
		SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
		String typeSearchString = typeSearch.getSelectedItem();

		// tim kiem thong tin theo lua chon da lay ra
		switch (typeSearchString) {
		case "Tên": {
			// neu khong nhap gi -> thong bao loi
			if (keySearch.length() == 0) {
				tvNhanKhau.setItems(listValueTableView);
				Alert alert = new Alert(AlertType.WARNING, "Bạn chưa nhập tên nhân khẩu!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}
			
			int index = 0;	//Số lượng nhân khẩu có tên mà ta tìm được
			List<NhanKhauModel> listNhanhKhauModelsSearch = new ArrayList<>();
			for(NhanKhauModel nhanKhauModel : listNhanKhau) {
				if(nhanKhauModel.getTen().contains(keySearch)) {
					listNhanhKhauModelsSearch.add(nhanKhauModel); //Thêm vào nếu như có nhiều nhân khẩu cùng tên
					index++;
				}
			}
			
			listValueTableView_tmp = FXCollections.observableArrayList(listNhanhKhauModelsSearch);
			tvNhanKhau.setItems(listValueTableView_tmp);
			
			// Nếu không tìm thấy thì thông báo lỗi
			if (index == 0) {
				tvNhanKhau.setItems(listValueTableView); // Hiển thị toàn bộ thông tin
				Alert alert = new Alert(AlertType.INFORMATION, "Không tìm thấy nhân khẩu có tên như vậy!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
			}
			break;
		}
		case "Tuổi": {
			//Không nhập thì thông báo lỗi
			if (keySearch.length() == 0) {
				tvNhanKhau.setItems(listValueTableView);
				Alert alert = new Alert(AlertType.WARNING, "Bạn chưa nhập tuổi!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}

			// Kiểm tra tuổi nhập vào có hợp lệ hay không
			Pattern pattern = Pattern.compile("\\d{1,}");
			if(!pattern.matcher(keySearch).matches()) {
				Alert alert = new Alert(AlertType.WARNING, "Tuổi không hợp lệ!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}
			
			int index = 0; //Số lượng nhân khẩu tìm được
			List<NhanKhauModel> listNhanKhau_tmp = new ArrayList<>();
			for (NhanKhauModel nhanKhauModel : listNhanKhau) {
				if (nhanKhauModel.getTuoi() == Integer.parseInt(keySearch)) {
					listNhanKhau_tmp.add(nhanKhauModel);
					index++;
				}
			}
			listValueTableView_tmp = FXCollections.observableArrayList(listNhanKhau_tmp);
			tvNhanKhau.setItems(listValueTableView_tmp);
			
			// Nếu không tìm thấy thì thông báo cho người dùng
			if (index == 0) {
				tvNhanKhau.setItems(listValueTableView); // Hiển thị toàn bộ thông tin
				Alert alert = new Alert(AlertType.INFORMATION, "Không tìm thấy nhân khẩu có tuổi như vậy!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
			}
			break;
		}
		default: { //Mặc định tìm theo ID
			// Không nhập gì thì thông báo lỗi
			if (keySearch.length() == 0) {
				tvNhanKhau.setItems(listValueTableView);
				Alert alert = new Alert(AlertType.INFORMATION, "Bạn chưa nhập ID", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}

			// Kiểm tra thông tin ID có hợp lệ hay không
			Pattern pattern = Pattern.compile("\\d{1,}");
			if(!pattern.matcher(keySearch).matches()) {
				Alert alert = new Alert(AlertType.WARNING, "ID không hợp lệ!", ButtonType.OK);
				alert.setHeaderText(null);
				alert.showAndWait();
				break;
			}

			for (NhanKhauModel nhanKhauModel : listNhanKhau) {
				if (nhanKhauModel.getId() == Integer.parseInt(keySearch)) {
					listValueTableView_tmp = FXCollections.observableArrayList(nhanKhauModel);
					tvNhanKhau.setItems(listValueTableView_tmp);
					return; 
				}
			}
			
			// Không tìm thấy thì thông báo lỗi
			tvNhanKhau.setItems(listValueTableView);
			Alert alert = new Alert(AlertType.WARNING, "Không tìm thấy nhân khẩu có ID như vậy!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		}
		}
	}

	
	//Thêm nhân khẩu
	public void addNhanKhau(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		Parent home = FXMLLoader.load(getClass().getResource("/views/nhankhau/AddNhanKhau.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Thêm nhân khẩu mới");
        stage.setScene(new Scene(home,800,612));
        stage.setResizable(false);
        stage.showAndWait();
        
        //Update lại thông tin trên label
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
        
        showNhanKhau();
	}
	
	// Xóa nhân khẩu
	public void delNhanKhau() throws IOException, ClassNotFoundException, SQLException {
		//Chọn một nhân khẩu trên table view
		NhanKhauModel nhanKhauModel = tvNhanKhau.getSelectionModel().getSelectedItem();
		int maho = 0;
		
		if(nhanKhauModel == null) {
			Alert alert = new Alert(AlertType.WARNING, "Hãy chọn nhân khẩu bạn muốn xóa!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
		} else {
			// Kiểm tra nếu là chủ hộ
			List<ChuHoModel> listChuHo = new ChuHoService().getListChuHo();
			for(ChuHoModel chuho : listChuHo) {
				if(chuho.getIdChuHo() == nhanKhauModel.getId()) {
					Alert alert = new Alert(AlertType.WARNING, "Bạn không thể xóa nhân khẩu này!", ButtonType.OK);
					alert.setHeaderText("Nhân khẩu này là một chủ hộ!");
					alert.showAndWait();
					return;
				}
			}
			
			Alert alert = new Alert(AlertType.WARNING, "Bạn có chắc muốn xóa nhân khẩu này!", ButtonType.YES, ButtonType.NO);
			alert.setHeaderText(null);
			Optional<ButtonType> result = alert.showAndWait();
			
			if(result.get() == ButtonType.NO) {
				return;
			} else {
				List<QuanHeModel> listQuanHe = new QuanHeService().getListQuanHe();
				for(QuanHeModel quanHeModel : listQuanHe) {
					if(quanHeModel.getIdThanhVien() == nhanKhauModel.getId()) {
						maho = quanHeModel.getMaHo();
						break;
					}
				}
				
				new NhanKhauService().del(nhanKhauModel.getId());
				new QuanHeService().del(maho, nhanKhauModel.getId());
			}
		}
		
		//Update lại thông tin trên label
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
		
		showNhanKhau();
	}
	
	public void updateNhanKhau() throws IOException, ClassNotFoundException, SQLException {
		//Lấy ra nhân khẩu cần update
		NhanKhauModel nhanKhauModel = tvNhanKhau.getSelectionModel().getSelectedItem();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/nhankhau/UpdateNhanKhau.fxml"));
		Parent home = loader.load(); 
        Stage stage = new Stage();
        stage.setTitle("Sửa thông tin nhân khẩu");
        stage.setScene(new Scene(home,800,550));
        UpdateNhanKhau updateNhanKhau = loader.getController();
        
        // Trường hợp chưa chọn nhân khẩu
        if(updateNhanKhau == null) return;
        if(nhanKhauModel == null) {
			Alert alert = new Alert(AlertType.WARNING, "Chọn nhân khẩu cần sửa!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
        
        updateNhanKhau.setNhanKhauModel(nhanKhauModel);
        
        stage.setResizable(false);
        stage.showAndWait();
        showNhanKhau();
	}
	
	//Show thông tin chi tiêt nhân khẩu
	public void showChiTiet() throws IOException, ClassNotFoundException, SQLException {
		//chọn nhân khẩu cần show thông tin
		NhanKhauModel selectNhanKhau = tvNhanKhau.getSelectionModel().getSelectedItem();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/views/nhankhau/ChiTietNhanKhau.fxml"));
		Parent home = loader.load(); 
        Stage stage = new Stage();
        stage.setTitle("Thông tin chi tiết nhân khẩu");
        stage.setScene(new Scene(home,800,550));
        ChiTietNhanKhauController showChiTietNhanKhau = loader.getController();
        
        // Trường hợp không hợp lệ
        if(showChiTietNhanKhau == null) return;
        if(selectNhanKhau == null) {
			Alert alert = new Alert(AlertType.WARNING, "Chọn nhân khẩu cần xem thông tin chi tiết!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
        
        showChiTietNhanKhau.setNhanKhauModel(selectNhanKhau);
        
        stage.setResizable(false);
        stage.showAndWait();
        showNhanKhau();
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
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
			
			
			showNhanKhau();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}