package models;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class NhanKhauModel {
    private int id;
    private String cccd;
    private String ten;
    private String gioiTinh;
    private Date ngaySinh;
    private String sdt;
    private String bietDanh;
    private String danToc;
    private String noiThuongTru;
    private String ngheNghiep;
    private String noiLamViec;
    private String nguyenQuan;
    private String noiCapCCCD;

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
    
    

    public NhanKhauModel(int id, String cccd, String ten, String gioiTinh, Date ngaySinh, String sdt, String bietDanh,
			String danToc, String noiThuongTru, String ngheNghiep, String noiLamViec, String nguyenQuan,
			String noiCapCCCD) {
		super();
		this.id = id;
		this.cccd = cccd;
		this.ten = ten;
		this.gioiTinh = gioiTinh;
		this.ngaySinh = ngaySinh;
		this.sdt = sdt;
		this.bietDanh = bietDanh;
		this.danToc = danToc;
		this.noiThuongTru = noiThuongTru;
		this.ngheNghiep = ngheNghiep;
		this.noiLamViec = noiLamViec;
		this.nguyenQuan = nguyenQuan;
		this.noiCapCCCD = noiCapCCCD;
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
    public String getBietDanh() {
		return bietDanh;
	}
    public String getDanToc() {
		return danToc;
	}
    public String getNgheNghiep() {
		return ngheNghiep;
	}
    public String getNguyenQuan() {
		return nguyenQuan;
	}
    public String getNoiCapCCCD() {
		return noiCapCCCD;
	}
    public String getNoiLamViec() {
		return noiLamViec;
	}
    public String getNoiThuongTru() {
		return noiThuongTru;
	}
    public void setBietDanh(String bietDanh) {
		this.bietDanh = bietDanh;
	}
    public void setDanToc(String danToc) {
		this.danToc = danToc;
	}
    public void setNgheNghiep(String ngheNghiep) {
		this.ngheNghiep = ngheNghiep;
	}
    public void setNguyenQuan(String nguyenQuan) {
		this.nguyenQuan = nguyenQuan;
	}
    public void setNoiCapCCCD(String noiCapCCCD) {
		this.noiCapCCCD = noiCapCCCD;
	}
    public void setNoiLamViec(String noiLamViec) {
		this.noiLamViec = noiLamViec;
	}
    public void setNoiThuongTru(String noiThuongTru) {
		this.noiThuongTru = noiThuongTru;
	}
    
}
