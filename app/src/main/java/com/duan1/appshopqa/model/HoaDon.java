package com.duan1.appshopqa.model;

public class HoaDon {
    String maHD;
    int maKH;
    String maNV;
    String ngayTao;

    public HoaDon(String hoaDon, int maKH, String maNV, String ngayTao) {
        this.maHD = hoaDon;
        this.maKH = maKH;
        this.maNV = maNV;
        this.ngayTao = ngayTao;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setHoaDon(String hoaDon) {
        this.maHD = hoaDon;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }
}