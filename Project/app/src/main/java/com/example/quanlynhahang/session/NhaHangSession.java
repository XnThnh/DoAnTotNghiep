package com.example.quanlynhahang.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.quanlynhahang.model.NhaHang;

public class NhaHangSession {
    private static final String PREF_NAME = "NhaHangSession";
    private static NhaHang nhaHang = null;
    public static void setNhaHang(Context context, NhaHang nh) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("maNH", nh.getMaNH());
        editor.putString("firebaseUID", nh.getFirebaseUID());
        editor.putString("tenNH", nh.getTenNH());
        editor.putString("diaChi", nh.getDiaChi());
        editor.putString("taiKhoan", nh.getTaiKhoan());
        editor.putString("matKhau", nh.getMatKhau());
        editor.putInt("sdt", nh.getSdt());
        editor.apply();

        nhaHang = nh;
    }

    public static NhaHang loadFromPrefs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int maNH = prefs.getInt("maNH", -1);
        if (maNH == -1) return null;

        nhaHang = new NhaHang(
                maNH,
                prefs.getString("firebaseUID", ""),
                prefs.getString("tenNH", ""),prefs.getString("diaChi", ""),
                prefs.getString("taiKhoan", ""),
                prefs.getString("matKhau", ""),
                prefs.getInt("sdt", 0)
        );

        return nhaHang;
    }

    public static NhaHang getCurrentNhaHang() {
        return nhaHang;
    }

    public static void clear(Context context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().clear().apply();
        nhaHang = null;
    }
}
