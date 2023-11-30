package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private TextField tfUsername;
	@FXML
	private PasswordField tfPassword;
	
	public void Login(ActionEvent event) throws IOException {
		String username = tfUsername.getText();
		String pass = tfPassword.getText();
		
		// check username and password
		if(!username.equals("admin") || !pass.equals("admin")) {
			Alert alert = new Alert(AlertType.WARNING, "Tài khoản hoặc mật khẩu không đúng!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		
		Parent home = FXMLLoader.load(getClass().getResource("/views/Home3.fxml"));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(home,800,600));
        stage.setResizable(false);
        stage.show();
	}

}
