package com.example.quanlynhahang.model;

public class NhanVien {
    private int maNV;

    private String firebaseUID;

    private String tenNV;

    private int sdt;

    private String vaiTro;

    private String taiKhoan;

    private String matkhau;

    private String urlAnh;

    private NhaHang nhaHang;

    public NhanVien() {
    }

    public NhanVien(int maNV, String firebaseUID, int sdt, String tenNV, String vaiTro, String taiKhoan, String urlAnh, String matkhau, NhaHang nhaHang) {
        this.maNV = maNV;
        this.firebaseUID = firebaseUID;
        this.sdt = sdt;
        this.tenNV = tenNV;
        this.vaiTro = vaiTro;
        this.taiKhoan = taiKhoan;
        this.urlAnh = urlAnh;
        this.matkhau = matkhau;
        this.nhaHang = nhaHang;
    }


    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getFirebaseUID() {
        return firebaseUID;
    }

    public void setFirebaseUID(String firebaseUID) {
        this.firebaseUID = firebaseUID;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public int getSdt() {
        return sdt;
    }

    public void setSdt(int sdt) {
        this.sdt = sdt;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getUrlAnh() {
        return urlAnh;
    }

    public void setUrlAnh(String urlAnh) {
        this.urlAnh = urlAnh;
    }

    public NhaHang getNhaHang() {
        return nhaHang;
    }

    public void setNhaHang(NhaHang nhaHang) {
        this.nhaHang = nhaHang;
    }
}
