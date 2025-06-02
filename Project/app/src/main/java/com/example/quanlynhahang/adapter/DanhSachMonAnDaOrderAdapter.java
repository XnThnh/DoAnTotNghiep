package com.example.quanlynhahang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlynhahang.databinding.ItemMonAnTrongOrderBinding;
import com.example.quanlynhahang.model.MonAn;
import com.example.quanlynhahang.model.MonAnFirebase;
import com.example.quanlynhahang.utils.Param;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DanhSachMonAnDaOrderAdapter extends RecyclerView.Adapter<DanhSachMonAnDaOrderAdapter.ViewHolder> {
    private ArrayList<MonAnFirebase> dsMonAn;
    private Context context;
    private DatabaseReference dbRef;
    private String maBan;
    private String nhaHangId;

    public DanhSachMonAnDaOrderAdapter(ArrayList<MonAnFirebase> dsMonAn, Context context,String nhaHangId, String maBan) {
        this.dsMonAn = dsMonAn;
        this.context = context;
        this.maBan = maBan;
        this.nhaHangId = nhaHangId;
        dbRef = FirebaseDatabase.getInstance(Param.firebaseURL).getReference("order").child(nhaHangId).child(maBan);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMonAnTrongOrderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MonAnFirebase monAn = dsMonAn.get(position);
        holder.binding.tvTenMonAn.setText(monAn.getTenMonAn());
        holder.binding.tvGia.setText(monAn.getGiaMonAn() + "");
        holder.binding.tvSoLuong.setText(monAn.getSoLuong() + "");
        Glide.with(context).asBitmap().load(Param.URL + "/" + monAn.getUrlAnh()).into(holder.binding.imvAnh);

        holder.binding.btnCong.setOnClickListener(view -> {
            int soLuong = monAn.getSoLuong();
            dbRef.child("dsMonAn").child(String.valueOf(monAn.getId())).child("soLuong").setValue(soLuong + 1);
        });

        holder.binding.btnTru.setOnClickListener(view -> {
            int newSoLuong = monAn.getSoLuong() - 1;
            if (newSoLuong <= 0){
                Toast.makeText(context, "Đã xóa món " + monAn.getTenMonAn(), Toast.LENGTH_SHORT).show();
                dbRef.child("dsMonAn").child(String.valueOf(monAn.getId())).removeValue(); // xóa món nếu giảm về 0
            }
            else
                dbRef.child("dsMonAn").child(String.valueOf(monAn.getId())).child("soLuong").setValue(newSoLuong);
        });
    }

    @Override
    public int getItemCount() {
        return dsMonAn.size();
    }

    public void removeOrder(){
        dbRef.removeValue();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMonAnTrongOrderBinding binding;
        public ViewHolder(ItemMonAnTrongOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
