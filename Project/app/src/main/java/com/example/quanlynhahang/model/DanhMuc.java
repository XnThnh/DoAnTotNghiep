package com.example.quanlynhahang.model;

import androidx.annotation.NonNull;

public class DanhMuc {

    private int maDanhMuc;
    private String tenDanhMuc;

    private NhaHang nhaHang;

    public DanhMuc() {
    }

    public DanhMuc(int maDanhMuc, String tenDanhMuc, NhaHang nhaHang) {
        this.maDanhMuc = maDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
        this.nhaHang = nhaHang;
    }

    public int getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(int maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public NhaHang getNhaHang() {
        return nhaHang;
    }

    public void setNhaHang(NhaHang nhaHang) {
        this.nhaHang = nhaHang;
    }

    @NonNull
    @Override
    public String toString() {
        return tenDanhMuc;
    }
}
