package com.example.quanlynhahang.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.quanlynhahang.model.NhaHang;
import com.example.quanlynhahang.model.NhanVien;
import com.example.quanlynhahang.repository.NhaHangRepository;
import com.example.quanlynhahang.repository.NhanVienRepository;

public class NhanVienSession {
    private static final String PREF_NAME = "NhanVienSession";
    private static NhanVien nhanVien = null;
    private static NhaHang nh = null;
    public static void setNhanVien(Context context,NhanVien nv){
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("maNV",nv.getMaNV());
        editor.putString("firebaseUID",nv.getFirebaseUID());
        editor.putString("tenNV",nv.getTenNV());
        editor.putInt("sdt",nv.getSdt());
        editor.putString("urlAnh",nv.getUrlAnh());
        editor.putString("vaiTro",nv.getVaiTro());
        editor.putString("taiKhoan",nv.getTaiKhoan());
        editor.putString("matKhau",nv.getMatkhau());
        editor.putInt("maNH",nv.getNhaHang().getMaNH());
        editor.apply();
        nhanVien = nv;
        nh = nhanVien.getNhaHang();
    }

    public static NhanVien loadFromPrefs(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int maNV = prefs.getInt("maNV", -1);
        if (maNV == -1) return null;
        else nhanVien = new NhanVien(prefs.getInt("maNV", -1),
                prefs.getString("firebaseUID", ""),
                prefs.getInt("sdt", 0),
                prefs.getString("tenNV", ""),
                prefs.getString("vaiTro", ""),
                prefs.getString("taiKhoan", ""),
                prefs.getString("urlAnh", ""),
                prefs.getString("matKhau", "")
                ,nh);
        return nhanVien;
    }
    public static NhanVien getNhanVien(){
        return nhanVien;
    }
    public static void clear(Context context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().clear().apply();
        nhanVien = null;
    }
}
