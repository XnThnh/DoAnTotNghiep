package com.example.quanlynhahang.view.nhahang.ui.QLNV;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlynhahang.R;
import com.example.quanlynhahang.adapter.NhanVienAdapter;
import com.example.quanlynhahang.databinding.DialogThemNhanVienBinding;
import com.example.quanlynhahang.databinding.FragmentQlnvBinding;
import com.example.quanlynhahang.model.NhanVien;
import com.example.quanlynhahang.repository.NhanVienRepository;
import com.example.quanlynhahang.session.NhaHangSession;
import com.example.quanlynhahang.viewmodel.qlnv.QLNVViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class QLNVFragment extends Fragment {
    private NhanVienRepository nhanVienRepository;
    private NhanVienAdapter adapter;
    private FragmentQlnvBinding binding;
    private ArrayList<NhanVien> listNhanVien;
    private QLNVViewModel viewModel;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new QLNVViewModel(nhanVienRepository);
        binding = FragmentQlnvBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nhanVienRepository = NhanVienRepository.getInstance();
        mAuth = FirebaseAuth.getInstance();
        viewModel = new QLNVViewModel(nhanVienRepository);
        listNhanVien = new ArrayList<>();
        adapter = new NhanVienAdapter(listNhanVien,requireContext());
        implementGetListNhanVien();
        implementLoadingAni();
        implementThanhCong();
        binding.recView.setAdapter(adapter);
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                implementThemOnClickListener();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void implementGetListNhanVien(){
        viewModel.getListNhanVien().observe(getViewLifecycleOwner(), nhanViens -> {
            listNhanVien.clear();
            listNhanVien.addAll(nhanViens);
            Log.d("QLNVFragment", "implementGetListNhanVien:" + listNhanVien.size());
            adapter.notifyDataSetChanged();
        });
    }

    private void implementLoadingAni(){
        viewModel.getDangTai().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.loadingAni.setVisibility(View.VISIBLE);
            } else {
                binding.loadingAni.setVisibility(View.GONE);
            }
        });
    }

    private void implementThanhCong(){
        viewModel.getThanhCong().observe(getViewLifecycleOwner(), isThanhCong -> {
            if (isThanhCong) {
                Toast.makeText(requireContext(), "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show();
                viewModel.reloadListNhanVien();
            }
            else Toast.makeText(requireContext(), "Thêm nhân viên thất bại", Toast.LENGTH_SHORT).show();
        });

    }

    private void implementThemOnClickListener(){
        Dialog dialog = new Dialog(requireContext());
        DialogThemNhanVienBinding dialogBinding = DialogThemNhanVienBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialogBinding.getRoot());
        dialog.show();
        dialogBinding.btnHuy.setOnClickListener(view -> dialog.dismiss());
        dialogBinding.btnThem.setOnClickListener(view -> {
            if (dialogBinding.edtTenNH.getText().toString().isEmpty()
                    || dialogBinding.edtURLAnh.getText().toString().isEmpty()
                    || dialogBinding.edtTaiKhoan.getText().toString().isEmpty()
                    || dialogBinding.edtMatKhau.getText().toString().isEmpty()
                    || dialogBinding.edtSdt.getText().toString().isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
            else{
                mAuth.createUserWithEmailAndPassword(dialogBinding.edtTaiKhoan.getText().toString(), dialogBinding.edtMatKhau.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                NhanVien nhanvien = new NhanVien();
                                nhanvien.setTenNV(dialogBinding.edtTenNH.getText().toString());
                                nhanvien.setUrlAnh(dialogBinding.edtURLAnh.getText().toString());
                                nhanvien.setTaiKhoan(dialogBinding.edtTaiKhoan.getText().toString());
                                nhanvien.setMatkhau(dialogBinding.edtMatKhau.getText().toString());
                                nhanvien.setSdt(Integer.parseInt(dialogBinding.edtSdt.getText().toString()));
                                nhanvien.setFirebaseUID(mAuth.getCurrentUser().getUid());
                                nhanvien.setNhaHang(NhaHangSession.getCurrentNhaHang());
                                viewModel.addNhanVien(nhanvien);
                                dialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireContext(),"Lỗi đăng kí tài khoản " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

}