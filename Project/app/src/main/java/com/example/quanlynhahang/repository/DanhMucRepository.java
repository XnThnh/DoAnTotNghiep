package com.example.quanlynhahang.repository;

import com.example.quanlynhahang.model.DanhMuc;
import com.example.quanlynhahang.retrofit.DanhMucAPI;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class DanhMucRepository {
    public static DanhMucRepository instance ;
    private static final Object LOCK = new Object();
    private DanhMucAPI danhMucAPI;

    public static DanhMucRepository getInstance() {
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    DanhMucAPI danhMucAPI = com.example.quanlynhahang.retrofit.RetrofitClientJava.getDanhMucAPI();
                    instance = new DanhMucRepository(danhMucAPI);
                }
            }
        }
        return instance;
    }

    public DanhMucRepository(DanhMucAPI danhMucAPI) {
        this.danhMucAPI =  danhMucAPI;
    }
    public Single<List<DanhMuc>> layDanhSachDanhMuc(int manh){
        return danhMucAPI.getAllDanhMucByNhaHang(manh);
    }
    public Completable themDanhMuc(DanhMuc danhMuc){
        return danhMucAPI.themDanhMuc(danhMuc);
    }
    public Completable capNhatDanhMuc(DanhMuc danhMuc){
        return danhMucAPI.capNhatDanhMuc(danhMuc.getMaDanhMuc(), danhMuc);
    }
    public Completable xoaDanhMuc(DanhMuc danhMuc){
        return danhMucAPI.xoaDanhMuc(danhMuc.getMaDanhMuc());
    }
}
