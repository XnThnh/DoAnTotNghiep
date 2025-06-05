package com.example.quanlynhahang.viewmodel.nhanvien;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quanlynhahang.model.HoaDon;
import com.example.quanlynhahang.model.MonAnTrongDonHang;
import com.example.quanlynhahang.repository.DonHangRepository;
import com.example.quanlynhahang.repository.HoaDonRepository;
import com.example.quanlynhahang.retrofit.HoaDonAPI;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HoaDonViewmodel extends ViewModel {
    private final HoaDonRepository hoaDonRepository;
    private DonHangRepository donHangRepository;
    private CompositeDisposable quanLyLuong = new CompositeDisposable();

    public HoaDonViewmodel() {
        this.hoaDonRepository = HoaDonRepository.getInstance();
        this.donHangRepository = DonHangRepository.getInstance();
    }

    private MutableLiveData<String> _taoHoaDon = new MutableLiveData<>();

    public LiveData<String> getTaoHoaDon() {
        return _taoHoaDon;
    }

    private MutableLiveData<Integer> _tongTien = new MutableLiveData<>();

    public LiveData<Integer> getTongTien() {
        return _tongTien;
    }

    private MutableLiveData<List<MonAnTrongDonHang>> _dsMonAnTrongDonHang = new MutableLiveData<>();

    public LiveData<List<MonAnTrongDonHang>> dsMonAnTrongDonHang() {
        return _dsMonAnTrongDonHang;
    }

    public void getDsMonAnTrongDonHang(int maDonHang) {
        quanLyLuong.add(donHangRepository.layMonAnTheoDonHang(maDonHang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<MonAnTrongDonHang>>() {
                    @Override
                    public void onSuccess(@NonNull List<MonAnTrongDonHang> list) {
                        if (list != null && !list.isEmpty()) {
                            _dsMonAnTrongDonHang.setValue(list);
                            int tong = 0;
                            for (MonAnTrongDonHang monAn : list) {
                                tong += monAn.getSoLuong() * monAn.getMonAn().getGiaMonAn();
                            }
                            _tongTien.setValue(tong);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _dsMonAnTrongDonHang.setValue(null);
                        Log.d("DonHangViewModel", "DonHangViewModel : Error " + e.getMessage());
                    }
                }));
    }

    public void taoHoaDon(int maDonHang, int maNhanVien, int maNhaHang, String phuongThuc) {
        List<MonAnTrongDonHang> list = _dsMonAnTrongDonHang.getValue();
        int tong = 0;
        for (MonAnTrongDonHang monAn : list) {
            tong += monAn.getSoLuong() * monAn.getMonAn().getGiaMonAn();
        }

        quanLyLuong.add(hoaDonRepository.taoHoaDon(maDonHang, maNhanVien, maNhaHang, tong, phuongThuc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<HoaDon>() {
                    @Override
                    public void onSuccess(@NonNull HoaDon hoaDon) {
                        if (hoaDon != null) {
                            _taoHoaDon.setValue(null);
                            Log.d("HoaDon", "HoaDon " + hoaDon.toString());
                        } else {
                            _taoHoaDon.setValue("Hóa đơn rỗng");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("HoaDon", "Error: " + e.getMessage());
                    }
                }));
    }
}
