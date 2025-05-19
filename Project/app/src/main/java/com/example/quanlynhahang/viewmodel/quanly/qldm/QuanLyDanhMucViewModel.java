package com.example.quanlynhahang.viewmodel.quanly.qldm;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quanlynhahang.model.DanhMuc;
import com.example.quanlynhahang.repository.DanhMucRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuanLyDanhMucViewModel extends ViewModel {

    private DanhMucRepository danhMucRepository;
    private CompositeDisposable quanLyLuong = new CompositeDisposable();

    public QuanLyDanhMucViewModel(DanhMucRepository danhMucRepository) {
        this.danhMucRepository = danhMucRepository;
    }

    private MutableLiveData<Boolean> _dangTai = new MutableLiveData<>();
    public LiveData<Boolean> getDangTai() {return _dangTai;}

    private MutableLiveData<Boolean> _daLayDanhSach = new MutableLiveData<>();
    public LiveData<Boolean> getDaLayDanhSach() {return _daLayDanhSach;}

    private MutableLiveData<Boolean> _themThanhCong = new MutableLiveData<>();
    public LiveData<Boolean> getThemThanhCong() {return _themThanhCong;}

    private MutableLiveData<Boolean> _capNhatThanhCong = new MutableLiveData<>();
    public LiveData<Boolean> getCapNhatThanhCong() {return _capNhatThanhCong;}

    private MutableLiveData<Boolean> _xoaThanhCong = new MutableLiveData<>();
    public LiveData<Boolean> getXoaThanhCong() {return _xoaThanhCong;}

    private MutableLiveData<List<DanhMuc>> _listDanhMuc = new MutableLiveData<>();


    public LiveData<List<DanhMuc>> getListDanhMuc(int maNH){
        _dangTai.setValue(true);
        quanLyLuong.add(danhMucRepository.layDanhSachDanhMuc(maNH)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DanhMuc>>() {
                    @Override
                    public void onSuccess(@NonNull List<DanhMuc> dsDanhMuc) {
                        _dangTai.setValue(false);
                        _listDanhMuc.setValue(dsDanhMuc);
                        _daLayDanhSach.setValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("QuanLyDanhMucViewModel", "Lỗi lấy danh sách danh mục:" + e.getMessage());
                        _daLayDanhSach.setValue(false);
                    }
                }));
        return _listDanhMuc;
    }

    public void themDanhMuc(DanhMuc danhMuc){
        _dangTai.setValue(true);
        quanLyLuong.add(danhMucRepository.themDanhMuc(danhMuc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    _themThanhCong.setValue(true);
                    _dangTai.setValue(false);
                },throwable -> {
                    _themThanhCong.setValue(false);
                    _dangTai.setValue(false);
                    Log.d("QuanLyDanhMucViewModel", "Lỗi thêm danh mục:" + throwable.getMessage());
                }));
    }

    public void capNhatDanhMuc(DanhMuc danhMuc){
        _dangTai.setValue(true);
        quanLyLuong.add(danhMucRepository.capNhatDanhMuc(danhMuc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()->{
                            _capNhatThanhCong.setValue(true);
                            _dangTai.setValue(false);
                        },throwable ->{
                            _capNhatThanhCong.setValue(false);
                            _dangTai.setValue(false);
                        }
                ));
    }

    public void xoaDanhMuc(DanhMuc danhMuc){
        _dangTai.setValue(true);
        quanLyLuong.add(danhMucRepository.xoaDanhMuc(danhMuc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()->{
                            _xoaThanhCong.setValue(true);
                            _dangTai.setValue(false);
                        }
                        , throwable -> {
                            _xoaThanhCong.setValue(false);
                            _dangTai.setValue(false);
                            Log.d("QuanLyDanhMucViewModel", "Lỗi xóa danh mục:" + throwable.getMessage());
                        }));
    }

    public void reloadListDanhMuc(int maNH){
        _dangTai.setValue(true);
        quanLyLuong.add(danhMucRepository.layDanhSachDanhMuc(maNH)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DanhMuc>>() {
                    @Override
                    public void onSuccess(@NonNull List<DanhMuc> dsDanhMuc) {
                        _dangTai.setValue(false);
                        _listDanhMuc.setValue(dsDanhMuc);
                        _daLayDanhSach.setValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("QuanLyDanhMucViewModel", "Lỗi lấy danh sách danh mục:" + e.getMessage());
                        _daLayDanhSach.setValue(false);
                    }
                }));
    }

}