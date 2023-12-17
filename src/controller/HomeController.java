package controller;

import java.io.IOException;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;



public class HomeController implements Initializable {
	@FXML
	private BorderPane borderPane;
	
	public void setNhanKhau(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/NhanKhau.fxml"));
		Pane nhankhauPane = (Pane) loader.load();
		borderPane.setCenter(nhankhauPane);
	}

	public void setHoKhau(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/HoKhau.fxml"));
		Pane hokhauPane = (Pane) loader.load();
		borderPane.setCenter(hokhauPane);

	}

	public void setKhoanPhi(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/KhoanThu.fxml"));
		Pane khoanphiPane = (Pane) loader.load();
		borderPane.setCenter(khoanphiPane);
	}
	
	public void setDongPhi(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/NopTien.fxml"));
		Pane dongphiPane = (Pane) loader.load();
		borderPane.setCenter(dongphiPane);
	}
	
	public void setThongKe(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/ThongKe.fxml"));
		Pane thongkePane = (Pane) loader.load();
		borderPane.setCenter(thongkePane);

	}
	
	public void setTrangChu(ActionEvent event) throws IOException {
//		FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views/Main.fxml"));
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Main.fxml"));
		Pane trangchuPane = (Pane) loader.load();
		borderPane.setCenter(trangchuPane);

	}
	
	@FXML
//	public void setDangXuat(ActionEvent event) throws IOException {
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
//	    Parent root = null;
//	    try {
//	        root = loader.load();
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	    Stage stage = (Stage) borderPane.getScene().getWindow();
//	    stage.setScene(new Scene(root));
//	    stage.show(); // Thêm dòng này để hiển thị lại Stage
//	}
	
	public void setDangXuat(ActionEvent event) throws IOException {
        // Tạo một Alert với loại CONFIRMATION
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn đăng xuất?");
       
        // Hiển thị Alert và chờ người dùng phản hồi
        Optional<ButtonType> result = alert.showAndWait();

        // Kiểm tra xem người dùng đã chọn OK hoặc Cancel
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Nếu chọn OK, thực hiện đăng xuất
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) borderPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            // Người dùng chọn Cancel hoặc đóng cửa sổ Alert, không làm gì cả
        }
    }

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Pane login = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
			borderPane.setCenter(login);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
