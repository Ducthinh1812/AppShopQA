package com.duan1.appshopqa.model;

public class TheLoai {
    public int maTL;
    public int tenTL;

    public TheLoai() {
    }

    public TheLoai(int maTL, int tenTL) {
        this.maTL = maTL;
        this.tenTL = tenTL;
    }

    public int getMaTL() {
        return maTL;
    }

    public void setMaTL(int maTL) {
        this.maTL = maTL;
    }

    public int getTenTL() {
        return tenTL;
    }

    public void setTenTL(int tenTL) {
        this.tenTL = tenTL;
    }

}
