package com.example.quanlynhahang.view.nhanvien.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.quanlynhahang.R;
import com.example.quanlynhahang.adapter.DonHangAdapter;
import com.example.quanlynhahang.databinding.ActivityHoaDonBinding;
import com.example.quanlynhahang.databinding.DialogQrThanhToanBinding;
import com.example.quanlynhahang.model.MonAnTrongDonHang;
import com.example.quanlynhahang.model.NhaHang;
import com.example.quanlynhahang.session.NhanVienSession;
import com.example.quanlynhahang.utils.Param;
import com.example.quanlynhahang.viewmodel.nhanvien.HoaDonViewmodel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HoaDonActivity extends AppCompatActivity {
    private DatabaseReference dbRef;
    private ActivityHoaDonBinding binding;
    private static final NhaHang nhaHang = NhanVienSession.getNhanVien().getNhaHang();
    private static final String nhaHangID = nhaHang.getMaNH() + "_" + nhaHang.getTenNH();
    private Intent getData;
    private String maBan;
    private int maDonHang;
    private String phuongThuc = Param.TIEN_MAT;
    private HoaDonViewmodel viewModel;
    private List<MonAnTrongDonHang> dsMonAn = new ArrayList<>();
    private DonHangAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHoaDonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getData = getIntent();
        maDonHang = getData.getIntExtra("maDonHang",0);
        maBan = getData.getStringExtra("maBan");

        dbRef = FirebaseDatabase.getInstance(Param.firebaseURL).getReference("order")
                .child(NhanVienSession.getNhanVien().getNhaHang().getMaNH() + "_" + NhanVienSession.getNhanVien().getNhaHang().getTenNH())
                .child(maBan);

        viewModel = new ViewModelProvider(this).get(HoaDonViewmodel.class);

        adapter = new DonHangAdapter(this,dsMonAn);
        binding.recView.setAdapter(adapter);

        binding.rgPhuongThuc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbTienMat){
                    phuongThuc = Param.TIEN_MAT;
                }
                else if(checkedId == R.id.rbChuyenKhoan){
                    phuongThuc = Param.CHUYEN_KHOAN;
                }
            }
        });


        binding.btnXacNhan.setOnClickListener(view ->{
            viewModel.taoHoaDon(maDonHang, NhanVienSession.getNhanVien().getMaNV(), nhaHang.getMaNH(), phuongThuc);
            if(phuongThuc.equals(Param.TIEN_MAT)){
                initHoanThanhThanhToan("Thanh toán thành công");
            }
            else {
                initThanhToanBangQR();
            }
        });
        initTongTien();
        initLayDSMonAn();

    }

    private void initTongTien(){
        viewModel.getTongTien().observe(this, tongTien -> {
            if(tongTien > 0){
                binding.tvTongTien.setText(String.valueOf(tongTien));
            }
        });
    }

    private void initLayDSMonAn(){
        viewModel.getDsMonAnTrongDonHang(maDonHang);
        viewModel.dsMonAnTrongDonHang().observe(this,list ->{
            if(list != null && !list.isEmpty()){
                dsMonAn.clear();
                dsMonAn.addAll(list);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initHoanThanhThanhToan(String thongTinThongBao){
        dbRef.removeValue();
        dbRef = FirebaseDatabase.getInstance(Param.firebaseURL).getReference("ban").child(nhaHangID+"_dsBan").child(maBan);
        dbRef.child("trangThai").setValue(Param.TRONG);
        dbRef.child("maNV").setValue("");
        Toast.makeText(this,thongTinThongBao,Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, NhanVienMainActivity.class));
        finish();
    }

    private void initThanhToanBangQR(){
        DialogQrThanhToanBinding qrThanhToanBinding = DialogQrThanhToanBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(this);
        dialog.setContentView(qrThanhToanBinding.getRoot());

        String tongTien = binding.tvTongTien.getText().toString();
        String url = "https://img.vietqr.io/image/HDB-0977979389-print.png?amount="+tongTien+"&addInfo="+maBan + "thanh toan don " + maDonHang;
        Glide.with(this).asBitmap().load(url).into(qrThanhToanBinding.imvQR);
        dialog.show();

        qrThanhToanBinding.btnHoanThanh.setOnClickListener(view -> {
            initHoanThanhThanhToan("Đã xác nhận thanh toán và yêu cầu nhà hàng cập nhật trạng thái ");
        });

    }
}