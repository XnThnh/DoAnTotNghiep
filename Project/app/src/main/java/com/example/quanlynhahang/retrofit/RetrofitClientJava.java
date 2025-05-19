package com.example.quanlynhahang.retrofit;

import com.example.quanlynhahang.utils.Param;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientJava {
    private static final String URL = Param.URL;
    private static Retrofit retrofit;
    public static Retrofit getInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static NhaHangAPI getNhaHangAPI() {
        return getInstance().create(NhaHangAPI.class);
    }
    public static NhanVienAPI getNhanVienAPI() {return getInstance().create(NhanVienAPI.class);}
    public static DanhMucAPI getDanhMucAPI() {return getInstance().create(DanhMucAPI.class);}
    public static BanAPI getBanAPI() {return getInstance().create(BanAPI.class);}
    public static MonAnAPI getMonAnAPI() {return getInstance().create(MonAnAPI.class);}
}
