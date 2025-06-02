package com.example.quanlynhahang.model;

public class DonHang {

    private int maDonHang;
    private Ban ban;
    private NhanVien nhanVien;
    private String trangThai;
    private NhaHang nhaHang;

    public DonHang() {
    }

    public DonHang(Ban ban, int maDonHang, NhanVien nhanVien, String trangThai, NhaHang nhaHang) {
        this.ban = ban;
        this.maDonHang = maDonHang;
        this.nhanVien = nhanVien;
        this.trangThai = trangThai;
        this.nhaHang = nhaHang;
    }

    public int getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(int maDonHang) {
        this.maDonHang = maDonHang;
    }

    public Ban getBan() {
        return ban;
    }

    public void setBan(Ban ban) {
        this.ban = ban;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public NhaHang getNhaHang() {
        return nhaHang;
    }

    public void setNhaHang(NhaHang nhaHang) {
        this.nhaHang = nhaHang;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
