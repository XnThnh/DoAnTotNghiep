package com.example.quanlynhahang.view.nhahang.QuanLyDanhMuc;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlynhahang.adapter.DanhMucAdapter;
import com.example.quanlynhahang.databinding.DialogSuaDanhMucBinding;
import com.example.quanlynhahang.databinding.DialogThemDanhMucBinding;
import com.example.quanlynhahang.databinding.DialogXoaBinding;
import com.example.quanlynhahang.databinding.FragmentQuanLyDanhMucBinding;
import com.example.quanlynhahang.model.DanhMuc;
import com.example.quanlynhahang.repository.DanhMucRepository;
import com.example.quanlynhahang.session.NhaHangSession;
import com.example.quanlynhahang.viewmodel.quanly.qldm.QuanLyDanhMucViewModel;

import java.util.ArrayList;

public class QuanLyDanhMucFragment extends Fragment {

    private FragmentQuanLyDanhMucBinding binding;
    private ArrayList<DanhMuc> dsDanhMuc = new ArrayList<>();
    private QuanLyDanhMucViewModel viewModel;
    private DanhMucRepository danhMucRepository;
    private DanhMucAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuanLyDanhMucBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        danhMucRepository = DanhMucRepository.getInstance();
        viewModel = new QuanLyDanhMucViewModel(danhMucRepository);
        initLayDanhSachDanhMuc();
        initRecycleView();
        initDangTai();
        binding.btnThem.setOnClickListener(view1 ->initThemDanhMuc());
        initThemThanhCong();
        initCapNhatThanhCong();
    }

    private void initThemDanhMuc() {
        DialogThemDanhMucBinding dialogThemDanhMucBinding = DialogThemDanhMucBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(dialogThemDanhMucBinding.getRoot());
        dialog.show();
        dialogThemDanhMucBinding.btnHuy.setOnClickListener(view -> dialog.dismiss());
        dialogThemDanhMucBinding.btnThem.setOnClickListener(view -> {
            String tenDanhMuc = dialogThemDanhMucBinding.edtDanhMuc.getText().toString();
            if(!tenDanhMuc.equals("")){
                DanhMuc danhMuc = new DanhMuc();
                danhMuc.setTenDanhMuc(tenDanhMuc);
                danhMuc.setNhaHang(NhaHangSession.getCurrentNhaHang());
                viewModel.themDanhMuc(danhMuc);
            }
            else Toast.makeText(requireContext(), "Vui lòng nhập tên danh mục", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initLayDanhSachDanhMuc(){
        viewModel.getListDanhMuc(NhaHangSession.getCurrentNhaHang().getMaNH()).observe(getViewLifecycleOwner(),listDanhMuc -> {
            dsDanhMuc.clear();
            dsDanhMuc.addAll(listDanhMuc);
            adapter.notifyDataSetChanged();
        });
    }

    private void initRecycleView(){
        adapter = new DanhMucAdapter(dsDanhMuc, requireContext(), new DanhMucAdapter.onClick() {
            @Override
            public void suaClick(DanhMuc danhMuc) {
                initSuaDanhMuc(danhMuc);
            }

            @Override
            public void xoaClick(DanhMuc danhMuc) {
                initXoaDanhMuc(danhMuc);
            }
        });
        binding.recView.setAdapter(adapter);
    }

    private void initXoaDanhMuc(DanhMuc danhMuc){
        DialogXoaBinding dialogXoaBinding = DialogXoaBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(dialogXoaBinding.getRoot());
        dialogXoaBinding.tvXacNhanXoa.setText("Bạn có chắc chắn muốn xoá danh mục này ?");
        dialog.show();
        dialogXoaBinding.btnHuy.setOnClickListener(view -> dialog.dismiss());
        dialogXoaBinding.btnXoa.setOnClickListener(view -> {
            viewModel.xoaDanhMuc(danhMuc);
            initThongBaoXoa();
            dialog.dismiss();
        });
    }

    private void initThongBaoXoa() {
        viewModel.getXoaThanhCong().observe(getViewLifecycleOwner(),thanhCong ->{
            if(thanhCong){
                Toast.makeText(requireContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                viewModel.reloadListDanhMuc(NhaHangSession.getCurrentNhaHang().getMaNH());
                adapter.notifyDataSetChanged();
            }
            else Toast.makeText(requireContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
        });
    }

    private void initSuaDanhMuc(DanhMuc danhMuc){
        DialogSuaDanhMucBinding dialogSuaDanhMucBinding = DialogSuaDanhMucBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(dialogSuaDanhMucBinding.getRoot());
        dialogSuaDanhMucBinding.edtDanhMuc.setText(danhMuc.getTenDanhMuc());
        dialog.show();
        dialogSuaDanhMucBinding.btnHuy.setOnClickListener(view -> dialog.dismiss());
        dialogSuaDanhMucBinding.btnThem.setOnClickListener(view -> {
            String tenDanhMuc = dialogSuaDanhMucBinding.edtDanhMuc.getText().toString();
            if(!tenDanhMuc.equals("")){
                danhMuc.setTenDanhMuc(tenDanhMuc);
                viewModel.capNhatDanhMuc(danhMuc);
                dialog.dismiss();
            }
            else Toast.makeText(requireContext(), "Vui lòng nhập tên danh mục", Toast.LENGTH_SHORT).show();

        });
    }

    private void initDangTai() {
        viewModel.getDangTai().observe(getViewLifecycleOwner(), dangTai -> {
            if (dangTai) {
                binding.loadingAni.setVisibility(View.VISIBLE);
            } else {
                binding.loadingAni.setVisibility(View.GONE);
            }
        });
    }

    private void initThemThanhCong(){
        viewModel.getThemThanhCong().observe(getViewLifecycleOwner(),thanhCong -> {
            if(thanhCong){
                Toast.makeText(requireContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                viewModel.reloadListDanhMuc(NhaHangSession.getCurrentNhaHang().getMaNH());
                adapter.notifyDataSetChanged();
            }
            else Toast.makeText(requireContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
        });
    }

    private void initCapNhatThanhCong(){
        viewModel.getCapNhatThanhCong().observe(getViewLifecycleOwner(),thanhCong -> {
            if(thanhCong){
                Toast.makeText(requireContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                viewModel.reloadListDanhMuc(NhaHangSession.getCurrentNhaHang().getMaNH());
                adapter.notifyDataSetChanged();
            }
            else Toast.makeText(requireContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
        });
    }
}