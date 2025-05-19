package com.example.quanlynhahang.model;

public class MonAn {
    private int id;

    private String tenMonAn;
    private String moTa;
    private int giaMonAn;
    private String urlAnh;
    private DanhMuc danhMuc;
    private NhaHang nhaHang;

    public MonAn() {
    }

    public MonAn(String moTa, int id, String tenMonAn, int giaMonAn, String urlAnh, DanhMuc danhMuc, NhaHang nhaHang) {
        this.moTa = moTa;
        this.id = id;
        this.tenMonAn = tenMonAn;
        this.giaMonAn = giaMonAn;
        this.urlAnh = urlAnh;
        this.danhMuc = danhMuc;
        this.nhaHang = nhaHang;
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

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getGiaMonAn() {
        return giaMonAn;
    }

    public void setGiaMonAn(int giaMonAn) {
        this.giaMonAn = giaMonAn;
    }

    public String getUrlAnh() {
        return urlAnh;
    }

    public void setUrlAnh(String urlAnh) {
        this.urlAnh = urlAnh;
    }

    public DanhMuc getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;
    }

    public NhaHang getNhaHang() {
        return nhaHang;
    }

    public void setNhaHang(NhaHang nhaHang) {
        this.nhaHang = nhaHang;
    }
}
