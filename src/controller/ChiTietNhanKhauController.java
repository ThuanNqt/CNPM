package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.NhanKhauModel;

public class ChiTietNhanKhauController {
    @FXML
    private TextField tfHoTen;
    @FXML
    private DatePicker dpNgaySinh;
    @FXML
    private TextField tfBietDanh;
    @FXML
    private TextField tfGioiTinh;
    @FXML
    private TextField tfSoDienThoai;
    @FXML
    private TextField tfCCCD;
    @FXML
    private TextField tfDanToc;
    @FXML
    private TextField tfNoiThuongTru;
    @FXML
    private TextField tfNgheNghiep;
    @FXML
    private TextField tfNoiLamViec;
    @FXML
    private TextField tfNguyenQuan;
    @FXML
    private TextField tfNoiCap;
    

    private NhanKhauModel nhanKhauModel;

    public NhanKhauModel getNhanKhauModel() {
        return nhanKhauModel;
    }

    public void setNhanKhauModel(NhanKhauModel nhanKhauModel) throws ClassNotFoundException, SQLException {
        // Set the NhanKhauModel and populate the form fields
        this.nhanKhauModel = nhanKhauModel;

        

        java.util.Date ngaySinhUtilDate = new java.util.Date(nhanKhauModel.getNgaySinh().getTime());
        LocalDate localDate = ngaySinhUtilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dpNgaySinh.setValue(localDate);
        tfHoTen.setText(nhanKhauModel.getTen());
        tfHoTen.setEditable(false);
        tfGioiTinh.setText(nhanKhauModel.getGioiTinh());
        tfSoDienThoai.setText(nhanKhauModel.getSdt());
        tfCCCD.setText(nhanKhauModel.getCccd());
        tfBietDanh.setText(nhanKhauModel.getBietDanh());
        tfDanToc.setText(nhanKhauModel.getDanToc());
        tfNoiThuongTru.setText(nhanKhauModel.getNoiThuongTru());
        tfNgheNghiep.setText(nhanKhauModel.getNgheNghiep());
        tfNoiLamViec.setText(nhanKhauModel.getNoiLamViec());
        tfNguyenQuan.setText(nhanKhauModel.getNguyenQuan());
        tfNoiCap.setText(nhanKhauModel.getNoiCapCCCD());
        
    }
}