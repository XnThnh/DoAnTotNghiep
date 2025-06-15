package com.example.quanlynhahang.view.nhahang.HoaDon;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanlynhahang.R;
import com.example.quanlynhahang.adapter.HoaDonAdapter;
import com.example.quanlynhahang.databinding.FragmentHoaDonBinding;
import com.example.quanlynhahang.model.HoaDon;
import com.example.quanlynhahang.session.NhaHangSession;
import com.example.quanlynhahang.viewmodel.quanly.hoadon.HoaDonNhaHangVM;

import java.util.ArrayList;
import java.util.List;


public class HoaDonFragment extends Fragment {
    private FragmentHoaDonBinding binding;
    private List<HoaDon> dsHoaDon;
    private HoaDonAdapter adapter;
    private HoaDonNhaHangVM viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hoa_don, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentHoaDonBinding.bind(view);
        dsHoaDon = new ArrayList<>();
        adapter = new HoaDonAdapter(requireContext(), dsHoaDon, false, hoaDon -> {
            Intent intent = new Intent(requireContext(), ChiTietHoaDonNHActivity.class);
            intent.putExtra("hoaDonID", hoaDon.getId());
            startActivity(intent);
        });
        binding.recView.setAdapter(adapter);
        viewModel = new ViewModelProvider(this).get(HoaDonNhaHangVM.class);


        initLayDSHoaDon();
    }

    private void initLayDSHoaDon() {
        viewModel.layDSHoaDon(NhaHangSession.getCurrentNhaHang().getMaNH());
        viewModel.getDsHoaDon().observe(getViewLifecycleOwner(), list -> {
            if (list != null) {
                dsHoaDon.clear();
                dsHoaDon.addAll(list);
                adapter.notifyDataSetChanged();
            }
        });
    }
}