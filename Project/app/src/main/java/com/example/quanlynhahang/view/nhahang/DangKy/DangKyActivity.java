package com.example.quanlynhahang.view.nhahang.DangKy;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.quanlynhahang.databinding.ActivityMainBinding;
import com.example.quanlynhahang.model.NhaHang;
import com.example.quanlynhahang.repository.NhaHangRepository;
import com.example.quanlynhahang.viewmodel.quanly.dangky.DangKyNhaHangVMF;
import com.example.quanlynhahang.viewmodel.quanly.dangky.DangKyNhaHangViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangKyActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private DangKyNhaHangViewModel viewModel;
    private NhaHangRepository repository;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = NhaHangRepository.getInstance();
        DangKyNhaHangVMF factory = new DangKyNhaHangVMF(repository);
        viewModel = new ViewModelProvider(this, factory).get(DangKyNhaHangViewModel.class);
        mAuth = FirebaseAuth.getInstance();

        binding.btnDangKyNH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenNH = binding.etTenNH.getText().toString();
                String diaChi = binding.etDiaChiNH.getText().toString();
                String taiKhoan = binding.etTaiKhoan.getText().toString();
                String matKhau = binding.etMatKhau.getText().toString();
                String sdt = binding.etSdtNH.getText().toString();
                if(!tenNH.isEmpty() && !diaChi.isEmpty() && !taiKhoan.isEmpty() && !matKhau.isEmpty() && !sdt.isEmpty()){
                    mAuth.createUserWithEmailAndPassword(taiKhoan,matKhau)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    NhaHang nhaHang = new NhaHang(authResult.getUser().getUid(),diaChi,tenNH,taiKhoan,matKhau,Integer.parseInt(sdt));
                                    viewModel.thucHienDangKy(nhaHang);
                                    Toast.makeText(DangKyActivity.this, "Đăng ký thành cong: " , Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(DangKyActivity.this, "Đăng ký thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }
            }
        });
        implementDangKiThanhCong();
        implementLoadingAnimation();
    }

    private void implementDangKiThanhCong(){
        viewModel.getSuKienDangKyThanhCong().observe(this, new Observer<NhaHang>() {
            @Override
            public void onChanged(NhaHang nhaHang) {
                if (nhaHang != null) {
                    // Đăng ký thành công, hiển thị thông báo Toast
                    Toast.makeText(DangKyActivity.this,
                            "Đăng ký Nhà hàng thành công! Mã Nhà hàng: " + nhaHang.getMaNH(),
                            Toast.LENGTH_LONG).show();
                    // Intent intent = new Intent(DangKyNhaHangActivityJava.this, LoginActivity.class);
                    // startActivity(intent);
                    // finish(); // Đóng màn hình đăng ký
                }
            }
        });
    }
    private void implementLoadingAnimation(){
        viewModel.getDangTai().observe(this,loading -> {
            if(loading){
                binding.loadingAni.setVisibility(View.VISIBLE);
            }else{
                binding.loadingAni.setVisibility(View.GONE);
            }
        });
    }
}