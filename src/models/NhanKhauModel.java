package models;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class NhanKhauModel {
    private int id;
    private String cccd;
    private String ten;
    private String gioiTinh;
    private Date ngaySinh;
    private String sdt;

    public NhanKhauModel() {
    }

    public NhanKhauModel(String cccd, String ten, String gioiTinh,Date ngaySinh, String sdt) {
        this.cccd = cccd;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
    }

    public NhanKhauModel(int id, String cccd, String ten,String gioiTinh, Date ngaySinh, String sdt) {
        this.id = id;
        this.cccd = cccd;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
    }

    public NhanKhauModel(int id, String cccd, String ten,String gioiTinh, String sdt) {
        this.id = id;
        this.cccd = cccd;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
    
    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
    private LocalDate convertToLocalDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }
    public int getTuoi() {
        LocalDate birthDate = convertToLocalDate(ngaySinh);
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }
    
}
