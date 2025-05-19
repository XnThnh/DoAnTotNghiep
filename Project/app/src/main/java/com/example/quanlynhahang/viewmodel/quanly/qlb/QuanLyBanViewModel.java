package com.example.quanlynhahang.viewmodel.quanly.qlb;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quanlynhahang.model.Ban;
import com.example.quanlynhahang.repository.BanRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuanLyBanViewModel extends ViewModel {
    private BanRepository banRepository;
    private CompositeDisposable quanLyLuong = new CompositeDisposable();

    public QuanLyBanViewModel(BanRepository banRepository) {
        this.banRepository = banRepository;
    }

    private MutableLiveData<Boolean> _dangTai = new MutableLiveData<>();
    public LiveData<Boolean> getDangTai() {return _dangTai;}

    private MutableLiveData<Boolean> _layDanhSachThanhCong = new MutableLiveData<>();
    public LiveData<Boolean> getLayDanhSachThanhCong() {return _layDanhSachThanhCong;}

    private MutableLiveData<Boolean> _themThanhCong = new MutableLiveData<>();
    public LiveData<Boolean> getThemThanhCong() {return _themThanhCong;}

    private MutableLiveData<Boolean> _capNhatThanhCong = new MutableLiveData<>();
    public LiveData<Boolean> getCapNhatThanhCong() {return _capNhatThanhCong;}

    private MutableLiveData<Boolean> _xoaThanhCong = new MutableLiveData<>();
    public LiveData<Boolean> getXoaThanhCong() {return _xoaThanhCong;}


    private MutableLiveData<List<Ban>> _danhSachBan = new MutableLiveData<>();
    public LiveData<List<Ban>> getDanhSachBan(int maNH){
        _dangTai.setValue(true);
            quanLyLuong.add(banRepository.getBanByNhaHang(maNH)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<List<Ban>>() {
                        @Override
                        public void onSuccess(@NonNull List<Ban> dsBan) {
                            if(!dsBan.isEmpty()){
                                _danhSachBan.setValue(dsBan);
                                _layDanhSachThanhCong.setValue(true);
                                Log.d("QuanLyBanViewModel", "danh sách bàn:" + dsBan.size());
                            }
                            else {
                                _layDanhSachThanhCong.setValue(false);
                                Log.d("QuanLyBanViewModel", "danh sách bàn: 0" + dsBan.size());
                            }
                            _dangTai.setValue(false);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            _layDanhSachThanhCong.setValue(false);
                            _dangTai.setValue(false);
                            Log.d("QuanLyBanViewModel", "Lỗi lấy danh sách bàn:" + e.getMessage());
                        }
                    }));
        return _danhSachBan;
    }

    public void reloadDanhSachBan(int maNH){
        quanLyLuong.add(banRepository.getBanByNhaHang(maNH)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Ban>>() {
                    @Override
                    public void onSuccess(@NonNull List<Ban> dsBan) {
                        if(!dsBan.isEmpty()){
                            _danhSachBan.setValue(dsBan);
                            _layDanhSachThanhCong.setValue(true);
                            Log.d("QuanLyBanViewModel", "danh sách bàn:" + dsBan.size());
                        }
                        else {
                            _layDanhSachThanhCong.setValue(false);
                            Log.d("QuanLyBanViewModel", "danh sách bàn: 0" + dsBan.size());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _layDanhSachThanhCong.setValue(false);
                        Log.d("QuanLyBanViewModel", "Lỗi lấy danh sách bàn:" + e.getMessage());
                    }
                }));
    }

    public void themBan(Ban ban){
        _dangTai.setValue(true);
        quanLyLuong.add(banRepository.themBan(ban)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Ban>() {
                    @Override
                    public void onSuccess(@NonNull Ban ban) {
                        _themThanhCong.setValue(true);
                        _dangTai.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _themThanhCong.setValue(false);
                        _dangTai.setValue(false);
                        Log.d("QuanLyBanViewModel", "Lỗi thêm bàn:" + e.getMessage());
                    }
                }));
    }

    public void capNhatBan(Ban ban){
        _dangTai.setValue(true);
        quanLyLuong.add(banRepository.capNhatBan(ban)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Ban>() {
                    @Override
                    public void onSuccess(@NonNull Ban ban) {
                        _capNhatThanhCong.setValue(true);
                        _dangTai.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _capNhatThanhCong.setValue(false);
                        _dangTai.setValue(false);
                        Log.d("QuanLyBanViewModel", "Lỗi cap nhat bàn:" + e.getMessage());
                    }
                }));
    }

    public void xoaBan(int maBan){
        _dangTai.setValue(true);
        quanLyLuong.add(banRepository.xoaBan(maBan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    _xoaThanhCong.setValue(true);
                    _dangTai.setValue(false);
                },throwable -> {
                    _xoaThanhCong.setValue(false);
                    _dangTai.setValue(false);
                    Log.d("QuanLyBanViewModel", "Lỗi xóa bàn:" + throwable.getMessage());
                }));
    }

}
