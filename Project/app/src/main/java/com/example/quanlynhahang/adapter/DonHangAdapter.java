package com.example.quanlynhahang.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlynhahang.databinding.ItemMonAnTrongOrderBinding;
import com.example.quanlynhahang.databinding.LayoutItemMonAnDonHangBinding;
import com.example.quanlynhahang.model.MonAnTrongDonHang;
import com.example.quanlynhahang.utils.Param;

import java.util.List;
import java.util.Locale;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHolder>{
    private List<MonAnTrongDonHang> dsMonAn;
    private Context context;

    public DonHangAdapter(Context context,List<MonAnTrongDonHang> dsMonAn) {
        this.dsMonAn = dsMonAn;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutItemMonAnDonHangBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MonAnTrongDonHang monAn = dsMonAn.get(position);
        holder.binding.tvTenMonAn.setText(monAn.getMonAn().getTenMonAn());
        holder.binding.tvGia.setText(String.format(Locale.getDefault(),"%,d",monAn.getMonAn().getGiaMonAn()));
        holder.binding.tvSoLuong.setText(Integer.toString(monAn.getSoLuong()));
        Glide.with(context).asBitmap().load(Param.URL + "/" + monAn.getMonAn().getUrlAnh()).into(holder.binding.imvAnh);
        holder.binding.tvTongTien.setText(String.format(Locale.getDefault(),"%,d",monAn.getMonAn().getGiaMonAn() * monAn.getSoLuong()));
    }

    @Override
    public int getItemCount() {
        return dsMonAn.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private LayoutItemMonAnDonHangBinding binding;
        public ViewHolder(LayoutItemMonAnDonHangBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
