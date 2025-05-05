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
import com.example.quanlynhahang.view.nhahang.NhaHangMainActivity;
import com.example.quanlynhahang.viewmodel.dangnhap.DangNhapNhaHangVM;
import com.google.firebase.auth.FirebaseAuth;

public class DangNhapActivity extends AppCompatActivity {
    private ActivityDangNhapBinding binding;
    private FirebaseAuth mAuth;
    private NhaHangRepository nhaHangRepository;
    private DangNhapNhaHangVM viewModel;
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
            }
            else NhaHangSession.clear(this);
        }
        nhaHangRepository = NhaHangRepository.getInstance();
        viewModel = new DangNhapNhaHangVM(nhaHangRepository);
        binding.btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.dangNhap(binding.etTaiKhoanLogin.getText().toString(),binding.etMatKhauLogin.getText().toString());
            }
        });
        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(DangNhapActivity.this,DangKyActivity.class);
                startActivity(signUp);
            }
        });

        implementLoadingAnimation();
        implementDangNhap();
        implementNhaHangSession();
    }
    private void implementLoadingAnimation(){
        viewModel.getDangTai().observe(this,loading -> {
            if(loading) binding.loadingAni.setVisibility(View.VISIBLE);
            else binding.loadingAni.setVisibility(View.GONE);
        });
    }
    private void implementDangNhap(){
        viewModel.getDangNhapThanhCong().observe(this,thanhcong ->{
            if(thanhcong){
                Toast.makeText(DangNhapActivity.this,"Dang nhap thanh cong",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DangNhapActivity.this, NhaHangMainActivity.class);
                startActivity(intent);
            }

            else Toast.makeText(DangNhapActivity.this,"Dang nhap that bai",Toast.LENGTH_SHORT).show();
        });
    }
    private void implementNhaHangSession(){
        viewModel.getNhaHangDaDangNhap().observe(this,nhaHang ->{
            if(nhaHang != null){
                NhaHangSession.setNhaHang(this,nhaHang);
            }
        });
    }
}