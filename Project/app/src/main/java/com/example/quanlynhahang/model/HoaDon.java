package com.example.quanlynhahang.model;

import java.time.LocalDateTime;

public class HoaDon {

    private int id;
    private String thoiGian;
    private double tongTien;
    private String phuongThuc;
    private DonHang donHang;
    private NhanVien nhanVien;
    private NhaHang nhaHang;

    public HoaDon() {
    }

    public HoaDon(int id, String thoiGian, double tongTien, String phuongThuc, DonHang donHang, NhanVien nhanVien, NhaHang nhaHang) {
        this.id = id;
        this.thoiGian = thoiGian;
        this.tongTien = tongTien;
        this.phuongThuc = phuongThuc;
        this.donHang = donHang;
        this.nhanVien = nhanVien;
        this.nhaHang = nhaHang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getPhuongThuc() {
        return phuongThuc;
    }

    public void setPhuongThuc(String phuongThuc) {
        this.phuongThuc = phuongThuc;
    }

    public DonHang getDonHang() {
        return donHang;
    }

    public void setDonHang(DonHang donHang) {
        this.donHang = donHang;
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

    @Override
    public String toString() {
        return "HoaDon{" +
                "id=" + id +
                ", thoiGian='" + thoiGian + '\'' +
                ", tongTien=" + tongTien +
                ", phuongThuc='" + phuongThuc + '\'' +
                ", donHang=" + donHang +
                ", nhanVien=" + nhanVien +
                ", nhaHang=" + nhaHang +
                '}';
    }
}
