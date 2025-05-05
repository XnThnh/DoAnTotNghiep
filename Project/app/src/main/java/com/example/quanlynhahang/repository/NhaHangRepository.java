package com.example.quanlynhahang.repository;

import android.util.Log;

import com.example.quanlynhahang.model.NhaHang;
import com.example.quanlynhahang.retrofit.NhaHangAPI;
import com.google.gson.Gson;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Single;

public class NhaHangRepository {
    private static NhaHangRepository instance; // <-- Tên ngắn gọn
    private static final Object LOCK = new Object();

    public static NhaHangRepository getInstance() { // <-- Tên ngắn gọn
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    NhaHangAPI apiSource = com.example.quanlynhahang.retrofit.RetrofitClientJava.getNhaHangAPI();
                    instance = new NhaHangRepository(apiSource); //
                }
            }
        }
        return instance;
    }
    private NhaHangAPI nhaHangAPI;

    public NhaHangRepository(NhaHangAPI nhaHangAPI) {
        this.nhaHangAPI = nhaHangAPI;
    }
    public Single<NhaHang> dangKyNhaHang(NhaHang nhaHang) {
        return nhaHangAPI.dangKyNhaHang(nhaHang);
    }
    public Single<NhaHang> dangNhapNhaHang(String nhaHangUID){
        return nhaHangAPI.dangNhapNhaHang(nhaHangUID);
    }
}
