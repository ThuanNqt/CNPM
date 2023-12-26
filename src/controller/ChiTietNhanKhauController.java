package controller;

import java.awt.TextField;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

import models.NhanKhauModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;



public class ChiTietNhanKhauController implements Initializable{
  
	 private NhanKhauModel nhanKhauModel;

	    public NhanKhauModel getNhanKhauModel() {
	        return nhanKhauModel;
	    }

	    public void setNhanKhauModel(NhanKhauModel nhanKhauModel) throws ClassNotFoundException, SQLException {
	        
	        this.nhanKhauModel = nhanKhauModel;

	        
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			System.out.print(nhanKhauModel.getTen());
			
		}



	
}
