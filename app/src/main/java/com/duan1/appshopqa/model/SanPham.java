package com.duan1.appshopqa.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    public int maSP;
    public String tenSP;
    public String hinhAnhSP;
    public String moTaSP;
    public int soLuongNhap;
    public int giaBan;
    public int maTL;

    public SanPham(int maSP, String tenSP, String hinhAnhSP, String moTaSP, int soLuongNhap, int giaBan, int maTL) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.hinhAnhSP = hinhAnhSP;
        this.moTaSP = moTaSP;
        this.soLuongNhap = soLuongNhap;
        this.giaBan = giaBan;
        this.maTL = maTL;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getHinhAnhSP() {
        return hinhAnhSP;
    }

    public void setHinhAnhSP(String hinhAnhSP) {
        this.hinhAnhSP = hinhAnhSP;
    }

    public String getMoTaSP() {
        return moTaSP;
    }

    public void setMoTaSP(String moTaSP) {
        this.moTaSP = moTaSP;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public int getMaTL() {
        return maTL;
    }

    public void setMaTL(int maTL) {
        this.maTL = maTL;
    }
}
