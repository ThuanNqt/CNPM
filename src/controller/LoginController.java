package controller;

import javafx.scene.control.Label;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.Main;
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
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import services.MysqlConnection;
import javafx.scene.control.Button;
import javafx.*;
import javafx.application.Platform;

public class LoginController {
	@FXML
	private TextField tfUsername;
	@FXML
	private PasswordField tfPassword;
	@FXML
	private Button loginBTN;
	@FXML
	private Label lbLoginFail;
	
	public void Login(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		String username = tfUsername.getText();
	    String pass = tfPassword.getText();
	    Platform.runLater(() -> lbLoginFail.setVisible(false)); //chưa hiển thị lbLoginFail
	    
	    try (Connection connection = MysqlConnection.getMysqlConnection()) {
	        String query = "SELECT * FROM users WHERE username = ? AND passwd = ?";
	        
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setString(1, username);
	            preparedStatement.setString(2, pass);
	            
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (!resultSet.next()) {	        
	                	Platform.runLater(() -> lbLoginFail.setVisible(true)); // Hiển thị label lbLoginFail
	                    return;
	                }
	                
	                // Đăng nhập thành công
	              Stage stage = (Stage) loginBTN.getScene().getWindow();
	              FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/Home3.fxml"));
	              Scene scene = new Scene(fxmlLoader.load());
	              stage.setTitle("app");
	              stage.setScene(scene);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
	}	
}
