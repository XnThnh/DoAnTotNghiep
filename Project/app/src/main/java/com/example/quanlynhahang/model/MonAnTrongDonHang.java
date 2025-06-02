package com.example.quanlynhahang.model;

public class MonAnTrongDonHang {
    private int id;
    private String trangThai;
    private int donGia;
    private int soLuong;

    private MonAn monAn;

    private DonHang donHang;

    public MonAnTrongDonHang() {
    }

    public MonAnTrongDonHang(int id, String trangThai, int donGia, int soLuong, MonAn monAn, DonHang donHang) {
        this.id = id;
        this.trangThai = trangThai;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.monAn = monAn;
        this.donHang = donHang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public MonAn getMonAn() {
        return monAn;
    }

    public void setMonAn(MonAn monAn) {
        this.monAn = monAn;
    }

    public DonHang getDonHang() {
        return donHang;
    }

    public void setDonHang(DonHang donHang) {
        this.donHang = donHang;
    }
}
