package com.example.quanlynhahang.retrofit;

import com.example.quanlynhahang.model.DanhMuc;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DanhMucAPI {
    @GET("/danhmuc/getByNH/{maNH}")
    Single<List<DanhMuc>> getAllDanhMucByNhaHang(@Path("maNH") int maNH);

    @POST("/danhmuc/themDanhMuc")
    Completable themDanhMuc(@Body DanhMuc danhMuc);

    @PUT("/danhmuc/{maDanhMuc}")
    Completable capNhatDanhMuc(@Path("maDanhMuc") int maDanhMuc, @Body DanhMuc danhMuc);

    @DELETE("/danhmuc/{maDanhMuc}")
    Completable xoaDanhMuc(@Path("maDanhMuc") int maDanhMuc);
}
