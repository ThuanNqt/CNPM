package models;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class NhanKhauModel {
    private int id;
    private String cmnd;
    private String ten;
    private Date ngaySinh;
    private String sdt;

    public NhanKhauModel() {
    }

    public NhanKhauModel(String cmnd, String ten, Date ngaySinh, String sdt) {
        this.cmnd = cmnd;
        this.ten = ten;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
    }

    public NhanKhauModel(int id, String cmnd, String ten, Date ngaySinh, String sdt) {
        this.id = id;
        this.cmnd = cmnd;
        this.ten = ten;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
    }

    public NhanKhauModel(int id, String cmnd, String ten, String sdt) {
        this.id = id;
        this.cmnd = cmnd;
        this.ten = ten;
        this.sdt = sdt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
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
