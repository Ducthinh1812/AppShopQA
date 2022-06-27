package com.duan1.appshopqa.model;

public class KhachHang {
    public String MaKH;
    public String TenKH;
    public String DiaChiKH;
    public String NamSinh;
    public String SDT;
    public String Gmail;
    public String MatKhau;

    public KhachHang() {
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String maKH) {
        MaKH = maKH;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String tenKH) {
        TenKH = tenKH;
    }

    public String getDiaChiKH() {
        return DiaChiKH;
    }

    public void setDiaChiKH(String diaChiKH) {
        DiaChiKH = diaChiKH;
    }

    public String getNamSinh() {
        return NamSinh;
    }

    public void setNamSinh(String namSinh) {
        NamSinh = namSinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getGmail() {
        return Gmail;
    }

    public void setGmail(String gmail) {
        Gmail = gmail;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public KhachHang(String maKH, String tenKH, String diaChiKH, String namSinh, String SDT, String gmail, String matKhau) {
        MaKH = maKH;
        TenKH = tenKH;
        DiaChiKH = diaChiKH;
        NamSinh = namSinh;
        this.SDT = SDT;
        Gmail = gmail;
        MatKhau = matKhau;
    }
}
