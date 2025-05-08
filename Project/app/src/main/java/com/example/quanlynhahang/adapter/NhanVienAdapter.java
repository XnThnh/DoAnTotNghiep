package com.example.quanlynhahang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlynhahang.databinding.ItemNhanVienBinding;
import com.example.quanlynhahang.model.NhanVien;

import java.util.ArrayList;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.ViewHolder> {
    private ArrayList<NhanVien> listNhanVien;
    private Context context;
    private ClickListener clickListener;
    public interface ClickListener{
        void xoaCLick(NhanVien nhanVien);
        void suaClick(NhanVien nhanVien);
    }
    public NhanVienAdapter(ArrayList<NhanVien> listNhanVien, Context context, ClickListener clickListener) {
        this.listNhanVien = listNhanVien;
        this.context = context;
        this.clickListener = clickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemNhanVienBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NhanVien nhanVien = listNhanVien.get(position);
        holder.itemBinding.tvTenNV.setText(nhanVien.getTenNV());
        holder.itemBinding.tvSDT.setText(String.valueOf(nhanVien.getSdt()));
        holder.itemBinding.tvVaiTro.setText("Nhân viên");
        if(nhanVien.getUrlAnh() != null){
            Glide.with(context).load(nhanVien.getUrlAnh()).into(holder.itemBinding.imvAnh);
        }
        holder.itemBinding.btnXoa.setOnClickListener(view -> {
            clickListener.xoaCLick(nhanVien);
        });
        holder.itemBinding.btnSua.setOnClickListener(view->{
            clickListener.suaClick(nhanVien);
        });
    }

    @Override
    public int getItemCount() {
        return listNhanVien.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ItemNhanVienBinding itemBinding;
        public ViewHolder(ItemNhanVienBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}
