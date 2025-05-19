package com.example.quanlynhahang.repository;

import com.example.quanlynhahang.model.NhaHang;
import com.example.quanlynhahang.model.NhanVien;
import com.example.quanlynhahang.retrofit.NhaHangAPI;
import com.example.quanlynhahang.retrofit.NhanVienAPI;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class NhanVienRepository {
    private static NhanVienRepository instance; // <-- Tên ngắn gọn
    private static final Object LOCK = new Object();

    public static NhanVienRepository getInstance() { // <-- Tên ngắn gọn
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    NhanVienAPI apiSource = com.example.quanlynhahang.retrofit.RetrofitClientJava.getNhanVienAPI();
                    instance = new NhanVienRepository(apiSource); //
                }
            }
        }
        return instance;
    }
    private NhanVienAPI nhanVienAPI;

    public NhanVienRepository(NhanVienAPI nhanVienAPI) {
        this.nhanVienAPI = nhanVienAPI;
    }
    public Single<NhanVien> dangNhap(String firebaseuid) {
        return nhanVienAPI.dangNhap(firebaseuid);
    }
    public Single<List<NhanVien>> layDanhSachNV(int manh) {
        return nhanVienAPI.getNhanVienByNhaHang(manh);
    }
    public Single<NhanVien> themNhanVien(NhanVien nhanVien) {
        return nhanVienAPI.themNhanVien(nhanVien);
    }
    public Single<NhanVien> capNhatNhanVien(NhanVien nhanVien) {
        return nhanVienAPI.capNhatNhanVien(nhanVien.getMaNV(), nhanVien);
    }
    public Completable xoaNhanVien(NhanVien nhanVien) {
        return nhanVienAPI.xoaNhanVien(nhanVien.getMaNV());
    }
    }
