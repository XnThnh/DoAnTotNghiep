package com.example.quanlynhahang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlynhahang.databinding.ItemMonAnTrongListBinding;
import com.example.quanlynhahang.model.MonAn;
import com.example.quanlynhahang.utils.Param;

import java.util.ArrayList;
import java.util.List;

public class DanhSachMonAnOrderAdapter extends RecyclerView.Adapter<DanhSachMonAnOrderAdapter.ViewHolder>{
    private ArrayList<MonAn> listMonAn;
    private Context context;
    private List<MonAn> monAnDuocChon;

    public DanhSachMonAnOrderAdapter(Context context,ArrayList<MonAn> listMonAn) {
        this.context = context;
        this.listMonAn = listMonAn;
        monAnDuocChon = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMonAnTrongListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MonAn monAn = listMonAn.get(position);
        holder.binding.tvTenMonAn.setText(monAn.getTenMonAn());
        holder.binding.tvGia.setText(String.valueOf(monAn.getGiaMonAn()));
        Glide.with(context).asBitmap().load(Param.URL + "/" + monAn.getUrlAnh()).into(holder.binding.imvAnh);
        holder.binding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                holder.binding.checkBox.setChecked(true);
                monAnDuocChon.add(monAn);
            }else{
                holder.binding.checkBox.setChecked(false);
                monAnDuocChon.remove(monAn);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listMonAn.size();
    }

    public List<MonAn> getMonAnDuocChon() {
        return monAnDuocChon;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ItemMonAnTrongListBinding binding;

        public ViewHolder(ItemMonAnTrongListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
