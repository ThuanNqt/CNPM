package controller;

import java.awt.Label;


import java.io.IOException;

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
import javafx.scene.control.Button;

public class LoginController {
	/*
	@FXML
	private TextField tfUsername;
	@FXML
	private PasswordField tfPassword;
	@FXML Button btnLogin;
	
	public void Login(ActionEvent event) throws IOException {
		String username = tfUsername.getText();
		String pass = tfPassword.getText();
		
		// check username and password
		if(!username.equals("admin") || !pass.equals("admin")) {
			Alert alert = new Alert(AlertType.ERROR, "Tài khoản hoặc mật khẩu không đúng!", ButtonType.OK);
			alert.setHeaderText(null);
			alert.showAndWait();
			return;
		}
		x
		Parent home = FXMLLoader.load(getClass().getResource("/views/Home3.fxml"));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(home,800,600));
        stage.setResizable(false);
        stage.show();
	}*/
	 

    @FXML
    private Button loginBTN;

    @FXML
    private PasswordField passWord;

    @FXML
    private TextField userName;

    @FXML
    void login(ActionEvent event) throws IOException {
    	Stage stage = (Stage) loginBTN.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/Home3.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("app");
        stage.setScene(scene);
    }

	  

	 
	    
	    

}
