package com.example.quanlynhahang.retrofit;

import com.example.quanlynhahang.model.HoaDon;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface HoaDonAPI {
    @POST("hoadon/tao")
    Single<HoaDon> taoHoaDon(@Query("maDonHang") int maDonHang, @Query("maNhanVien") int maNhanVien, @Query("maNhaHang") int maNhaHang,
                             @Query("tongTien") int tongTien,
                             @Query("phuongThuc") String phuongThuc);

    @GET("/hoadon/laydstheonv")
    Single<List<HoaDon>> layDSTheoNV(@Query("maNV") int manv);

    @GET("/hoadon/laydstheonh")
    Single<List<HoaDon>> layDSTheoNH(@Query("maNH") int manh);

    @GET("/hoadon/layhdtheoid")
    Single<HoaDon> layHoaDonTheoID(@Query("id") int id);

    @PUT("/hoadon/capnhat")
    Single<HoaDon> capNhatHoaDon(@Body HoaDon hoaDon);
}
