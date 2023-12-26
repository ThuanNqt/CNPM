package controller.noptien;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.KhoanThuModel;
import services.KhoanThuService;

//Import statements...

public class ChooseKhoanNop implements Initializable {
 @FXML private TableView<KhoanThuModel> tvKhoanPhi;
 @FXML private TableColumn<KhoanThuModel, String> colMaKhoanPhi, colTenKhoanThu, colSoTien, colLoaiKhoanThu;
 @FXML private TextField tfSearch;
 @FXML private ComboBox<String> cbChooseSearch;

 private KhoanThuModel khoanthuChoose;
 private List<KhoanThuModel> listKhoanThu;
 private ObservableList<KhoanThuModel> listValueTableView;

 public KhoanThuModel getKhoanthuChoose() {
     return khoanthuChoose;
 }

 public void setKhoanthuChoose(KhoanThuModel khoanthuChoose) {
     this.khoanthuChoose = khoanthuChoose;
 }

 public void showKhoanThu() {
     try {
         listKhoanThu = new KhoanThuService().getListKhoanThu();
         listValueTableView = FXCollections.observableArrayList(listKhoanThu);

         configureTableColumns();
         configureComboBox();

         tvKhoanPhi.setItems(listValueTableView);
     } catch (ClassNotFoundException | SQLException e) {
         e.printStackTrace(); // Handle or log the exception appropriately
     }
 }

 private void configureTableColumns() {
     colMaKhoanPhi.setCellValueFactory(new PropertyValueFactory<>("maKhoanThu"));
     colTenKhoanThu.setCellValueFactory(new PropertyValueFactory<>("tenKhoanThu"));
     colSoTien.setCellValueFactory(new PropertyValueFactory<>("soTien"));
     colLoaiKhoanThu.setCellValueFactory(cellData -> {
         int loaiKhoanThu = cellData.getValue().getLoaiKhoanThu();
         return new ReadOnlyStringWrapper(getLoaiKhoanThuString(loaiKhoanThu));
     });
 }

 private String getLoaiKhoanThuString(int loaiKhoanThu) {
     Map<Integer, String> mapLoaiKhoanThu = Map.of(1, "Bắt buộc", 0, "Tự nguyện");
     return mapLoaiKhoanThu.get(loaiKhoanThu);
 }

 private void configureComboBox() {
     ObservableList<String> listComboBox = FXCollections.observableArrayList("Tên khoản thu", "Mã khoản thu");
     cbChooseSearch.setValue("Tìm kiếm theo");
     cbChooseSearch.setItems(listComboBox);
 }

 public void searchKhoanThu() {
	    String keySearch = tfSearch.getText();
	    SingleSelectionModel<String> typeSearch = cbChooseSearch.getSelectionModel();
	    String typeSearchString = typeSearch.getSelectedItem();

	    switch (typeSearchString) {
	        case "Tên khoản thu":
	            searchByName(keySearch);
	            break;
	        default:
	            searchById(keySearch);
	            break;
	    }
	}

	private void searchByName(String keySearch) {
	    if (keySearch.isEmpty()) {
	        showAllDataWithWarning("Hãy nhập vào tên khoản thu!");
	        return;
	    }

	    List<KhoanThuModel> filteredList = listKhoanThu.stream()
	            .filter(model -> model.getTenKhoanThu().contains(keySearch))
	            .collect(Collectors.toList());

	    updateTableViewAndShowResult(filteredList, "Không tìm thấy!");
	}

	private void searchById(String keySearch) {
	    if (keySearch.isEmpty()) {
	        showAllDataWithWarning("Hãy nhập vào mã khoản thu!");
	        return;
	    }

	    if (!isValidNumber(keySearch)) {
	        showAlert(AlertType.WARNING, "Bạn phải nhập vào một số!");
	        return;
	    }

	    int searchId = Integer.parseInt(keySearch);

	    List<KhoanThuModel> filteredList = listKhoanThu.stream()
	            .filter(model -> model.getMaKhoanThu() == searchId)
	            .collect(Collectors.toList());

	    updateTableViewAndShowResult(filteredList, "Không tìm thấy!");
	}

	private boolean isValidNumber(String input) {
	    Pattern pattern = Pattern.compile("\\d{1,}");
	    return pattern.matcher(input).matches();
	}

	private void showAllDataWithWarning(String warningMessage) {
	    tvKhoanPhi.setItems(listValueTableView);
	    showAlert(AlertType.WARNING, warningMessage);
	}

	private void updateTableViewAndShowResult(List<KhoanThuModel> filteredList, String notFoundMessage) {
	    ObservableList<KhoanThuModel> updatedList = FXCollections.observableArrayList(filteredList);
	    tvKhoanPhi.setItems(updatedList);

	    if (filteredList.isEmpty()) {
	        showAllDataWithWarning(notFoundMessage);
	    }
	}

	private void showAlert(AlertType alertType, String message) {
	    Alert alert = new Alert(alertType, message, ButtonType.OK);
	    alert.setHeaderText(null);
	    alert.showAndWait();
	}


 public void xacnhan(ActionEvent event) {
     khoanthuChoose = tvKhoanPhi.getSelectionModel().getSelectedItem();
     setKhoanthuChoose(khoanthuChoose);

     Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     stage.close();
 }

 @Override
 public void initialize(URL arg0, ResourceBundle arg1) {
     showKhoanThu();
 }
}

