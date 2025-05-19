package com.example.quanlynhahang.retrofit;

import com.example.quanlynhahang.model.Ban;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BanAPI {

    @GET("/ban/{maNH}")
    public Single<List<Ban>> getBanByNhaHang(@Path("maNH") int maNH);

    @POST("/ban/them")
    public Single<Ban> themBan(@Body Ban ban);

    @PUT("/ban/sua")
    public Single<Ban> capNhatBan( @Body Ban ban);

    @DELETE("/ban/xoa/{maBan}")
    public Completable xoaBan(@Path("maBan") int maBan);
}
