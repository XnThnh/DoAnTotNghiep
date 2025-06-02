package com.example.quanlynhahang.viewmodel.nhanvien;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quanlynhahang.model.Ban;
import com.example.quanlynhahang.model.BanChoNhanVien;
import com.example.quanlynhahang.model.NhanVien;
import com.example.quanlynhahang.repository.BanRepository;
import com.example.quanlynhahang.utils.Param;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NhanVienHomeViewModel extends ViewModel {
    private BanRepository banRepository;
    private NhanVien nhanVien;
    private DatabaseReference dbRef;
    private FirebaseDatabase database;
    private CompositeDisposable quanLyLuong = new CompositeDisposable();

    public NhanVienHomeViewModel(NhanVien nhanVien) {
        this.banRepository = BanRepository.getInstance();
        this.nhanVien = nhanVien;
        database = FirebaseDatabase.getInstance(Param.firebaseURL);
        dbRef = database.getReference("ban").child(nhanVien.getNhaHang().getMaNH() + "_" + nhanVien.getNhaHang().getTenNH() + "_dsBan");
    }
    private MutableLiveData<List<Ban>> _dsBan = new MutableLiveData<>();
    public MutableLiveData<List<Ban>> getDsBan() {
        quanLyLuong.add(banRepository.getBanByNhaHang(nhanVien.getNhaHang().getMaNH())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Ban>>() {
                    @Override
                    public void onSuccess(@NonNull List<Ban> bans) {
                        _dsBan.setValue(bans);
                        Log.d("NhanVienHomeViewModel", "onSuccess: " + bans.size() + nhanVien.getNhaHang().getMaNH() + "_" + nhanVien.getNhaHang().getTenNH() + "_dsBan");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _dsBan.setValue(null);
                        Log.d("NhanVienHomeViewModel", "onError: "+ e.getMessage());
                    }
                }));
        return _dsBan;
    }

    private MutableLiveData<List<BanChoNhanVien>> _dsBanTrenFireBase = new MutableLiveData<>();
    public LiveData<List<BanChoNhanVien>> getDsBanTrenFireBase() {
        return _dsBanTrenFireBase;
    }

    private MutableLiveData<String> _moBan = new MutableLiveData<>();
    public LiveData<String> getMoBan() {return _moBan;}

    public void loadBan(List<Ban> dsBan){
        dbRef.get().addOnSuccessListener(dataSnapshot -> {
           if(!dataSnapshot.exists()){
               if(dsBan != null){
                   for(Ban ban : dsBan){
                       Map<String,Object> banMap = new HashMap<>();
                       banMap.put(Param.MA_BAN,ban.getMaBan());
                       banMap.put(Param.TEN_BAN,ban.getTenBan());
                       banMap.put(Param.SO_CHO_NGOI,ban.getSoChoNgoi());
                       banMap.put(Param.MA_NV,"");
                       banMap.put(Param.TRANG_THAI, Param.TRONG);
                       dbRef.child(ban.getMaBan() + "_" + ban.getTenBan()).setValue(banMap);
                   }
               }
               else Log.d("NhanVienHomeViewModel", "loadBan: khong co ban");
           }
               ArrayList<BanChoNhanVien> banChoNhanVienList = new ArrayList<>();
                  for (DataSnapshot snap : dataSnapshot.getChildren()) {
                      BanChoNhanVien ban = snap.getValue(BanChoNhanVien.class);
                      banChoNhanVienList.add(ban);
                  }
               _dsBanTrenFireBase.setValue(banChoNhanVienList);
              Log.d("NhanVienHomeViewModel", "loadBan: co ban" + banChoNhanVienList.size());

        }).addOnFailureListener(e -> Log.d("NhanVienHomeViewModel", "loadBan: " + e.getMessage()));
    }

    public void moBan(BanChoNhanVien banChoNhanVien){
        String ref = banChoNhanVien.getMaBan()+"_"+banChoNhanVien.getTenBan();
        DatabaseReference banRef = dbRef.child(ref);
        banRef.get().addOnSuccessListener(dataSnapshot -> {
            String trangThai = dataSnapshot.child(Param.TRANG_THAI).getValue(String.class);
            if(trangThai.equals(Param.TRONG)){
                Map<String,Object> update = new HashMap<>();
                update.put(Param.TRANG_THAI,Param.DANG_PHUC_VU);
                update.put(Param.MA_NV,nhanVien.getFirebaseUID());
                banRef.updateChildren(update);
                _moBan.setValue(ref);
            }
            else {
                String uidNhanVien = dataSnapshot.child(Param.MA_NV).getValue(String.class);
                if(uidNhanVien != null && uidNhanVien.equals(nhanVien.getFirebaseUID())){
                    _moBan.setValue(ref);
                }
                else _moBan.setValue(null);
            }
        }).addOnFailureListener(error ->{});
    }

    public void observeBanTrenFirebase(){
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                List<BanChoNhanVien> dsBan = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    BanChoNhanVien ban = child.getValue(BanChoNhanVien.class);
                    if (ban != null) dsBan.add(ban);
                }
                _dsBanTrenFireBase.setValue(dsBan); // <-- Cập nhật LiveData
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
                Log.d("NhanVienHomeViewModel", "observeBanTrenFirebase: Loi " + error.getMessage());
            }
        });
    }
}
