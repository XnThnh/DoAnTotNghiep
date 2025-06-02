package com.example.quanlynhahang.view.nhanvien.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quanlynhahang.R;
import com.example.quanlynhahang.databinding.ActivityOrderBinding;

import com.example.quanlynhahang.model.NhaHang;
import com.example.quanlynhahang.session.NhanVienSession;
import com.example.quanlynhahang.utils.Param;

import com.example.quanlynhahang.view.nhanvien.fragment.DonHangFragment;
import com.example.quanlynhahang.view.nhanvien.fragment.OrderFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;


public class OrderActivity extends AppCompatActivity {
    private static final NhaHang nhaHang = NhanVienSession.getNhanVien().getNhaHang();
    private static final String nhaHangID = nhaHang.getMaNH() + "_" + nhaHang.getTenNH();

    private ActivityOrderBinding binding;
    private Intent getData;
    private String getRef;
    private DatabaseReference dbRef;
    private ArrayList<Fragment> listFragment = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getData = getIntent();
        getRef = getData.getStringExtra("maBan");
        listFragment.add(OrderFragment.newInstance(getRef));
        listFragment.add(DonHangFragment.newInstance(getRef));
        binding.viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return listFragment.get(position);
            }

            @Override
            public int getItemCount() {
                return listFragment.size();
            }
        });
        binding.viewPager.setCurrentItem(0);

        binding.btnOrder.setOnClickListener(v -> {
            binding.viewPager.setCurrentItem(0);
            binding.btnOrder.setTextColor(getColor(R.color.green));
            binding.btnDonHang.setTextColor(getColor(R.color.black));
            binding.btnHuyOrder.setText("Huỷ order");
            binding.btnHuyOrder.setTextColor(getColor(R.color.red));
        });

        binding.btnDonHang.setOnClickListener(v -> {
            binding.viewPager.setCurrentItem(1);
            binding.btnOrder.setTextColor(getColor(R.color.black));
            binding.btnDonHang.setTextColor(getColor(R.color.green));
            binding.btnHuyOrder.setText("Thanh toán");
            binding.btnHuyOrder.setTextColor(getColor(R.color.green));
        });


        dbRef = FirebaseDatabase.getInstance(Param.firebaseURL).getReference("ban")
                .child(NhanVienSession.getNhanVien().getNhaHang().getMaNH() + "_" + NhanVienSession.getNhanVien().getNhaHang().getTenNH() + "_dsBan");

        binding.btnHuyOrder.setOnClickListener(v1 -> {
           if(binding.viewPager.getCurrentItem() == 0){
               DatabaseReference ref = dbRef.child(getRef);
               ref.child("maNV").setValue("");
               ref.child("trangThai").setValue(Param.TRONG);
               OrderFragment.huyOrder();
               finish();
           }
           else if(binding.viewPager.getCurrentItem() == 1){
               Toast.makeText(this, "Thanh toans nha ", Toast.LENGTH_SHORT).show();
           }
        });
    }

}