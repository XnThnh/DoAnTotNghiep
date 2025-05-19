package com.example.quanlynhahang.viewmodel.quanly.dangnhap;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quanlynhahang.model.NhaHang;
import com.example.quanlynhahang.repository.NhaHangRepository;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapNhaHangVM extends ViewModel {
    private final NhaHangRepository nhaHangRepository;
    private final FirebaseAuth mAuth;
    private final CompositeDisposable quanLyLuong = new CompositeDisposable();

    public DangNhapNhaHangVM(NhaHangRepository nhaHangRepository) {
        this.nhaHangRepository = nhaHangRepository;
        this.mAuth = FirebaseAuth.getInstance();
    }

    private MutableLiveData<Boolean> _dangTai = new MutableLiveData<>();

    public LiveData<Boolean> getDangTai() {
        return _dangTai;
    }

    private MutableLiveData<Boolean> _dangNhapThanhCong = new MutableLiveData<>();

    public LiveData<Boolean> getDangNhapThanhCong() {
        return _dangNhapThanhCong;
    }

    private MutableLiveData<NhaHang> _getNhaHangDaDangNhap = new MutableLiveData<>();
    public LiveData<NhaHang> getNhaHangDaDangNhap(){return _getNhaHangDaDangNhap;}

    private MutableLiveData<NhaHang> _getNhaHang = new MutableLiveData<>();
    public LiveData<NhaHang> getNhaHang(){return _getNhaHang;}

    public void dangNhap(String taiKhoan, String matKhau) {
        _dangTai.setValue(true);
        mAuth.signInWithEmailAndPassword(taiKhoan, matKhau).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        quanLyLuong.add(nhaHangRepository.dangNhapNhaHang(authResult.getUser().getUid())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableSingleObserver<NhaHang>() {
                                    @Override
                                    public void onSuccess(NhaHang nhaHang) {
                                        _dangTai.setValue(false);
                                        if (nhaHang != null) {
                                            _dangNhapThanhCong.setValue(true);
                                            _getNhaHangDaDangNhap.setValue(nhaHang);
                                        } else {
                                            _dangNhapThanhCong.setValue(false);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d("DangNhapError", e.getMessage());
                                        _dangTai.setValue(false);
                                        _dangNhapThanhCong.setValue(false);
                                    }
                                }));
                    }
                })
                .addOnFailureListener(e -> {
                    _dangTai.setValue(false);
                    _dangNhapThanhCong.setValue(false);
                });
    }

    public void getNhaHang(int id){
        quanLyLuong.add(nhaHangRepository.getNhaHang(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<NhaHang>() {
                    @Override
                    public void onSuccess(@NonNull NhaHang nhaHang) {
                        _getNhaHang.setValue(nhaHang);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _getNhaHang.setValue(null);
                    }
                }));
    }
}

