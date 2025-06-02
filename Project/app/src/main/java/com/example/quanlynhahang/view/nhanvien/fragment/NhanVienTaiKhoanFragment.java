package com.example.quanlynhahang.view.nhanvien.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.quanlynhahang.R;
import com.example.quanlynhahang.databinding.FragmentNhanVienTaiKhoanBinding;
import com.example.quanlynhahang.model.NhanVien;
import com.example.quanlynhahang.session.NhanVienSession;
import com.example.quanlynhahang.view.DangNhapActivity;
import com.google.firebase.auth.FirebaseAuth;

public class NhanVienTaiKhoanFragment extends Fragment {
    private FragmentNhanVienTaiKhoanBinding binding;
    private FirebaseAuth mAuth;
    private NhanVien nhanVien;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nhan_vien_tai_khoan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentNhanVienTaiKhoanBinding.bind(view);
        mAuth = FirebaseAuth.getInstance();

        setUpThongTin();

        binding.btnDangXuat.setOnClickListener(v->{
            mAuth.signOut();
            NhanVienSession.clear(requireContext());
            startActivity(new Intent(requireContext(), DangNhapActivity.class));
            requireActivity().finish();
        });
    }

    private void setUpThongTin(){
        nhanVien = NhanVienSession.getNhanVien();
        Glide.with(requireContext()).load(nhanVien.getUrlAnh()).into(binding.imvAnh);
        binding.tvMaNV.setText(nhanVien.getMaNV()+"");
        binding.tvHoVaTen.setText(nhanVien.getTenNV());
        binding.tvSDT.setText(nhanVien.getSdt()+"");
        binding.tvTaiKhoan.setText(nhanVien.getTaiKhoan());
        binding.tvTenNhaHang.setText(nhanVien.getNhaHang().getTenNH());
        Log.d("TAG", "setUpThongTin: "+nhanVien.toString());
    }
}