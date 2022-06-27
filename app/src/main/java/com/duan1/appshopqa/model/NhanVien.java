package com.duan1.appshopqa.model;

public class NhanVien {
    public int MaNV;
    public String TenNV;
    public String GmailNV;
    public String MatkhauNV;
public NhanVien(){

}

    public NhanVien(int maNV, String tenNV, String gmailNV, String matkhauNV) {
        MaNV = maNV;
        TenNV = tenNV;
        GmailNV = gmailNV;
        MatkhauNV = matkhauNV;
    }

    public int getMaNV() {
        return MaNV;
    }

    public void setMaNV(int maNV) {
        MaNV = maNV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String tenNV) {
        TenNV = tenNV;
    }

    public String getGmailNV() {
        return GmailNV;
    }

    public void setGmailNV(String gmailNV) {
        GmailNV = gmailNV;
    }

    public String getMatkhauNV() {
        return MatkhauNV;
    }

    public void setMatkhauNV(String matkhauNV) {
        MatkhauNV = matkhauNV;
    }
}
