package com.example.quanlynhahang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhahang.R;
import com.example.quanlynhahang.databinding.ItemBanBinding;
import com.example.quanlynhahang.databinding.ItemBanChoNhanVienBinding;
import com.example.quanlynhahang.model.BanChoNhanVien;
import com.example.quanlynhahang.utils.Param;

import java.util.ArrayList;

public class BanChoNhanVienAdapter extends RecyclerView.Adapter<BanChoNhanVienAdapter.BanChoNhanVienViewHolder> {
    private ArrayList<BanChoNhanVien> dsBan;
    private ClickListener clickListener;
    private Context context;

    public BanChoNhanVienAdapter(ArrayList<BanChoNhanVien> dsBan, ClickListener clickListener, Context context) {
        this.dsBan = dsBan;
        this.clickListener = clickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public BanChoNhanVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BanChoNhanVienViewHolder(ItemBanChoNhanVienBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BanChoNhanVienViewHolder holder, int position) {
        BanChoNhanVien ban = dsBan.get(position);
        holder.binding.tvSoChoNgoi.setText("Số chỗ ngồi: "+ban.getSoChoNgoi());
        holder.binding.tvTenBan.setText(ban.getTenBan());
        holder.binding.tvTrangThai.setText(ban.getTrangThai());
        if(ban.getTrangThai().equals(Param.TRONG)){
            holder.binding.tvTrangThai.setTextColor(context.getColor(R.color.green));

        }
        else {
            holder.binding.tvTrangThai.setTextColor(context.getColor(R.color.gray));
        }
        holder.binding.getRoot().setOnClickListener(v -> {clickListener.onItemClick(ban);});
    }

    @Override
    public int getItemCount() {
        return dsBan.size();
    }

    public class BanChoNhanVienViewHolder extends RecyclerView.ViewHolder {
        private ItemBanChoNhanVienBinding binding;

        public BanChoNhanVienViewHolder(ItemBanChoNhanVienBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public interface ClickListener{
        void onItemClick(BanChoNhanVien ban);
    }
}
