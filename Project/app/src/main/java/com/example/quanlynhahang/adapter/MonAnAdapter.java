package com.example.quanlynhahang.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlynhahang.R;
import com.example.quanlynhahang.databinding.ItemMonAnBinding;
import com.example.quanlynhahang.model.MonAn;
import com.example.quanlynhahang.utils.Param;

import java.util.List;

public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.MonAnViewHolder> {
    private Context context;
    private List<MonAn> dsMonAn;
    private onClickListener onClickListener;

    public MonAnAdapter(Context context, List<MonAn> dsMonAn, MonAnAdapter.onClickListener onClickListener) {
        this.context = context;
        this.dsMonAn = dsMonAn;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MonAnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MonAnViewHolder(ItemMonAnBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MonAnViewHolder holder, int position) {
        MonAn monAn = dsMonAn.get(position);
        holder.binding.tvTenMonAn.setText(monAn.getTenMonAn());
        holder.binding.tvGia.setText(String.valueOf(monAn.getGiaMonAn()));
        holder.binding.tvMoTa.setText(monAn.getMoTa());
        Glide.with(context).asBitmap().load(Param.URL + "/" + monAn.getUrlAnh()).into(holder.binding.imvAnh);
        Log.d("TAG", "onBindViewHolder: "+Param.URL + "/images/" + monAn.getUrlAnh());
        holder.binding.btnSua.setOnClickListener(view -> {onClickListener.suaClick(monAn);});
        holder.binding.btnXoa.setOnClickListener(view -> {onClickListener.xoaClick(monAn);});
    }

    @Override
    public int getItemCount() {
        return dsMonAn.size();
    }

    public class MonAnViewHolder extends RecyclerView.ViewHolder {
        private ItemMonAnBinding binding;

        public MonAnViewHolder(ItemMonAnBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public interface onClickListener{
        void suaClick(MonAn monAn);
        void xoaClick(MonAn monAn);
    }
}
