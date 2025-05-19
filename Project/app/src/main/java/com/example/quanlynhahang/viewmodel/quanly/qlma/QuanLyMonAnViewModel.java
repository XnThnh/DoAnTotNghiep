package com.example.quanlynhahang.viewmodel.quanly.qlma;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quanlynhahang.model.MonAn;
import com.example.quanlynhahang.repository.MonAnRepository;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class QuanLyMonAnViewModel extends ViewModel {
    private MonAnRepository monAnRepository;
    private CompositeDisposable quanLyLuong = new CompositeDisposable();

    public QuanLyMonAnViewModel(MonAnRepository monAnRepository) {
        this.monAnRepository = monAnRepository;
    }

    private MutableLiveData<Boolean> _danhTai = new MutableLiveData<>();
    public LiveData<Boolean> getDanhTai() {return _danhTai;}

    private MutableLiveData<Boolean> _themThanhCong = new MutableLiveData<>();
    public LiveData<Boolean> getThemThanhCong() {return _themThanhCong;}

    private MutableLiveData<List<MonAn>> _dsMonAn = new MutableLiveData<>();
    public LiveData<List<MonAn>> layDSMonAn(int nhaHangId){
        _danhTai.setValue(true);
        quanLyLuong.add(monAnRepository.layDSMonAnTheoNhaHang(nhaHangId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<MonAn>>() {
                    @Override
                    public void onSuccess(@NonNull List<MonAn> monAns) {
                        _dsMonAn.setValue(monAns);
                        _danhTai.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _danhTai.setValue(false);
                        Log.d("QuanLyMonAnViewModel", "Lỗi lấy danh sách món ăn " + e.getMessage());
                    }
                }));
        return _dsMonAn;
    }

    private MutableLiveData<String> _xoaThanhCong = new MutableLiveData<>();
    public LiveData<String> getXoaThanhCong() {return _xoaThanhCong;}

    public void reloadDsMonAn(int nhaHangId){
        quanLyLuong.add(monAnRepository.layDSMonAnTheoNhaHang(nhaHangId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<MonAn>>() {
                    @Override
                    public void onSuccess(@NonNull List<MonAn> monAns) {
                        _dsMonAn.setValue(monAns);
                        _danhTai.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _danhTai.setValue(false);
                        Log.d("QuanLyMonAnViewModel", "Lỗi lấy danh sách món ăn " + e.getMessage());
                    }
                }));
    }


    public void themMonAn(String tenMonAn, String moTa, String gia, int danhMucId, int nhaHangId, File file){
        _danhTai.setValue(true);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
        quanLyLuong.add(monAnRepository.themMonAn(RequestBody.create(MediaType.parse("text/plain"), tenMonAn),
                RequestBody.create(MediaType.parse("text/plain"), moTa),
                RequestBody.create(MediaType.parse("text/plain"), gia),
                RequestBody.create(MediaType.parse("text/plain"), String.valueOf(danhMucId)),
                RequestBody.create(MediaType.parse("text/plain"), String.valueOf(nhaHangId)),
                imagePart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MonAn>() {
                    @Override
                    public void onSuccess(@NonNull MonAn monAn) {
                        _danhTai.setValue(false);
                        _themThanhCong.setValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e)
                    {
                        _themThanhCong.setValue(false);
                        _danhTai.setValue(false);
                        Log.d("QuanLyMonAnViewModel", "Lỗi thêm món ăn " + e.getMessage());
                    }
                }));
    }

    public void xoaMonAn(int monAnID){
        _danhTai.setValue(true);
        quanLyLuong.add(monAnRepository.xoaMonAn(monAnID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->{
                    _danhTai.setValue(false);
                    _xoaThanhCong.setValue(null);
                },throwable -> {
                    _danhTai.setValue(false);
                    _xoaThanhCong.setValue(throwable.getMessage());
                    Log.d("QuanLyMonAnViewModel", "Lỗi xóa món ăn " + throwable.getMessage());
                }));
    }
}
