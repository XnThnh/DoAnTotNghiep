package com.example.quanlynhahang.view.nhanvien.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.quanlynhahang.R;
import com.example.quanlynhahang.adapter.DanhSachMonAnDaOrderAdapter;
import com.example.quanlynhahang.adapter.DanhSachMonAnOrderAdapter;
import com.example.quanlynhahang.databinding.DialogLaydsVaThemMonBinding;
import com.example.quanlynhahang.databinding.FragmentOrderBinding;
import com.example.quanlynhahang.model.MonAn;
import com.example.quanlynhahang.model.MonAnFirebase;
import com.example.quanlynhahang.model.NhaHang;
import com.example.quanlynhahang.session.NhanVienSession;
import com.example.quanlynhahang.utils.Param;
import com.example.quanlynhahang.viewmodel.nhanvien.OrderViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderFragment extends Fragment {
    private static final NhaHang nhaHang = NhanVienSession.getNhanVien().getNhaHang();
    private static final String nhaHangID = nhaHang.getMaNH() + "_" + nhaHang.getTenNH();
    private DatabaseReference dbRef;
    private FragmentOrderBinding binding;
    private String getRef;
    private OrderViewModel orderViewModel;
    private ArrayList<MonAn> listMonAnTuDialog = new ArrayList<>();
    private DanhSachMonAnOrderAdapter adapter = null;
    private List<MonAn> dsMonAnDuocChon;
    private static DanhSachMonAnDaOrderAdapter orderAdapter;
    private ArrayList<MonAnFirebase> dsMonAn = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }
    public static OrderFragment newInstance(String maBan) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString("maBan", maBan);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentOrderBinding.bind(view);
        getRef = getArguments().getString("maBan");
        orderViewModel = new OrderViewModel();
        dsMonAnDuocChon = new ArrayList<>();
        orderAdapter = new DanhSachMonAnDaOrderAdapter(dsMonAn,requireContext(),nhaHangID,getRef);
        binding.recView.setAdapter(orderAdapter);
        layDSMonAnTuFirebase();


        dbRef = FirebaseDatabase.getInstance(Param.firebaseURL).getReference("ban")
                .child(NhanVienSession.getNhanVien().getNhaHang().getMaNH() + "_" + NhanVienSession.getNhanVien().getNhaHang().getTenNH() + "_dsBan");

        layDsMonAn();

        binding.floatingActionButton.setOnClickListener(v ->{
            moDanhSachVaThemMon();
        });

        binding.btnXacNhanOrder.setOnClickListener(v->{
            xacNhanOrderVaThemMonAnVaoDon();
        });

        observeTaoDonHangVaThemMon();
    }

    private void xacNhanOrderVaThemMonAnVaoDon() {
        DatabaseReference ref = FirebaseDatabase.getInstance(Param.firebaseURL).getReference("order").child(nhaHangID).child(getRef);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.getChildrenCount() > 0){
                    DataSnapshot maDonHangSnap = snapshot.child("maDonHang");
                    if(maDonHangSnap.exists() && Integer.parseInt(String.valueOf(maDonHangSnap.getValue())) == 0){
                        int id = NhanVienSession.getNhanVien().getMaNV();
                        int maBan = Integer.parseInt(String.valueOf(getRef.charAt(0)));
                        orderViewModel.taoDonHangVaThemMonAn(id,maBan,dsMonAn);
                    }
                    else {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Lỗi " + error.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }

    private void moDanhSachVaThemMon() {
        DialogLaydsVaThemMonBinding dialogLaydsVaThemMonBinding = DialogLaydsVaThemMonBinding.inflate(getLayoutInflater());
        adapter = new DanhSachMonAnOrderAdapter(requireContext(), listMonAnTuDialog);
        dialogLaydsVaThemMonBinding.recView.setAdapter(adapter);

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(dialogLaydsVaThemMonBinding.getRoot());
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.show();

        dialogLaydsVaThemMonBinding.btnHuy.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialogLaydsVaThemMonBinding.btnThem.setOnClickListener(view -> {
            List<MonAn> dsTrongAdapter = adapter.getMonAnDuocChon();
            if(dsTrongAdapter != null && !dsTrongAdapter.isEmpty()){
                dsMonAnDuocChon.clear();
                for(MonAn monAn : dsTrongAdapter){
                    if(dsMonAnDuocChon.contains(monAn)){
                        Toast.makeText(requireContext(), "Món " + monAn.getTenMonAn()  +" đã được chọn !", Toast.LENGTH_SHORT).show();
                    }
                    else dsMonAnDuocChon.add(monAn);
                }
                themDSOrderLenFirebase(dsMonAnDuocChon,getRef);
            }

            dialog.dismiss();
        });

    }

    private void layDsMonAn(){
        orderViewModel.getDsMonAn(NhanVienSession.getNhanVien().getNhaHang().getMaNH()).observe(getViewLifecycleOwner(),list ->{
            if(list != null){
                listMonAnTuDialog.clear();
                listMonAnTuDialog.addAll(list);
                if(adapter != null){
                    adapter.notifyDataSetChanged();
                }
                Log.d("TAG", "layDsMonAn: " + listMonAnTuDialog.size());
            }
        });
    }

    private void themDSOrderLenFirebase(List<MonAn> dsMonChon, String maBan){
        DatabaseReference getdbRef = FirebaseDatabase.getInstance(Param.firebaseURL).getReference("order").child(nhaHangID).child(maBan);
        getdbRef.child("maDonHang").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists() && !dsMonChon.isEmpty()) getdbRef.child("maDonHang").setValue(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Lỗi", Toast.LENGTH_SHORT);
            }
        });

        for (MonAn mon : dsMonChon) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", mon.getId());
            data.put("tenMonAn", mon.getTenMonAn());
            data.put("giaMonAn", mon.getGiaMonAn());
            data.put("urlAnh", mon.getUrlAnh());
            data.put("soLuong", 1); // mặc định
            getdbRef.child("dsMonAn")
                    .child(String.valueOf(mon.getId()))
                    .setValue(data);
        }
    }

    private void layDSMonAnTuFirebase(){
        DatabaseReference ref = FirebaseDatabase.getInstance(Param.firebaseURL).getReference("order").child(nhaHangID).child(getRef).child("dsMonAn");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dsMonAn.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    MonAnFirebase mon = snap.getValue(MonAnFirebase.class);
                    dsMonAn.add(mon);
                }
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "onCancelled: " + error.getMessage());
            }
        });
    }

    public static void huyOrder(){
        orderAdapter.removeOrder();
    }

    private void observeTaoDonHangVaThemMon(){
        orderViewModel.getTaoDonHangVaThemMonAn().observe(getViewLifecycleOwner(),donHang -> {
            if(donHang == null){
                Toast.makeText(requireContext(), "Lỗi tao don hang", Toast.LENGTH_SHORT).show();
            }
            else {
                DatabaseReference ref = FirebaseDatabase.getInstance(Param.firebaseURL).getReference("order").child(nhaHangID).child(getRef);
                ref.child("maDonHang").setValue(donHang.getMaDonHang());
                Toast.makeText(requireContext(), "Tạo đơn hàng thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

}