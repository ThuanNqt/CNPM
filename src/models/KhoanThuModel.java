package models;

import java.util.Date;

public class KhoanThuModel {
	private int maKhoanThu;
	private String tenKhoanThu;
	private double soTien;
	private int loaiKhoanThu;// tự nguyện là 1 bắt buộc là 0
	private String hinhThucThu;
	private Date ngayBatDauThu;
	private Date ngayKetThucThu;
	
	public KhoanThuModel() {}
	
	public KhoanThuModel(String tenKhoanThu, double soTien, int loaiKhoanThu ) {
		this.tenKhoanThu=tenKhoanThu;
		this.soTien = soTien;
		this.loaiKhoanThu = loaiKhoanThu;
	}
	
	public KhoanThuModel(int maKhoanThu ,String tenKhoanThu, double soTien, int loaiKhoanThu,String hinhThucThu, Date ngayBatDauThu, Date ngayKetThucThu ) {
		this.maKhoanThu = maKhoanThu;
		this.tenKhoanThu=tenKhoanThu;
		this.soTien = soTien;
		this.loaiKhoanThu = loaiKhoanThu;
		this.hinhThucThu = hinhThucThu;
		this.ngayBatDauThu = ngayBatDauThu;
		this.ngayKetThucThu = ngayKetThucThu;
	}
	public KhoanThuModel(int maKhoanThu ,String tenKhoanThu, double soTien, int loaiKhoanThu ) {
		this.maKhoanThu = maKhoanThu;
		this.tenKhoanThu=tenKhoanThu;
		this.soTien = soTien;
		this.loaiKhoanThu = loaiKhoanThu;
		
	}

	public int getMaKhoanThu() {
		return maKhoanThu;
	}

	public void setMaKhoanThu(int maKhoanThu) {
		this.maKhoanThu = maKhoanThu;
	}

	public String getTenKhoanThu() {
		return tenKhoanThu;
	}

	public void setTenKhoanThu(String tenKhoanThu) {
		this.tenKhoanThu = tenKhoanThu;
	}

	public double getSoTien() {
		return soTien;
	}

	public void setSoTien(double soTien) {
		this.soTien = soTien;
	}

	public int getLoaiKhoanThu() {
		return loaiKhoanThu;
	}

	public void setLoaiKhoanThu(int loaiKhoanThu) {
		this.loaiKhoanThu = loaiKhoanThu;
	}
	public String getHinhThucThu(){
		return hinhThucThu;
	}
	public void setHinhThucThu(String hinhThucThu) {
		this.hinhThucThu = hinhThucThu;
	}

	public Date getNgayBatDauThu() {
		return ngayBatDauThu;
	}

	public void setNgayBatDauThu(Date ngayBatDauThu) {
		this.ngayBatDauThu = ngayBatDauThu;
	}

	public Date getNgayKetThucThu() {
		return ngayKetThucThu;
	}

	public void setNgayKetThucThu(Date ngayKetThucThu) {
		this.ngayKetThucThu = ngayKetThucThu;
	}
	
	
}
