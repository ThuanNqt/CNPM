package controller.nhankhau;

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
        this.nhanKhauModel = nhanKhauModel;

        // Convert java.util.Date to LocalDate for DatePicker
        java.util.Date ngaySinhUtilDate = new java.util.Date(nhanKhauModel.getNgaySinh().getTime());
        LocalDate localDate = ngaySinhUtilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dpNgaySinh.setValue(localDate);

        // Set values for TextFields and make them uneditable
        tfHoTen.setText(nhanKhauModel.getTen());
        tfHoTen.setEditable(false);

        tfGioiTinh.setText(nhanKhauModel.getGioiTinh());
        tfGioiTinh.setEditable(false);

        tfSoDienThoai.setText(nhanKhauModel.getSdt());
        tfSoDienThoai.setEditable(false);

        tfCCCD.setText(nhanKhauModel.getCccd());
        tfCCCD.setEditable(false);

        tfBietDanh.setText(nhanKhauModel.getBietDanh());
        tfBietDanh.setEditable(false);

        tfDanToc.setText(nhanKhauModel.getDanToc());
        tfDanToc.setEditable(false);

        tfNoiThuongTru.setText(nhanKhauModel.getNoiThuongTru());
        tfNoiThuongTru.setEditable(false);

        tfNgheNghiep.setText(nhanKhauModel.getNgheNghiep());
        tfNgheNghiep.setEditable(false);

        tfNoiLamViec.setText(nhanKhauModel.getNoiLamViec());
        tfNoiLamViec.setEditable(false);

        tfNguyenQuan.setText(nhanKhauModel.getNguyenQuan());
        tfNguyenQuan.setEditable(false);

        tfNoiCap.setText(nhanKhauModel.getNoiCapCCCD());
        tfNoiCap.setEditable(false);
    }
}
