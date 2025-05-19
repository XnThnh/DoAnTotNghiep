package com.example.quanlynhahang.retrofit;

import com.example.quanlynhahang.model.MonAn;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface MonAnAPI {
    @GET("/monan/laydstheonh/{manh}")
    public Single<List<MonAn>> getMonAnByNhaHang(@Path("manh") int maNH);

    @GET("/monan/laydstheodm/{madanhmuc}")
    public Single<List<MonAn>> getMonAnByDanhMuc(@Path("madanhmuc") int madanhmuc);

    @Multipart
    @POST("/monan")
    public Single<MonAn> themMonAn(@Part("tenMon") RequestBody tenMon,
                           @Part("moTa") RequestBody moTa,
                           @Part("gia") RequestBody gia,
                           @Part("danhMucId") RequestBody danhMucId,
                           @Part("nhaHangId") RequestBody nhaHangId,
                           @Part MultipartBody.Part file);

    @DELETE("/monan/{id}")
    public Completable xoaMonAn(@Path("id") int id);
}
