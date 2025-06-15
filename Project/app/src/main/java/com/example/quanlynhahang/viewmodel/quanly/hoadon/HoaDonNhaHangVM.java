package com.example.quanlynhahang.viewmodel.quanly.hoadon;



import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quanlynhahang.model.HoaDon;
import com.example.quanlynhahang.repository.HoaDonRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HoaDonNhaHangVM extends ViewModel {
    private HoaDonRepository hoaDonRepository;
    private CompositeDisposable quanLyLuong = new CompositeDisposable();
    public HoaDonNhaHangVM() {
        hoaDonRepository = HoaDonRepository.getInstance();
    }

    private MutableLiveData<List<HoaDon>> _dsHoaDon = new MutableLiveData<>();
    public MutableLiveData<List<HoaDon>> getDsHoaDon() {
        return _dsHoaDon;
    }

    private MutableLiveData<String> _thongBao = new MutableLiveData<>();
    public MutableLiveData<String> getThongBao() {
        return _thongBao;
    }

    public void layDSHoaDon(int maNH){
        quanLyLuong.add(hoaDonRepository.layDSTheoNH(maNH)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<HoaDon>>() {
                    @Override
                    public void onSuccess(@NonNull List<HoaDon> hoaDons) {
                        if(hoaDons != null && !hoaDons.isEmpty()){
                            _dsHoaDon.setValue(hoaDons);
                            _thongBao.setValue(null);
                        }else{
                            _thongBao.setValue("Không có dữ liệu");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _thongBao.setValue("Lỗi " + e.getMessage());
                    }
                }));
    }
}
