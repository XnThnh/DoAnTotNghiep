package com.example.quanlynhahang.repository;

import com.example.quanlynhahang.model.HoaDon;
import com.example.quanlynhahang.retrofit.DonHangAPI;
import com.example.quanlynhahang.retrofit.HoaDonAPI;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class HoaDonRepository {
    public static HoaDonRepository instance ;
    private static final Object LOCK = new Object();
    private HoaDonAPI hoaDonAPI;

    public HoaDonRepository(HoaDonAPI hoaDonAPI) {
        this.hoaDonAPI = hoaDonAPI;
    }

    public static HoaDonRepository getInstance() {
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    HoaDonAPI hoaDonAPI1 = com.example.quanlynhahang.retrofit.RetrofitClientJava.getHoaDonAPI();
                    instance = new HoaDonRepository(hoaDonAPI1);
                }
            }
        }
        return instance;
    }

    public Single<HoaDon> taoHoaDon(int maDonHang, int maNhanVien, int maNhaHang, int tongTien, String phuongThuc) {
        return hoaDonAPI.taoHoaDon(maDonHang, maNhanVien, maNhaHang, tongTien, phuongThuc);
    }

    public Single<List<HoaDon>> layDSTheoNV(int manv) {
        return hoaDonAPI.layDSTheoNV(manv);
    }

    public Single<List<HoaDon>> layDSTheoNH(int manh) {
        return hoaDonAPI.layDSTheoNH(manh);
    }
    public Single<HoaDon> layHoaDonTheoID(int id) {
        return hoaDonAPI.layHoaDonTheoID(id);
    }
    public Single<HoaDon> capNhatHoaDon(HoaDon hoaDon) {
        return hoaDonAPI.capNhatHoaDon(hoaDon);
    }
}
