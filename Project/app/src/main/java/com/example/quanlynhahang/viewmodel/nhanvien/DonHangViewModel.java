package com.example.quanlynhahang.viewmodel.nhanvien;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quanlynhahang.model.MonAnTrongDonHang;
import com.example.quanlynhahang.repository.DonHangRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DonHangViewModel extends ViewModel {
    private DonHangRepository donHangRepository;
    private CompositeDisposable quanLyLuong = new CompositeDisposable();

    public DonHangViewModel(){
        donHangRepository = DonHangRepository.getInstance();
    }

    private MutableLiveData<List<MonAnTrongDonHang>> _dsMonAnTrongDonHang = new MutableLiveData<>();
    public LiveData<List<MonAnTrongDonHang>> dsMonAnTrongDonHang(){
        return _dsMonAnTrongDonHang;
    }

    public void getDsMonAnTrongDonHang(int maDonHang){
        quanLyLuong.add(donHangRepository.layMonAnTheoDonHang(maDonHang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<MonAnTrongDonHang>>() {
                    @Override
                    public void onSuccess(@NonNull List<MonAnTrongDonHang> list) {
                        if(list != null && !list.isEmpty()){
                            _dsMonAnTrongDonHang.setValue(list);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                            _dsMonAnTrongDonHang.setValue(null);
                            Log.d("DonHangViewModel", "DonHangViewModel : Error " + e.getMessage());
                    }
                }));
    }


}
