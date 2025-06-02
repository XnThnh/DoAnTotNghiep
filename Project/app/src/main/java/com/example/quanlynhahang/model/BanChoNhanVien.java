package com.example.quanlynhahang.model;

public class BanChoNhanVien {
    private int maBan;
    private String tenBan;
    private int soChoNgoi;
    private String trangThai;
    private String maNV;

    public BanChoNhanVien() {
    }

    public BanChoNhanVien(int maBan, String tenBan, int soChoNgoi, String trangThai, String maNV) {
        this.maBan = maBan;
        this.tenBan = tenBan;
        this.soChoNgoi = soChoNgoi;
        this.trangThai = trangThai;
        this.maNV = maNV;
    }

    public int getMaBan() {
        return maBan;
    }

    public void setMaBan(int maBan) {
        this.maBan = maBan;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public int getSoChoNgoi() {
        return soChoNgoi;
    }

    public void setSoChoNgoi(int soChoNgoi) {
        this.soChoNgoi = soChoNgoi;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
}
