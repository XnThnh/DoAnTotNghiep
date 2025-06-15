package com.example.quanlynhahang.viewmodel.nhanvien;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quanlynhahang.model.DonHang;
import com.example.quanlynhahang.model.HoaDon;
import com.example.quanlynhahang.model.MonAn;
import com.example.quanlynhahang.model.MonAnFirebase;
import com.example.quanlynhahang.model.MonAnTrongDonHang;
import com.example.quanlynhahang.repository.DonHangRepository;
import com.example.quanlynhahang.repository.HoaDonRepository;
import com.example.quanlynhahang.repository.MonAnRepository;
import com.example.quanlynhahang.view.nhanvien.fragment.DonHangFragment;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderViewModel extends ViewModel {
    private MonAnRepository monAnRepository;
    private DonHangRepository donHangRepository;

    private CompositeDisposable quanLyLuong = new CompositeDisposable();

    public OrderViewModel() {
        this.monAnRepository = MonAnRepository.getInstance();
        this.donHangRepository = DonHangRepository.getInstance();
    }

    private MutableLiveData<List<MonAn>> dsMonAn = new MutableLiveData<>();

    public LiveData<List<MonAn>> getDsMonAn(int maNH) {

        quanLyLuong.add(monAnRepository.layDSMonAnTheoNhaHang(maNH)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<MonAn>>() {
                    @Override
                    public void onSuccess(@NonNull List<MonAn> monAns) {
                        if (monAns != null) {
                            dsMonAn.setValue(monAns);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("TAG", "onError: " + e.getMessage());
                        dsMonAn.setValue(null);
                    }
                }));
        return dsMonAn;

    }

    private MutableLiveData<DonHang> _taoDonHangVaThemMonAn = new MutableLiveData<>();

    public LiveData<DonHang> getTaoDonHangVaThemMonAn() {
        return _taoDonHangVaThemMonAn;
    }


    public void taoDonHangVaThemMonAn(int manv, int maBan, List<MonAnFirebase> dsMonAn) {
        quanLyLuong.add(donHangRepository.taoDonHangVaThemMon(manv, maBan, dsMonAn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<DonHang>() {
                    @Override
                    public void onSuccess(@NonNull DonHang donHang) {
                        if (donHang != null) {
                            _taoDonHangVaThemMonAn.setValue(donHang);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _taoDonHangVaThemMonAn.setValue(null);
                        Log.d("OrderViewModel", "OrderViewModel: Error " + e);
                    }
                }));
    }

    private MutableLiveData<String> _taoHoaDon = new MutableLiveData<>();

    public LiveData<String> getTaoHoaDon() {
        return _taoHoaDon;
    }

    private MutableLiveData<String> _themMonAn = new MutableLiveData<>();

    public LiveData<String> getThemMonAn() {
        return _themMonAn;
    }

    public void themMonAn(int maDonHang,List<MonAnFirebase> dsMonAn){
        quanLyLuong.add(donHangRepository.themMonVaoDon(maDonHang, dsMonAn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<DonHang>() {
                    @Override
                    public void onSuccess(@NonNull DonHang donHang) {
                        _themMonAn.setValue(null);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _themMonAn.setValue("Lỗi thêm món vào đơn " + e.getMessage());
                    }
                }));
    }




}
