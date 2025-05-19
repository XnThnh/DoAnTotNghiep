package com.example.quanlynhahang.repository;

import com.example.quanlynhahang.model.Ban;
import com.example.quanlynhahang.retrofit.BanAPI;
import com.example.quanlynhahang.retrofit.DanhMucAPI;
import com.example.quanlynhahang.retrofit.RetrofitClientJava;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class BanRepository {
    public static BanRepository instance ;
    private static final Object LOCK = new Object();
    private BanAPI banAPI;

    public static BanRepository getInstance() {
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    BanAPI banAPI = com.example.quanlynhahang.retrofit.RetrofitClientJava.getBanAPI();
                    instance = new BanRepository(banAPI);
                }
            }
        }
        return instance;
    }

    public BanRepository(BanAPI banAPI) {
        this.banAPI = banAPI ;
    }

    public Single<List<Ban>> getBanByNhaHang(int maNH) {
        return banAPI.getBanByNhaHang(maNH);
    }

    public Single<Ban> themBan(Ban ban) {
        return banAPI.themBan(ban);
    }

    public Single<Ban> capNhatBan(Ban ban) {
        return banAPI.capNhatBan(ban);
    }

    public Completable xoaBan(int maBan) {
        return banAPI.xoaBan(maBan);
    }

}
