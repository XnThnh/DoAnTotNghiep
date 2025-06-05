package com.example.quanlynhahang.view.nhanvien.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quanlynhahang.R;
import com.example.quanlynhahang.adapter.DonHangAdapter;
import com.example.quanlynhahang.databinding.FragmentDonHangBinding;

import com.example.quanlynhahang.model.MonAnTrongDonHang;
import com.example.quanlynhahang.model.NhaHang;
import com.example.quanlynhahang.session.NhanVienSession;
import com.example.quanlynhahang.utils.Param;
import com.example.quanlynhahang.viewmodel.nhanvien.DonHangViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DonHangFragment extends Fragment {
    private static final NhaHang nhaHang = NhanVienSession.getNhanVien().getNhaHang();
    private static final String nhaHangID = nhaHang.getMaNH() + "_" + nhaHang.getTenNH();

    private FragmentDonHangBinding binding;
    private String maBan;
    private DatabaseReference dbRef;
    private DonHangViewModel donHangViewModel;
    private List<MonAnTrongDonHang> dsMonAn = new ArrayList<>();
    private DonHangAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_don_hang, container, false);
    }
    public static DonHangFragment newInstance(String maBan) {
        DonHangFragment fragment = new DonHangFragment();
        Bundle args = new Bundle();
        args.putString("maBan", maBan);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentDonHangBinding.bind(view);
        maBan = getArguments().getString("maBan");
        dbRef = FirebaseDatabase.getInstance(Param.firebaseURL).getReference("order").child(nhaHangID).child(maBan);
        donHangViewModel = new DonHangViewModel();
        adapter = new DonHangAdapter(requireContext(),dsMonAn);
        binding.recView.setAdapter(adapter);

        layDSMonAnTuFirebase();
        observeDanhSachMonAnTrongDonHang();
    }

    @SuppressLint("DefaultLocale")
    private void observeDanhSachMonAnTrongDonHang() {
        donHangViewModel.dsMonAnTrongDonHang().observe(getViewLifecycleOwner(),list ->{
            if(list == null){
                Toast.makeText(requireContext(), "Lỗi lấy danh sách món ăn", Toast.LENGTH_SHORT).show();
            }
            else {
                dsMonAn.clear();
                dsMonAn.addAll(list);
                adapter.notifyDataSetChanged();
                binding.tvTongTien.setText(String.format("%,d",tinhTongTien()));
            }

        });
    }

    private int tinhTongTien() {
        int tongTien = 0;
        for(MonAnTrongDonHang monAn : dsMonAn){
            tongTien += monAn.getMonAn().getGiaMonAn() * monAn.getSoLuong();
        }
        return tongTien;
    }

    private void layDSMonAnTuFirebase() {
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.getChildrenCount() > 0){
                    DataSnapshot maDonHangSnap = snapshot.child("maDonHang");
                    int maDonHang = Integer.parseInt(String.valueOf(maDonHangSnap.getValue()));
                    if(!maDonHangSnap.exists() || maDonHang != 0){
                        donHangViewModel.getDsMonAnTrongDonHang(maDonHang);
                    }
                    else {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("DonHangFragment","DonHangFragment onCancel" + error.getMessage());
            }
        });
    }


}