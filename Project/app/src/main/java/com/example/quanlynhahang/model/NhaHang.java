package com.example.quanlynhahang.model;

import com.google.gson.annotations.SerializedName;

public class NhaHang {

    private int maNH;

    private String firebaseUID;

    private String tenNH;

    private String diaChi;

    private String taiKhoan;

    private String matKhau;
    
    private int sdt;

    public NhaHang() {
    }

    public NhaHang(String firebaseUID, String diaChi, String tenNH, String taiKhoan, String matKhau, int sdt) {
        this.firebaseUID = firebaseUID;
        this.diaChi = diaChi;
        this.tenNH = tenNH;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.sdt = sdt;
    }

    public NhaHang(int maNH, String firebaseUID, String tenNH, String diaChi, String taiKhoan, String matKhau, int sdt) {
        this.maNH = maNH;
        this.firebaseUID = firebaseUID;
        this.tenNH = tenNH;
        this.diaChi = diaChi;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.sdt = sdt;
    }

    public String getFirebaseUID() {
        return firebaseUID;
    }

    public void setFirebaseUID(String firebaseUID) {
        this.firebaseUID = firebaseUID;
    }

    public int getMaNH() {
        return maNH;
    }

    public void setMaNH(int maNH) {
        this.maNH = maNH;
    }

    public String getTenNH() {
        return tenNH;
    }

    public void setTenNH(String tenNH) {
        this.tenNH = tenNH;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getSdt() {
        return sdt;
    }

    public void setSdt(int sdt) {
        this.sdt = sdt;
    }

    @Override
    public String toString() {
        return "NhaHang{" +
                "maNH=" + maNH +
                ", firebaseUID='" + firebaseUID + '\'' +
                ", tenNH='" + tenNH + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", taiKhoan='" + taiKhoan + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", sdt=" + sdt +
                '}';
    }
}
