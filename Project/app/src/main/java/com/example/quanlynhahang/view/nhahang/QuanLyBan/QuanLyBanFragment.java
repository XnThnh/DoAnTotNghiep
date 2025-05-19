package com.example.quanlynhahang.view.nhahang.QuanLyBan;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quanlynhahang.R;
import com.example.quanlynhahang.adapter.BanAdapter;
import com.example.quanlynhahang.databinding.DialogCapNhatBanBinding;
import com.example.quanlynhahang.databinding.DialogThemBanBinding;
import com.example.quanlynhahang.databinding.DialogXoaBanBinding;
import com.example.quanlynhahang.databinding.FragmentQuanLyBanBinding;
import com.example.quanlynhahang.model.Ban;
import com.example.quanlynhahang.repository.BanRepository;
import com.example.quanlynhahang.session.NhaHangSession;
import com.example.quanlynhahang.viewmodel.quanly.qlb.QuanLyBanViewModel;

import java.util.ArrayList;


public class QuanLyBanFragment extends Fragment {
    private BanAdapter banAdapter;
    private QuanLyBanViewModel viewModel;
    private ArrayList<Ban> dsBan = new ArrayList<>();
    private FragmentQuanLyBanBinding binding;
    private BanRepository banRepository;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quan_ly_ban, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        banRepository = BanRepository.getInstance();
        viewModel = new QuanLyBanViewModel(banRepository);
        binding = FragmentQuanLyBanBinding.bind(view);
        banAdapter = new BanAdapter(dsBan, new BanAdapter.onItemClick() {
            @Override
            public void onItemClick(Ban ban) {
                DialogCapNhatBanBinding dialogCapNhatBanBinding = DialogCapNhatBanBinding.inflate(getLayoutInflater());
                Dialog dialog = new Dialog(requireContext());
                dialog.setContentView(dialogCapNhatBanBinding.getRoot());
                dialogCapNhatBanBinding.edtTenBan.setText(ban.getTenBan());
                dialogCapNhatBanBinding.edtSoChoNgoi.setText(String.valueOf(ban.getSoChoNgoi()));
                dialog.show();
                dialogCapNhatBanBinding.btnHuy.setOnClickListener(view1->dialog.dismiss());
                dialogCapNhatBanBinding.btnCapNhat.setOnClickListener(view2 -> {
                    if(!dialogCapNhatBanBinding.edtTenBan.getText().toString().isEmpty() && !dialogCapNhatBanBinding.edtSoChoNgoi.getText().toString().isEmpty()){
                        ban.setTenBan(dialogCapNhatBanBinding.edtTenBan.getText().toString());
                        ban.setSoChoNgoi(Integer.parseInt(dialogCapNhatBanBinding.edtSoChoNgoi.getText().toString()));
                        viewModel.capNhatBan(ban);
                    }
                    dialog.dismiss();
                });
            }

            @Override
            public void onItemLongClick(Ban ban) {
                DialogXoaBanBinding dialogXoaBanBinding = DialogXoaBanBinding.inflate(getLayoutInflater());
                Dialog dialog = new Dialog(requireContext());
                dialog.setContentView(dialogXoaBanBinding.getRoot());
                dialog.show();
                dialogXoaBanBinding.btnHuy.setOnClickListener(view1->dialog.dismiss());
                dialogXoaBanBinding.btnXoa.setOnClickListener(view2->{viewModel.xoaBan(ban.getMaBan());
                dialog.dismiss();});
            }
        });
        binding.recView.setAdapter(banAdapter);
        binding.recView.setLayoutManager(new GridLayoutManager(requireContext(),2));
        binding.btnFloatingButton.setOnClickListener(view1 ->{
            DialogThemBanBinding dialogThemBanBinding = DialogThemBanBinding.inflate(getLayoutInflater());
            Dialog dialog = new Dialog(requireContext());
            dialog.setContentView(dialogThemBanBinding.getRoot());
            dialog.show();
            dialogThemBanBinding.btnThem.setOnClickListener(view2 ->{themBanOnClick(dialogThemBanBinding);
            dialog.dismiss();});
            dialogThemBanBinding.btnHuy.setOnClickListener(view2 ->{
                dialog.dismiss();
            });
        });
        initRecycleView();
        initDangTai();
        initThemThanhCong();
        initCapNhatThanhCong();
        initXoaThanhCong();
    }

    private void themBanOnClick(DialogThemBanBinding dialogThemBanBinding) {
        String tenBan = dialogThemBanBinding.edtTenBan.getText().toString();
        int soChoNgoi = Integer.parseInt(dialogThemBanBinding.edtSoChoNgoi.getText().toString());
        if(!tenBan.isEmpty() && !dialogThemBanBinding.edtSoChoNgoi.getText().toString().isEmpty() && soChoNgoi > 0){
            Ban ban = new Ban();
            ban.setTenBan(tenBan);
            ban.setSoChoNgoi(soChoNgoi);
            ban.setNhaHang(NhaHangSession.getCurrentNhaHang());
            viewModel.themBan(ban);
            viewModel.reloadDanhSachBan(NhaHangSession.getCurrentNhaHang().getMaNH());
            banAdapter.notifyDataSetChanged();
        }
        else Toast.makeText(requireContext(),"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
    }

    private void initRecycleView() {
        viewModel.getDanhSachBan(NhaHangSession.getCurrentNhaHang().getMaNH()).observe(getViewLifecycleOwner(),listBan ->{
            initLayThanhCong();
            dsBan.clear();
            dsBan.addAll(listBan);
            banAdapter.notifyDataSetChanged();
        });
    }

    private void initLayThanhCong(){
        viewModel.getLayDanhSachThanhCong().observe(getViewLifecycleOwner(),isThanhCong ->{
            if(!isThanhCong){
                Toast.makeText(requireContext(),"Lỗi lấy danh sách bàn",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDangTai(){
        viewModel.getDangTai().observe(getViewLifecycleOwner(),isDangTai ->{
            if(isDangTai){
                binding.loadingAni.setVisibility(View.VISIBLE);
            }
            else {
                binding.loadingAni.setVisibility(View.GONE);
            }
        });
    }

    private void initThemThanhCong(){
        viewModel.getThemThanhCong().observe(getViewLifecycleOwner(),isThanhCong -> {
            if(isThanhCong){
                Toast.makeText(requireContext(),"Thêm bàn thành công",Toast.LENGTH_SHORT).show();
                viewModel.reloadDanhSachBan(NhaHangSession.getCurrentNhaHang().getMaNH());
                banAdapter.notifyDataSetChanged();
            }
            else Toast.makeText(requireContext(),"Lỗi thêm bàn",Toast.LENGTH_SHORT).show();
        });
    }

    private void initCapNhatThanhCong(){
        viewModel.getCapNhatThanhCong().observe(getViewLifecycleOwner(),thanhCong ->{
            if(thanhCong){
                Toast.makeText(requireContext(),"Cập nhật bàn thành công",Toast.LENGTH_SHORT).show();
                viewModel.reloadDanhSachBan(NhaHangSession.getCurrentNhaHang().getMaNH());
                banAdapter.notifyDataSetChanged();
            }
            else Toast.makeText(requireContext(),"Lỗi cập nhật bàn",Toast.LENGTH_SHORT).show();
        });
    }

    private void initXoaThanhCong(){
        viewModel.getXoaThanhCong().observe(getViewLifecycleOwner(),isThanhCong -> {
            if(isThanhCong){
                Toast.makeText(requireContext(),"Xóa bàn thành công",Toast.LENGTH_SHORT).show();
                viewModel.reloadDanhSachBan(NhaHangSession.getCurrentNhaHang().getMaNH());
                banAdapter.notifyDataSetChanged();
            }
            else Toast.makeText(requireContext(),"Lỗi xóa bàn",Toast.LENGTH_SHORT).show();
        });
    }

}