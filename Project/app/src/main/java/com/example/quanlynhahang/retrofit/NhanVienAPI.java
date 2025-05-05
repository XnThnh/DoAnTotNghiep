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
    // Lay 1 NV theo MaNV
    @GET("/getByMaNV/{maNV}")
    Single<NhanVien> getNhanVienById(@Path("maNV") int maNV);

    // Them NhanVien - Gui object NhanVien trong Body (JSON)
    @POST("/nhanvien")
    Single<NhanVien> themNhanVien(@Body NhanVien nhanVien);

    // Cap nhat NhanVien - Gui object NhanVien trong Body (JSON)
    @PUT("/nhanvien")
    Single<NhanVien> capNhatNhanVien(@Path("maNV") int maNV, @Body NhanVien nhanVien);

    // Xoa NhanVien
    @DELETE("/api/nhan_vien/{maNV}")
    Completable xoaNhanVien(@Path("maNV") int maNV);
}
