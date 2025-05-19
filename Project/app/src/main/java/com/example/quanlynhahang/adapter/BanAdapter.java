package com.example.quanlynhahang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhahang.databinding.ItemBanBinding;
import com.example.quanlynhahang.model.Ban;

import java.util.ArrayList;

public class BanAdapter extends RecyclerView.Adapter<BanAdapter.ViewHolder> {
    private ArrayList<Ban> listBan;
    private onItemClick onItemClick;

    public BanAdapter(ArrayList<Ban> listBan, BanAdapter.onItemClick onItemClick) {
        this.listBan = listBan;
        this.onItemClick = onItemClick;
    }

    public interface onItemClick{
        void onItemClick(Ban ban);
        void onItemLongClick(Ban ban);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemBanBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ban ban = listBan.get(position);
        holder.binding.tvBanSo.setText(ban.getTenBan());
        holder.binding.tvSoChoNgoi.setText("Số chỗ ngồi : "+String.valueOf(ban.getSoChoNgoi()));
        holder.binding.getRoot().setOnClickListener(onClickView ->{
            onItemClick.onItemClick(ban);
        });
        holder.binding.getRoot().setOnLongClickListener(onLongClick ->{
            onItemClick.onItemLongClick(ban);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return listBan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemBanBinding binding;

        public ViewHolder(ItemBanBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
