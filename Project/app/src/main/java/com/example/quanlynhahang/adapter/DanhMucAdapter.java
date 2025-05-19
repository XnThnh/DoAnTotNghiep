package com.example.quanlynhahang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhahang.R;
import com.example.quanlynhahang.databinding.ItemDanhMucBinding;
import com.example.quanlynhahang.model.DanhMuc;

import java.util.ArrayList;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.ViewHolder> {
    private ArrayList<DanhMuc> dsDanhMuc;
    private Context context;
    private onClick onClick;

    public DanhMucAdapter(ArrayList<DanhMuc> dsDanhMuc, Context context, DanhMucAdapter.onClick onClick) {
        this.dsDanhMuc = dsDanhMuc;
        this.context = context;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemDanhMucBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DanhMuc danhMuc = dsDanhMuc.get(position);
        holder.binding.tvTenDanhMuc.setText(danhMuc.getTenDanhMuc());
        holder.binding.btnSua.setOnClickListener(view -> onClick.suaClick(danhMuc));
        holder.binding.btnXoa.setOnClickListener(view -> onClick.xoaClick(danhMuc));
    }

    @Override
    public int getItemCount() {
        return dsDanhMuc.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemDanhMucBinding binding;

        public ViewHolder(ItemDanhMucBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public interface onClick{
        void suaClick(DanhMuc danhMuc);
        void xoaClick(DanhMuc danhMuc);
    }
}
