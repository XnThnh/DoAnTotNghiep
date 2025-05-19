package com.example.quanlynhahang.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlynhahang.databinding.ActivityDangNhapBinding;
import com.example.quanlynhahang.repository.NhaHangRepository;
import com.example.quanlynhahang.session.NhaHangSession;
import com.example.quanlynhahang.session.NhanVienSession;
import com.example.quanlynhahang.view.nhahang.DangKy.DangKyActivity;
import com.example.quanlynhahang.view.nhahang.NhaHangMainActivity;
import com.example.quanlynhahang.view.nhanvien.NhanVienMainActivity;
import com.example.quanlynhahang.viewmodel.nhanvien.DangNhapViewModel;
import com.example.quanlynhahang.viewmodel.quanly.dangnhap.DangNhapNhaHangVM;
import com.google.firebase.auth.FirebaseAuth;

public class DangNhapActivity extends AppCompatActivity {
    private ActivityDangNhapBinding binding;
    private FirebaseAuth mAuth;
    private NhaHangRepository nhaHangRepository;
    private DangNhapNhaHangVM nhaHangViewModel;
    private DangNhapViewModel nhanVienViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDangNhapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        if(NhaHangSession.loadFromPrefs(this) != null && mAuth.getCurrentUser() != null){
            if(mAuth.getCurrentUser().getUid().equals(NhaHangSession.loadFromPrefs(this).getFirebaseUID())){
                startActivity(new Intent(this, NhaHangMainActivity.class));
                finish();
            }
            else NhaHangSession.clear(this);
        }
        else if(NhanVienSession.loadFromPrefs(this) != null && mAuth.getCurrentUser() != null){
            if(mAuth.getCurrentUser().getUid().equals(NhanVienSession.loadFromPrefs(this).getFirebaseUID())){
                startActivity(new Intent(this, NhanVienMainActivity.class));
                finish();
            }
            else NhanVienSession.clear(this);
        }

        nhanVienViewModel = new DangNhapViewModel();
        nhaHangRepository = NhaHangRepository.getInstance();
        nhaHangViewModel = new DangNhapNhaHangVM(nhaHangRepository);
        binding.btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.rgVaiTro.getCheckedRadioButtonId() == binding.rdbQuanLy.getId()){
                    nhaHangViewModel.dangNhap(binding.etTaiKhoanLogin.getText().toString(),binding.etMatKhauLogin.getText().toString());
                }
                else if(binding.rgVaiTro.getCheckedRadioButtonId() == binding.rdbNhanVien.getId()){
                    nhanVienViewModel.dangNhap(binding.etTaiKhoanLogin.getText().toString(),binding.etMatKhauLogin.getText().toString());
                }
                else Toast.makeText(DangNhapActivity.this,"Vui lòng chọn vai trò",Toast.LENGTH_SHORT).show();
            }
        });
        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(DangNhapActivity.this, DangKyActivity.class);
                startActivity(signUp);
            }
        });

        implementLoadingAnimation();
        implementDangNhap();
        implementNhaHangSession();

        implementNhanVienDangNhap();
        implementNhanVienSession();
    }
    private void implementLoadingAnimation(){
        nhaHangViewModel.getDangTai().observe(this,loading -> {
            if(loading) binding.loadingAni.setVisibility(View.VISIBLE);
            else binding.loadingAni.setVisibility(View.GONE);
        });
    }
    private void implementDangNhap(){
        nhaHangViewModel.getDangNhapThanhCong().observe(this,thanhcong ->{
            if(thanhcong){
                Toast.makeText(DangNhapActivity.this,"Dang nhap thanh cong",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DangNhapActivity.this, NhaHangMainActivity.class);
                startActivity(intent);
                finish();
            }

            else Toast.makeText(DangNhapActivity.this,"Dang nhap that bai",Toast.LENGTH_SHORT).show();
        });
    }
    private void implementNhaHangSession(){
        nhaHangViewModel.getNhaHangDaDangNhap().observe(this,nhaHang ->{
            if(nhaHang != null){
                NhaHangSession.setNhaHang(this,nhaHang);
            }
        });
    }

    private void implementNhanVienDangNhap(){
        nhanVienViewModel.getDangNhapThanhCong().observe(this,thanhcong ->{
            if(thanhcong == null){
                Toast.makeText(this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, NhanVienMainActivity.class));
                finish();
            }
            else Toast.makeText(this,"Lỗi đăng nhập "+ thanhcong,Toast.LENGTH_SHORT).show();
        });
        nhanVienViewModel.getDangTai().observe(this,loading -> {
            if(loading) binding.loadingAni.setVisibility(View.VISIBLE);
            else binding.loadingAni.setVisibility(View.GONE);
        });
    }

    private void implementNhanVienSession(){
        nhanVienViewModel.getNhanVienDaDangNhap().observe(this,nhanVien ->{
            NhanVienSession.setNhanVien(this,nhanVien);
        });
    }
}