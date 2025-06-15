package com.example.quanlynhahang.view.nhanvien.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.quanlynhahang.R;
import com.example.quanlynhahang.adapter.DonHangAdapter;
import com.example.quanlynhahang.databinding.ActivityChiTietHoaDonBinding;
import com.example.quanlynhahang.databinding.DialogQrThanhToanBinding;
import com.example.quanlynhahang.model.HoaDon;
import com.example.quanlynhahang.model.MonAnTrongDonHang;
import com.example.quanlynhahang.viewmodel.nhanvien.ChiTietHoaDonVm;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChiTietHoaDonActivity extends AppCompatActivity {
    private ActivityChiTietHoaDonBinding binding;
    private Intent getIntent;
    private ChiTietHoaDonVm viewModel;
    private HoaDon hoaDon;
    private List<MonAnTrongDonHang> dsMonAn;
    private DonHangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChiTietHoaDonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        hoaDon = new HoaDon();
        dsMonAn = new ArrayList<>();
        adapter = new DonHangAdapter(this,dsMonAn);
        binding.recView.setAdapter(adapter);
        binding.tvHoaDon.setText("Chi tiết hóa đơn");

        getIntent = getIntent();
        viewModel = new ViewModelProvider(this).get(ChiTietHoaDonVm.class);
        if(getIntent != null){
            int hoaDonID = getIntent.getIntExtra("hoaDonID",0);
            viewModel.layHoaDonTheoID(hoaDonID);
            initLayHoaDon();
        }
        initThongBaoLoi();


        binding.btnXacNhan.setOnClickListener(view ->{
            if(hoaDon != null) initThanhToanBangQR();
        });
    }

    private void initThongBaoLoi() {
    }

    private void initLayHoaDon() {
        viewModel.hoaDon.observe(this,result ->{
            if(result != null){
                hoaDon = result;
                setUpHoaDon();
                viewModel.getDsMonAnTrongDonHang(hoaDon.getDonHang().getMaDonHang());
                initLayDSMonAn();
                setUpView();
            }
        });
    }

    private void initLayDSMonAn() {
        viewModel.dsMonAnTrongDonHang().observe(this,ds->{
            if(ds != null){
                dsMonAn.clear();
                dsMonAn.addAll(ds);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setUpHoaDon(){
        binding.tvTongTien.setText(String.format(Locale.getDefault(),"%,d",Double.valueOf(hoaDon.getTongTien()).intValue()));
        binding.tvTrangThai.setText(hoaDon.getTrangThai());
    }

    private void setUpView(){
        if(hoaDon.getTrangThai().equals("Đã thanh toán")){
            binding.btnXacNhan.setVisibility(View.GONE);
            binding.tvTrangThai.setTextColor(getColor(R.color.green));
        }
        else{
            binding.btnXacNhan.setVisibility(View.VISIBLE);
            binding.tvTrangThai.setTextColor(getColor(R.color.gray));
        }
    }

    private void initThanhToanBangQR(){
        DialogQrThanhToanBinding qrThanhToanBinding = DialogQrThanhToanBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(this);
        dialog.setContentView(qrThanhToanBinding.getRoot());
        int maBan = hoaDon.getDonHang().getBan().getMaBan();
        int maDonHang = hoaDon.getDonHang().getMaDonHang();

        String tongTien = String.valueOf(hoaDon.getTongTien());
        String url = "https://img.vietqr.io/image/HDB-0977979389-print.png?amount="+tongTien+"&addInfo="+maBan + "thanh toan don " + maDonHang;
        Glide.with(this).asBitmap().load(url).into(qrThanhToanBinding.imvQR);
        dialog.show();

        qrThanhToanBinding.btnHoanThanh.setOnClickListener(view -> {
            dialog.dismiss();
        });

    }
}