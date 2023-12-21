package controller;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



import java.io.IOException;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import application.Main;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import models.HoKhauModel;
import models.KhoanThuModel;
import models.NhanKhauModel;
import models.NopTienModel;
import services.KhoanThuService;
import services.NopTienService;
import services.ThongKeService;

public class ThongKeController implements Initializable {
	@FXML
	TableColumn<KhoanThuModel, String> colTenPhi;
	@FXML
	TableColumn<KhoanThuModel, String> colTongSoTien;
	@FXML
	TableView<KhoanThuModel> tvThongKe;
	@FXML
	ComboBox<String> cbChooseSearch;
	@FXML
	private TableColumn<KhoanThuModel, Void> colAction;

	
	private ObservableList<KhoanThuModel> listValueTableView;
	private List<KhoanThuModel> listKhoanThu;

	public void showThongKe() throws ClassNotFoundException, SQLException {
		listKhoanThu = new KhoanThuService().getListKhoanThu();
		listValueTableView = FXCollections.observableArrayList(listKhoanThu);

		List<NopTienModel> listNopTien = new NopTienService().getListNopTien();
		Map<Integer, Long> mapMaKhoanThuToSoLuong = new TreeMap<>();
		for (KhoanThuModel khoanThuModel : listKhoanThu) {
			long cntNopTien = listNopTien.stream()
					.filter(noptien -> noptien.getMaKhoanThu() == khoanThuModel.getMaKhoanThu()).count();
			mapMaKhoanThuToSoLuong.put(khoanThuModel.getMaKhoanThu(), cntNopTien);
		}

		// thiet lap cac cot cho tableviews
		colTenPhi.setCellValueFactory(new PropertyValueFactory<KhoanThuModel, String>("tenKhoanThu"));
		try {
			colTongSoTien.setCellValueFactory(
					(CellDataFeatures<KhoanThuModel, String> p) -> new ReadOnlyStringWrapper(Double.toString(
							mapMaKhoanThuToSoLuong.get(p.getValue().getMaKhoanThu()) * p.getValue().getSoTien())));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		colAction.setCellFactory(param -> new TableCell<KhoanThuModel, Void>() {
		    private final HBox container = new HBox();
		    private final Button btn = new Button("Các hộ chưa nộp");

		    {
		    	 btn.setOnAction(event -> {
		            goThongKe2();
		        });
		        container.setAlignment(Pos.CENTER);
		        container.getChildren().addAll(btn);
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

		tvThongKe.setItems(listValueTableView);
		// thiet lap gia tri cho combobox
		ObservableList<String> listComboBox = FXCollections.observableArrayList("Bắt buộc", "Tự nguyện");
		cbChooseSearch.setValue("Thống kê theo");
		cbChooseSearch.setItems(listComboBox);
	}

	public void loc() {
		ObservableList<KhoanThuModel> listValueTableView_tmp = null;
		
		List<KhoanThuModel> listKhoanThuBatBuoc = new ArrayList<>();
		List<KhoanThuModel> listKhoanThuTuNguyen = new ArrayList<>();
		for (KhoanThuModel khoanThuModel : listKhoanThu) {
			if (khoanThuModel.getLoaiKhoanThu() == 0) {
				listKhoanThuTuNguyen.add(khoanThuModel);
			} else {
				listKhoanThuBatBuoc.add(khoanThuModel);
			}
		}

		// lay lua chon tim kiem cua khach hang
		SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
		String typeSearchString = typeSearch.getSelectedItem();
		
		switch (typeSearchString) {
		case "Tất cả":
			tvThongKe.setItems(listValueTableView);
			break;
		case "Bắt buộc":
			listValueTableView_tmp = FXCollections.observableArrayList(listKhoanThuBatBuoc);
			tvThongKe.setItems(listValueTableView_tmp);
			break;
		case "Tự nguyện":
			listValueTableView_tmp = FXCollections.observableArrayList(listKhoanThuTuNguyen);
			tvThongKe.setItems(listValueTableView_tmp);
			break;
		default:
			break;
		}
	}
	public KhoanThuModel thongkeKhoanThu() throws IOException, ClassNotFoundException, SQLException {
	    KhoanThuModel khoanThuModel = tvThongKe.getSelectionModel().getSelectedItem();
	    return khoanThuModel;
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			showThongKe();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	  @FXML
	 
	  private void goThongKe2() {
	        
	        KhoanThuModel selectedKhoanThuModel = tvThongKe.getSelectionModel().getSelectedItem();
	        
	        if (selectedKhoanThuModel != null) {
	            try {
	                
	                ThongKe2Controller thongKe2Controller = new ThongKe2Controller();
	                thongKe2Controller.setKhoanThuModel(selectedKhoanThuModel);

	                // Load ThongKe2 FXML
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/thongke2.fxml"));
	                loader.setController(thongKe2Controller);
	                Parent root = loader.load();

	                // Show ThongKe2 stage
	                Stage stage = new Stage();
	                stage.setScene(new Scene(root));
	                stage.show();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	
  }
