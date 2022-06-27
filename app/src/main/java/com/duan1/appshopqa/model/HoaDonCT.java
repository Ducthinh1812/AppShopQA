package com.duan1.appshopqa.model;

public class HoaDonCT {
    public int idsp;
    public String tensp;
    public String moTaSP;
    public long giasp;
    public String hinhsp;
    public int soluongsp;

    public HoaDonCT(int idsp, String tensp, String moTaSP, long giasp, String hinhsp, int soluongsp) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.moTaSP = moTaSP;
        this.giasp = giasp;
        this.hinhsp = hinhsp;
        this.soluongsp = soluongsp;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getMoTaSP() {
        return moTaSP;
    }

    public void setMoTaSP(String moTaSP) {
        this.moTaSP = moTaSP;
    }

    public long getGiasp() {
        return giasp;
    }

    public void setGiasp(long giasp) {
        this.giasp = giasp;
    }

    public String getHinhsp() {
        return hinhsp;
    }

    public void setHinhsp(String hinhsp) {
        this.hinhsp = hinhsp;
    }

    public int getSoluongsp() {
        return soluongsp;
    }

    public void setSoluongsp(int soluongsp) {
        this.soluongsp = soluongsp;
    }
}
