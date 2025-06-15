package com.example.quanlynhahang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhahang.R;
import com.example.quanlynhahang.databinding.ItemHoaDonNvBinding;
import com.example.quanlynhahang.model.HoaDon;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder>{
    private List<HoaDon> dsHoaDon;
    private Context context;
    private HoaDonListener listener;
    private Boolean isNhanVien;

    public HoaDonAdapter(Context context, List<HoaDon> dsHoaDon,Boolean isNhanVien, HoaDonListener listener) {
        this.context = context;
       this.dsHoaDon = dsHoaDon;
        this.listener = listener;
        this.isNhanVien = isNhanVien;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemHoaDonNvBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoaDon hoaDon = dsHoaDon.get(position);

        String thoiGian = hoaDon.getThoiGian();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime localDateTime = LocalDateTime.parse(thoiGian);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss");
            thoiGian = localDateTime.format(formatter);
        }

        holder.binding.tvNgayThanhToan.setText(thoiGian);
        if(!isNhanVien){
            holder.binding.tvTenNhanVien.setText(hoaDon.getNhanVien().getTenNV());
        }

        holder.binding.tvBan.setText(hoaDon.getDonHang().getBan().getTenBan());
        holder.binding.tvTrangThai.setText(hoaDon.getTrangThai());
        holder.binding.tvTongTien.setText(String.format(Locale.getDefault(),"%,d",Double.valueOf(hoaDon.getTongTien()).intValue()));
        holder.binding.tvPhuongThucThanhToan.setText(hoaDon.getPhuongThuc());

        if(hoaDon.getTrangThai().equals("Đã thanh toán")){
            holder.binding.tvTrangThai.setTextColor(context.getColor(R.color.green));
        }
        else holder.binding.tvTrangThai.setTextColor(context.getColor(R.color.gray));

        holder.binding.getRoot().setOnClickListener(view -> listener.onClick(hoaDon));
    }

    @Override
    public int getItemCount() {
        return dsHoaDon.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemHoaDonNvBinding binding;

        public ViewHolder(ItemHoaDonNvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface HoaDonListener {
        void onClick(HoaDon hoaDon);
    }
}
