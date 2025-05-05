package com.example.quanlynhahang.retrofit;

import com.example.quanlynhahang.model.NhaHang;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NhaHangAPI {
    @POST("nhahang/dang_ky_nha_hang")
    Single<NhaHang> dangKyNhaHang(@Body NhaHang nhaHang);
    @GET("/nhahang/dangnhap") // <-- Giả sử backend có endpoint này
    Single<NhaHang> dangNhapNhaHang(@Query("firebaseuid") String firebaseuid);
}
