package com.example.quanlynhahang.repository;

import com.example.quanlynhahang.model.MonAn;
import com.example.quanlynhahang.retrofit.BanAPI;
import com.example.quanlynhahang.retrofit.MonAnAPI;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MonAnRepository {
    public static MonAnRepository instance ;
    private static final Object LOCK = new Object();
    private MonAnAPI monAnAPI;

    public static MonAnRepository getInstance() {
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    MonAnAPI monAnApi = com.example.quanlynhahang.retrofit.RetrofitClientJava.getMonAnAPI();
                    instance = new MonAnRepository(monAnApi);
                }
            }
        }
        return instance;
    }

    public MonAnRepository(MonAnAPI monAnAPI) {
        this.monAnAPI = monAnAPI ;
    }

    public Single<List<MonAn>> layDSMonAnTheoNhaHang(int maNH){
        return monAnAPI.getMonAnByNhaHang(maNH);
    }

    public Single<List<MonAn>> layDSMonAnTheoDanhMuc(int maDanhMuc){
        return monAnAPI.getMonAnByDanhMuc(maDanhMuc);
    }

    public Single<MonAn> themMonAn(RequestBody tenMon, RequestBody moTa, RequestBody gia, RequestBody danhMucId, RequestBody nhaHangId, MultipartBody.Part file){
        return monAnAPI.themMonAn(tenMon,moTa,gia,danhMucId,nhaHangId,file);
    }
    public Completable xoaMonAn(int id){
        return monAnAPI.xoaMonAn(id);
    }
}
