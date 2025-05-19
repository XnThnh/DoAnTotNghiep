package com.example.quanlynhahang.retrofit;

import com.example.quanlynhahang.model.NhanVien;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NhanVienAPI {
    @GET("/nhanvien/getByNH/{maNH}")
    Single<List<NhanVien>> getNhanVienByNhaHang(@Path("maNH") int manh);

    @GET("/getByMaNV/{maNV}")
    Single<NhanVien> getNhanVienById(@Path("maNV") int maNV);


    @POST("/nhanvien")
    Single<NhanVien> themNhanVien(@Body NhanVien nhanVien);


    @PUT("/nhanvien/{maNV}")
    Single<NhanVien> capNhatNhanVien(@Path("maNV") int maNV, @Body NhanVien nhanVien);

    // Xoa NhanVien
    @DELETE("/nhanvien/{maNV}")
    Completable xoaNhanVien(@Path("maNV") int maNV);

    @GET("/nhanvien/dangnhap/{firebaseuid}")
    Single<NhanVien> dangNhap(@Path("firebaseuid") String firebaseuid);
}
