package com.example.quanlynhahang.view.nhanvien;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.quanlynhahang.R;
import com.example.quanlynhahang.adapter.NhanVienMainAdapter;
import com.example.quanlynhahang.databinding.ActivityNhanVienMainBinding;
import com.example.quanlynhahang.session.NhanVienSession;

import java.util.ArrayList;

public class NhanVienMainActivity extends AppCompatActivity {
    private ActivityNhanVienMainBinding binding;
    private NhanVienMainAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNhanVienMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        menuClickListener();

        ArrayList<Fragment> listFragment = new ArrayList<>();
        listFragment.add(new NhanVienHomeFragment());
        listFragment.add(new NhanVienThongBaoFragment());
        listFragment.add(new NhanVienTaiKhoanFragment());

        adapter = new NhanVienMainAdapter(this,listFragment);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setUserInputEnabled(false);
        binding.viewPager.setCurrentItem(0);

    }

    private void menuClickListener(){
        binding.menuBar.btnHome.setOnClickListener(v -> setMenuCheck(binding.menuBar.btnHome));
        binding.menuBar.btnThongBao.setOnClickListener(v -> setMenuCheck(binding.menuBar.btnThongBao));
        binding.menuBar.btnTaiKhoan.setOnClickListener(v -> setMenuCheck(binding.menuBar.btnTaiKhoan));
    }

    private void setMenuCheck(LinearLayout layout){
        binding.menuBar.icHome.setImageResource(R.drawable.ic_home);
        binding.menuBar.icThongBao.setImageResource(R.drawable.ic_home);
        binding.menuBar.icPerson.setImageResource(R.drawable.ic_person);

       binding.menuBar.textHome.setTextColor(getResources().getColor(R.color.black));
       binding.menuBar.textThongBao.setTextColor(getResources().getColor(R.color.black));
       binding.menuBar.textTaiKhoan.setTextColor(getResources().getColor(R.color.black));

        layout.setSelected(true);

        if(layout.getId() == R.id.btnHome){
            binding.viewPager.setCurrentItem(0);
            binding.menuBar.icHome.setImageResource(R.drawable.ic_home_selected);
            binding.menuBar.textHome.setTextColor(getResources().getColor(R.color.color_ff931c));
        }
        else if(layout.getId() == R.id.btnThongBao){
            binding.viewPager.setCurrentItem(1);
            binding.menuBar.icThongBao.setImageResource(R.drawable.ic_home_selected);
            binding.menuBar.textThongBao.setTextColor(getResources().getColor(R.color.color_ff931c));
        }
        else{
            binding.viewPager.setCurrentItem(2);
            binding.menuBar.icPerson.setImageResource(R.drawable.ic_person_selected);
            binding.menuBar.textTaiKhoan.setTextColor(getResources().getColor(R.color.color_ff931c));
        }

    }
}