package com.example.quanlynhahang.view.nhanvien;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanlynhahang.R;
import com.example.quanlynhahang.databinding.FragmentNhanVienTaiKhoanBinding;
import com.example.quanlynhahang.session.NhanVienSession;
import com.example.quanlynhahang.view.DangNhapActivity;
import com.google.firebase.auth.FirebaseAuth;

public class NhanVienTaiKhoanFragment extends Fragment {
    private FragmentNhanVienTaiKhoanBinding binding;
    private FirebaseAuth mAuth;
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
        binding.btnDangXuat.setOnClickListener(v->{
            mAuth.signOut();
            NhanVienSession.clear(requireContext());
            startActivity(new Intent(requireContext(), DangNhapActivity.class));
            requireActivity().finish();
        });
    }
}