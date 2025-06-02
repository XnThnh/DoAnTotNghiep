package com.example.quanlynhahang.model;

public class MonAnFirebase {
    private int id;
    private String tenMonAn;
    private int giaMonAn;
    private int soLuong;
    private String urlAnh;

    public MonAnFirebase() {
    }

    public String getUrlAnh() {
        return urlAnh;
    }

    public void setUrlAnh(String urlAnh) {
        this.urlAnh = urlAnh;
    }

    public MonAnFirebase(int id, String tenMonAn, int soLuong, int giaMonAn, String urlAnh) {
        this.id = id;
        this.tenMonAn = tenMonAn;
        this.soLuong = soLuong;
        this.giaMonAn = giaMonAn;
        this.urlAnh = urlAnh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public int getGiaMonAn() {
        return giaMonAn;
    }

    public void setGiaMonAn(int giaMonAn) {
        this.giaMonAn = giaMonAn;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
