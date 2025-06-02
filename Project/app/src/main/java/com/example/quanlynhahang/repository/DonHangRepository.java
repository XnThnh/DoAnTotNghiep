package com.example.quanlynhahang.repository;

import com.example.quanlynhahang.model.DonHang;
import com.example.quanlynhahang.model.MonAn;
import com.example.quanlynhahang.model.MonAnFirebase;
import com.example.quanlynhahang.model.MonAnTrongDonHang;
import com.example.quanlynhahang.retrofit.BanAPI;
import com.example.quanlynhahang.retrofit.DonHangAPI;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class DonHangRepository {
    public static DonHangRepository instance ;
    private static final Object LOCK = new Object();
    private DonHangAPI donHangAPI;

    public DonHangRepository(DonHangAPI donHangAPI) {
        this.donHangAPI = donHangAPI;
    }

    public static DonHangRepository getInstance() {
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    DonHangAPI donHangApi = com.example.quanlynhahang.retrofit.RetrofitClientJava.getDonHangAPI();
                    instance = new DonHangRepository(donHangApi);
                }
            }
        }
        return instance;
    }

    public Single<List<DonHang>> getDonHangByNhaHang(int maNH) {
        return donHangAPI.getDonHangByNhaHang(maNH);
    }

    public Single<List<MonAnTrongDonHang>> layMonAnTheoDonHang(int maDonHang) {
        return donHangAPI.layMonAnTheoDonHang(maDonHang);
    }

    public Single<DonHang> taoDonHangVaThemMon(int manv, int maBan, List<MonAnFirebase> dsMonAn) {
        return donHangAPI.taoDonHangVaThemMon(manv, maBan, dsMonAn);
    }
    public Single<DonHang> themMonVaoDon(int donHangID, List<MonAnFirebase> dsMonAn) {
        return donHangAPI.themMon(donHangID, dsMonAn);
    }
}
