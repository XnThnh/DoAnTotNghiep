package com.example.quanlynhahang.viewmodel.nhanvien;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quanlynhahang.model.HoaDon;
import com.example.quanlynhahang.model.MonAnTrongDonHang;
import com.example.quanlynhahang.repository.DonHangRepository;
import com.example.quanlynhahang.repository.HoaDonRepository;
import com.example.quanlynhahang.utils.Param;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChiTietHoaDonVm extends ViewModel {
    private HoaDonRepository hoaDonRepository;
    private DonHangRepository donHangRepository;
    private CompositeDisposable quanLyLuong = new CompositeDisposable();
    public ChiTietHoaDonVm(){
        hoaDonRepository = HoaDonRepository.getInstance();
        donHangRepository = DonHangRepository.getInstance();
    }

    private MutableLiveData<HoaDon> _hoaDon = new MutableLiveData<>();
    public LiveData<HoaDon> hoaDon = _hoaDon;


    private MutableLiveData<String> _thongBao = new MutableLiveData<>();
    public LiveData<String> thongBao = _thongBao;

    public void layHoaDonTheoID(int id){
        quanLyLuong.add(hoaDonRepository.layHoaDonTheoID(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<HoaDon>() {
                    @Override
                    public void onSuccess(@NonNull HoaDon hoaDon) {
                        if(hoaDon != null){
                            _hoaDon.setValue(hoaDon);
                            _thongBao.setValue(null);
                        }else{
                            _thongBao.setValue("Không tìm thấy hóa đơn");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _thongBao.setValue("Lỗi: " + e.getMessage());
                    }
                }));
    }

    private MutableLiveData<List<MonAnTrongDonHang>> _dsMonAnTrongDonHang = new MutableLiveData<>();
    public LiveData<List<MonAnTrongDonHang>> dsMonAnTrongDonHang(){
        return _dsMonAnTrongDonHang;
    }

    private MutableLiveData<String> _capNhatThanhCong = new MutableLiveData<>();
    public LiveData<String> capNhatThanhCong(){
        return _capNhatThanhCong;
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

    public void capNhatTrangThai(HoaDon hoaDon){
        quanLyLuong.add(hoaDonRepository.capNhatHoaDon(hoaDon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<HoaDon>() {

                    @Override
                    public void onSuccess(@NonNull HoaDon hoaDon) {
                        if(hoaDon.getTrangThai().equals("Đã thanh toán")){
                            _capNhatThanhCong.setValue(null);
                        }else{
                            _capNhatThanhCong.setValue("Cập nhật thất bại");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        _capNhatThanhCong.setValue("Lỗi: " + e.getMessage());
                    }
                }));
    }



}
