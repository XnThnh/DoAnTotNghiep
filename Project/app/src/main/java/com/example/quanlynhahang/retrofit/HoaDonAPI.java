package com.example.quanlynhahang.retrofit;

import com.example.quanlynhahang.model.HoaDon;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HoaDonAPI {
    @POST("hoadon/tao")
    Single<HoaDon> taoHoaDon(@Query("maDonHang") int maDonHang, @Query("maNhanVien") int maNhanVien, @Query("maNhaHang") int maNhaHang,
                             @Query("tongTien") int tongTien,
                             @Query("phuongThuc") String phuongThuc);
}
