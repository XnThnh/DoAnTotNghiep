package com.example.quanlynhahang.view.nhanvien.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quanlynhahang.R;
import com.example.quanlynhahang.adapter.HoaDonAdapter;
import com.example.quanlynhahang.databinding.FragmentNhanVienLichSuBinding;
import com.example.quanlynhahang.model.HoaDon;
import com.example.quanlynhahang.session.NhanVienSession;
import com.example.quanlynhahang.view.nhanvien.activity.ChiTietHoaDonActivity;
import com.example.quanlynhahang.viewmodel.nhanvien.LichSuVM;

import java.util.ArrayList;
import java.util.List;

public class NhanVienLichSuFragment extends Fragment {
    private LichSuVM viewModel;
    private List<HoaDon> dsHoaDon;
    private HoaDonAdapter adapter;
    private FragmentNhanVienLichSuBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nhan_vien_lich_su, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentNhanVienLichSuBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(LichSuVM.class);
        dsHoaDon = new ArrayList<>();
        adapter = new HoaDonAdapter(requireContext(),dsHoaDon,true, hoaDon -> {
            Intent intent = new Intent(getContext(), ChiTietHoaDonActivity.class);
            intent.putExtra("hoaDonID",hoaDon.getId());
            startActivity(intent);
        });
        binding.recView.setAdapter(adapter);
        binding.recView.setHasFixedSize(true);

        initLayDsHoaDon();
        initThongBaoLoi();
    }

    private void initThongBaoLoi() {
        viewModel.getThongBao().observe(getViewLifecycleOwner(),thongBao ->{
            if(thongBao != null){
                Toast.makeText(getContext(), thongBao, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initLayDsHoaDon() {
        viewModel.layDSHoaDon(NhanVienSession.getNhanVien().getMaNV());
        viewModel.getDsHoaDon().observe(getViewLifecycleOwner(),list ->{
            if(list != null){
                dsHoaDon.clear();
                dsHoaDon.addAll(list);
                adapter.notifyDataSetChanged();
            }
        });
    }
}