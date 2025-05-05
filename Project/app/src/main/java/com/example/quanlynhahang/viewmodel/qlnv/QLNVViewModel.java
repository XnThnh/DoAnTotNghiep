package com.example.quanlynhahang.viewmodel.qlnv;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quanlynhahang.model.NhanVien;
import com.example.quanlynhahang.repository.NhanVienRepository;
import com.example.quanlynhahang.session.NhaHangSession;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QLNVViewModel extends ViewModel {

    private final NhanVienRepository nhanVienRepository;
    private final CompositeDisposable quanLyLuong = new CompositeDisposable();

    public QLNVViewModel(NhanVienRepository nhanVienRepository) {
        this.nhanVienRepository = nhanVienRepository;
    }

    private MutableLiveData<Boolean> _dangTai = new MutableLiveData<>();
    public LiveData<Boolean> getDangTai() {return _dangTai;}

    private MutableLiveData<Boolean> _thanhCong = new MutableLiveData<>();
    public LiveData<Boolean> getThanhCong() {return _thanhCong;}

    private MutableLiveData<List<NhanVien>> _listNhanVien = new MutableLiveData<>();
    public LiveData<List<NhanVien>> getListNhanVien() {
        _dangTai.setValue(true);
            quanLyLuong.add(nhanVienRepository.layDanhSachNV(NhaHangSession.getCurrentNhaHang().getMaNH()) // Lấy Single<NhaHang> từ Repository
                    .subscribeOn(Schedulers.io()) // <-- Schedulers từ RxJava3
                    .observeOn(AndroidSchedulers.mainThread()) // <-- AndroidSchedulers từ RxAndroid3
                    .subscribeWith(new DisposableSingleObserver<List<NhanVien>>() {
                        @Override
                        public void onSuccess(@NonNull List<NhanVien> nhanViens) {
                            _listNhanVien.setValue(nhanViens);
                            _dangTai.setValue(false);
                        }
                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.d("QLNVViewModel", "Lỗi lấy danh sách nhân viên:" + e.getMessage());
                            _dangTai.setValue(false);
                        }
                    }));
        return _listNhanVien;
    }

    public void addNhanVien(NhanVien nhanVien){
        _dangTai.setValue(true);
        quanLyLuong.add(nhanVienRepository.themNhanVien(nhanVien)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<NhanVien>(){
                    @Override
                    public void onSuccess(@NonNull NhanVien nhanVien) {
                        if(nhanVien != null){
                            _thanhCong.setValue(true);
                        }
                        else {
                            _thanhCong.setValue(false);
                        }
                        _dangTai.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("QLNVViewModel", "Lỗi thêm nhân viên:" + e.getMessage());
                    }
                }));
    }

    public void reloadListNhanVien() {
        _dangTai.setValue(true);
        quanLyLuong.add(nhanVienRepository.layDanhSachNV(NhaHangSession.getCurrentNhaHang().getMaNH())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<NhanVien>>() {
                    @Override
                    public void onSuccess(@NonNull List<NhanVien> nhanViens) {
                        _listNhanVien.setValue(nhanViens);
                        _dangTai.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("QLNVViewModel", "Lỗi reload danh sách: " + e.getMessage());
                        _dangTai.setValue(false);
                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        quanLyLuong.clear();
    }
}