package com.example.quanlynhahang.model;

public class Ban {

    private int maBan;
    private String tenBan;
    private int soChoNgoi;
    private NhaHang nhaHang;

    public Ban() {
    }

    public Ban(int maBan, String tenBan, int soChoNgoi, NhaHang nhaHang) {
        this.maBan = maBan;
        this.tenBan = tenBan;
        this.soChoNgoi = soChoNgoi;
        this.nhaHang = nhaHang;
    }

    public int getMaBan() {
        return maBan;
    }

    public void setMaBan(int maBan) {
        this.maBan = maBan;
    }

    public int getSoChoNgoi() {
        return soChoNgoi;
    }

    public void setSoChoNgoi(int soChoNgoi) {
        this.soChoNgoi = soChoNgoi;
    }

    public NhaHang getNhaHang() {
        return nhaHang;
    }

    public void setNhaHang(NhaHang nhaHang) {
        this.nhaHang = nhaHang;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }
}
