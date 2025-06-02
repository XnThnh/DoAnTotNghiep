package com.example.quanlynhahang.retrofit;

import com.example.quanlynhahang.model.DonHang;
import com.example.quanlynhahang.model.MonAn;
import com.example.quanlynhahang.model.MonAnFirebase;
import com.example.quanlynhahang.model.MonAnTrongDonHang;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DonHangAPI {
    @GET("/donhang/{maNH}")
    public Single<List<DonHang>> getDonHangByNhaHang(@Path("maNH") int maNH);

    @GET("/donhang/laymonantheodon")
    public Single<List<MonAnTrongDonHang>> layMonAnTheoDonHang(@Query("maDonHang") int maDonHang);

    @POST("/donhang/tao")
    public Single<DonHang> taoDonHangVaThemMon(@Query("manv") int manv, @Query("maBan") int maBan, @Body List<MonAnFirebase> dsMonAn);

    @POST("/donhang/themmon")
    public Single<DonHang> themMon(@Query("donHangID") int donHangID, @Body List<MonAnFirebase> dsMonAn);
}
