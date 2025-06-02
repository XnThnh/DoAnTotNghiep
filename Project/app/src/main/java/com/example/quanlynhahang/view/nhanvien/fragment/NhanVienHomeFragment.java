package com.example.quanlynhahang.view.nhanvien.fragment;

import android.content.Intent;
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
import com.example.quanlynhahang.adapter.BanChoNhanVienAdapter;
import com.example.quanlynhahang.databinding.FragmentNhanVienHomeBinding;
import com.example.quanlynhahang.model.BanChoNhanVien;
import com.example.quanlynhahang.model.NhanVien;
import com.example.quanlynhahang.session.NhanVienSession;
import com.example.quanlynhahang.view.nhanvien.activity.OrderActivity;
import com.example.quanlynhahang.viewmodel.nhanvien.NhanVienHomeViewModel;

import java.util.ArrayList;


public class NhanVienHomeFragment extends Fragment {
    private NhanVien nhanVien;
    private NhanVienHomeViewModel viewModel;
    private ArrayList<BanChoNhanVien> dsBan;
    private BanChoNhanVienAdapter adapter;
    private FragmentNhanVienHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nhan_vien_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentNhanVienHomeBinding.bind(view);
        nhanVien = NhanVienSession.getNhanVien();
        dsBan = new ArrayList<>();

        adapter = new BanChoNhanVienAdapter(dsBan,ban -> {
            viewModel.moBan(ban);
        },requireContext());
        binding.recView.setAdapter(adapter);
        binding.recView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        viewModel = new NhanVienHomeViewModel(nhanVien);
        loadBan();
        observeMoBan();
        observeBanTrenFirebase();
    }


    private void loadBan(){
        viewModel.getDsBan().observe(getViewLifecycleOwner(), listKetqua -> {
            if (listKetqua != null && !listKetqua.isEmpty()) {
                viewModel.loadBan(listKetqua);
            }
        });
        viewModel.getDsBanTrenFireBase().observe(getViewLifecycleOwner(), list -> {
            if (list != null && !list.isEmpty()) {
                dsBan.clear();
                dsBan.addAll(list);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void observeMoBan(){
        viewModel.getMoBan().observe(getViewLifecycleOwner(), ref -> {
            if(ref != null){
                Intent intent = new Intent(getContext(), OrderActivity.class);
                intent.putExtra("maBan",ref);
                startActivity(intent);
                Toast.makeText(getContext(), "Đã mở bàn", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getContext(), "Không thể mở bàn", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void observeBanTrenFirebase(){
        viewModel.observeBanTrenFirebase();
    }
}