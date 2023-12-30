package controller.hokhau;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
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

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class TachHoController implements Initializable {
    @FXML
    private TableColumn<NhanKhauModel, Integer> colId;
    @FXML
    private TableColumn<NhanKhauModel, String> colTen;
    @FXML
    private TableColumn<NhanKhauModel, Integer> colQuanHe;
    @FXML
    private TableView<NhanKhauModel> tvThongKe;

    private ObservableList<NhanKhauModel> listValueTableView;
    private HoKhauModel selectedHoKhau;
    private NhanKhauService nhanKhauService;

    // Default constructor
    public TachHoController() {
        nhanKhauService = new NhanKhauService();
    }

    // Method to set the selected KhoanThuModel
    public void setHoKhauModel(HoKhauModel hoKhauModel) {
        this.selectedHoKhau = hoKhauModel;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initializeTableColumns();
        loadNhanKhauData();
    }

    private void initializeTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTen.setCellValueFactory(new PropertyValueFactory<>("ten"));
        colQuanHe.setCellValueFactory(new PropertyValueFactory<>("quanHeChuHo"));
    }

    private void loadNhanKhauData() {
        try {
            List<NhanKhauModel> nhankhauModel = getNhanKhauByHoKhau();
            listValueTableView = FXCollections.observableArrayList(nhankhauModel);
            tvThongKe.setItems(listValueTableView);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<NhanKhauModel> getNhanKhauByHoKhau() throws SQLException, ClassNotFoundException {
        return nhanKhauService.getNhanKhauByHoKhau(selectedHoKhau);
    }
    @FXML
    private TextField tfDiaChi;

    @FXML
    private TextField tfMaHoKhau;
    @FXML
    void tachHok(ActionEvent event) throws SQLException, ClassNotFoundException {
        NhanKhauModel nhanKhauModel = tvThongKe.getSelectionModel().getSelectedItem();

        if(nhanKhauModel == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Hãy chọn nhân khẩu bạn muốn tách!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            // kiem tra dieu kien chu ho
            List<ChuHoModel> listChuHo = new ChuHoService().getListChuHo();
            for(ChuHoModel chuho : listChuHo) {
                if(chuho.getIdChuHo() == nhanKhauModel.getId()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Bạn không thể lựa chọn nhân khẩu này!", ButtonType.OK);
                    alert.setHeaderText("Nhân khẩu này là một chủ hộ!");
                    alert.showAndWait();
                    return;
                }
            }
            if(validateInput()){
                QuanHeService quanHeService = new QuanHeService();
                quanHeService.deleteRelationship(selectedHoKhau.getMaHo(),nhanKhauModel.getId());

                int maHo = Integer.parseInt(tfMaHoKhau.getText());
                String diaChi = tfDiaChi.getText();
                HoKhauModel hk = new HoKhauModel(maHo,0,diaChi);
                addHoKhauToDatabase(hk, nhanKhauModel);
                closeStage(event);
            }

        }
    }
    private void addHoKhauToDatabase(HoKhauModel hoKhauModel, NhanKhauModel nhanKhauModel) throws ClassNotFoundException, SQLException {
        new HoKhauService().add(hoKhauModel);
        //new NhanKhauService().addnk(nhanKhauModel);
        new QuanHeService().add(new QuanHeModel(hoKhauModel.getMaHo(), nhanKhauModel.getId(), "Là chủ hộ"));
        new ChuHoService().add(new ChuHoModel(hoKhauModel.getMaHo(), nhanKhauModel.getId()));
    }
    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    private boolean validateMaHo() throws ClassNotFoundException, SQLException {
        Pattern pattern = Pattern.compile("\\d{1,11}");
        if (!pattern.matcher(tfMaHoKhau.getText()).matches()) {
            showAlert("Hãy nhập vào mã hộ khẩu hợp lệ!");
            return false;
        }

        List<HoKhauModel> listHoKhauModels = new HoKhauService().getListHoKhau();
        for (HoKhauModel hokhau : listHoKhauModels) {
            if (hokhau.getMaHo() == Integer.parseInt(tfMaHoKhau.getText())) {
                showAlert("Mã hộ khẩu bị trùng!");
                return false;
            }
        }
        return true;
    }
    private boolean validateDiaChi() {
        if (tfDiaChi.getText().length() >= 50 || tfDiaChi.getText().length() <= 1) {
            showAlert("Hãy nhập vào địa chỉ hợp lệ!");
            return false;
        }
        return true;
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    private boolean validateInput() throws ClassNotFoundException, SQLException {
        return validateMaHo() && validateDiaChi() ;

    }
}
