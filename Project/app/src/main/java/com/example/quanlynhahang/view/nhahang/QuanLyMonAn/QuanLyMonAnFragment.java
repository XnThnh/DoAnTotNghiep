package com.example.quanlynhahang.view.nhahang.QuanLyMonAn;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.quanlynhahang.R;
import com.example.quanlynhahang.adapter.MonAnAdapter;
import com.example.quanlynhahang.databinding.DialogThemMonAnBinding;
import com.example.quanlynhahang.databinding.DialogXoaMonAnBinding;
import com.example.quanlynhahang.databinding.FragmentQuanLyMonAnBinding;
import com.example.quanlynhahang.model.DanhMuc;
import com.example.quanlynhahang.model.MonAn;
import com.example.quanlynhahang.repository.DanhMucRepository;
import com.example.quanlynhahang.repository.MonAnRepository;
import com.example.quanlynhahang.session.NhaHangSession;
import com.example.quanlynhahang.viewmodel.quanly.qldm.QuanLyDanhMucViewModel;
import com.example.quanlynhahang.viewmodel.quanly.qlma.QuanLyMonAnViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class QuanLyMonAnFragment extends Fragment {
    private FragmentQuanLyMonAnBinding binding;
    private QuanLyDanhMucViewModel danhMucViewModel;
    private QuanLyMonAnViewModel monAnViewModel;
    private ArrayList<DanhMuc> listDanhMuc;
    private ArrayList<MonAn> listMonAn;
    private MutableLiveData<Uri> imageUri = new MutableLiveData<>();
    private int idDanhmuc = 0;
    private MonAnAdapter adapter;
    private ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri selectedImage = data.getData();
                            if (selectedImage != null) {
                                imageUri.setValue(selectedImage);
                            }
                        }
                    }
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quan_ly_mon_an, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentQuanLyMonAnBinding.bind(view);
        danhMucViewModel = new QuanLyDanhMucViewModel(DanhMucRepository.getInstance());
        monAnViewModel = new QuanLyMonAnViewModel(MonAnRepository.getInstance());
        listDanhMuc = new ArrayList<>();
        listMonAn = new ArrayList<>();
        initLayDsMonAn();
        adapter = new MonAnAdapter(requireContext(), listMonAn, new MonAnAdapter.onClickListener() {
            @Override
            public void suaClick(MonAn monAn) {

            }

            @Override
            public void xoaClick(MonAn monAn) {
                DialogXoaMonAnBinding dialogXoaMonAnBinding = DialogXoaMonAnBinding.inflate(getLayoutInflater());
                Dialog dialog = new Dialog(requireContext());
                dialog.setContentView(dialogXoaMonAnBinding.getRoot());
                dialog.show();
                dialogXoaMonAnBinding.btnHuy.setOnClickListener(view ->dialog.dismiss());
                dialogXoaMonAnBinding.btnXoa.setOnClickListener(view1 -> {
                    monAnViewModel.xoaMonAn(monAn.getId());
                    dialog.dismiss();
                });
            }
        });
        binding.recView.setAdapter(adapter);
        binding.btnThem.setOnClickListener(view1 -> {
            themMonAn();
        });
        layDanhSachDanhMuc();
        initThemThanhCong();
        initDangTai();
        initXoaThanhCong();
        Log.d("TAG","listSize : "+ listMonAn.size());
    }

    private void themMonAn() {
        Dialog dialog = new Dialog(requireContext());
        DialogThemMonAnBinding themMonAnBinding = DialogThemMonAnBinding.inflate(getLayoutInflater());
        dialog.setContentView(themMonAnBinding.getRoot());

        themMonAnBinding.spnDanhMuc.setAdapter(new ArrayAdapter<DanhMuc>(requireContext(), android.R.layout.simple_spinner_dropdown_item, listDanhMuc));
        xuLyLayDanhMucDuocChon(themMonAnBinding);
        initLayAnh(themMonAnBinding);
        dialog.show();

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        themMonAnBinding.btnHuy.setOnClickListener(view -> dialog.dismiss());
        themMonAnBinding.imvAnh.setOnClickListener(view -> {
            chonAnhTuGallery();
        });
        themMonAnBinding.btnThem.setOnClickListener(view -> {
            idDanhmuc = listDanhMuc.get(themMonAnBinding.spnDanhMuc.getSelectedItemPosition()).getMaDanhMuc();

            boolean isEnable = !themMonAnBinding.edtTenMonAn.getText().toString().isEmpty() && !themMonAnBinding.edtGiaMonAn.getText().toString().isEmpty()
                    && !themMonAnBinding.edtMoTa.getText().toString().isEmpty() && idDanhmuc != 0 && imageUri != null;
            if (isEnable) {
                File file = null;
                try {
                    file = layFileTuUri();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String tenMonAn = themMonAnBinding.edtTenMonAn.getText().toString();
                String moTa = themMonAnBinding.edtMoTa.getText().toString();
                String gia = themMonAnBinding.edtGiaMonAn.getText().toString();
                monAnViewModel.themMonAn(tenMonAn, moTa, gia, idDanhmuc,
                        NhaHangSession.getCurrentNhaHang().getMaNH(), file);
                dialog.dismiss();

            } else
                Toast.makeText(requireContext(), "Vui long nhap du thong tin", Toast.LENGTH_SHORT).show();
        });
    }

    private void chonAnhTuGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        pickImageLauncher.launch(intent);
    }

    private void layDanhSachDanhMuc() {
        danhMucViewModel.getListDanhMuc(NhaHangSession.getCurrentNhaHang().getMaNH()).observe(getViewLifecycleOwner(), list -> {
            listDanhMuc.clear();
            listDanhMuc.addAll(list);
        });
    }

    private void xuLyLayDanhMucDuocChon(DialogThemMonAnBinding themMonAnBinding) {
        themMonAnBinding.spnDanhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DanhMuc danhMuc = listDanhMuc.get(position);
                idDanhmuc = danhMuc.getMaDanhMuc();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private File layFileTuUri() throws IOException {
        InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri.getValue());
        File file = new File(requireContext().getCacheDir(), "upload.jpg");
        OutputStream outputStream = new FileOutputStream((file));
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, len);
        }
        inputStream.close();
        outputStream.close();
        return file;
    }

    private void initThemThanhCong() {
        monAnViewModel.getThemThanhCong().observe(getViewLifecycleOwner(), thanhCong -> {
            if (thanhCong){
                Toast.makeText(requireContext(), "Them Thanh cong", Toast.LENGTH_SHORT).show();
                monAnViewModel.reloadDsMonAn(NhaHangSession.getCurrentNhaHang().getMaNH());
            }

            else Toast.makeText(requireContext(), "Them that bai", Toast.LENGTH_SHORT).show();
        });
    }

    private void initLayDsMonAn(){
        monAnViewModel.layDSMonAn(NhaHangSession.getCurrentNhaHang().getMaNH()).observe(getViewLifecycleOwner(),list ->{
            listMonAn.clear();
            listMonAn.addAll(list);
            adapter.notifyDataSetChanged();
        });
    }

    private void initDangTai(){
        monAnViewModel.getDanhTai().observe(getViewLifecycleOwner(),dangTai ->{
            if(dangTai){
                binding.loadingAni.setVisibility(View.VISIBLE);
            }
            else binding.loadingAni.setVisibility(View.GONE);
        });
    }

    private void initLayAnh(DialogThemMonAnBinding dialog){
        imageUri.observe(getViewLifecycleOwner(),uri ->{
            if(uri != null){
            dialog.imvAnh.setImageURI(null);
            dialog.imvAnh.setImageURI(uri);
            }
        });
    }

    private void initXoaThanhCong(){
        monAnViewModel.getXoaThanhCong().observe(getViewLifecycleOwner(),thanhCong ->{
            if(thanhCong == null){
                Toast.makeText(requireContext(), "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                monAnViewModel.reloadDsMonAn(NhaHangSession.getCurrentNhaHang().getMaNH());
            }
            else Toast.makeText(requireContext(), "Xoa that bai,loi : "+ thanhCong, Toast.LENGTH_SHORT).show();
        });
    }
}