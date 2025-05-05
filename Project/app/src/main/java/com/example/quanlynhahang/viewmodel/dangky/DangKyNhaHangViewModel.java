package com.example.quanlynhahang.viewmodel.dangky;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quanlynhahang.model.NhaHang;
import com.example.quanlynhahang.repository.NhaHangRepository;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangKyNhaHangViewModel extends ViewModel {
    private final NhaHangRepository nhaHangRepository;
    private final CompositeDisposable quanLyLangNghe = new CompositeDisposable();

    private final MutableLiveData<Boolean> _dangTai = new MutableLiveData<>();

    public LiveData<Boolean> getDangTai() {
        return _dangTai;
    }

    // LiveData cho thông báo lỗi
    private final MutableLiveData<String> _thongBaoLoi = new MutableLiveData<>(); // Tên tiếng Việt: Thông báo lỗi

    public LiveData<String> getThongBaoLoi() {
        return _thongBaoLoi;
    }

    private final MutableLiveData<NhaHang> _suKienDangKyThanhCong = new MutableLiveData<>(); // Tên tiếng Việt: Sự kiện đăng ký thành công

    public LiveData<NhaHang> getSuKienDangKyThanhCong() {
        return _suKienDangKyThanhCong;
    }

    public DangKyNhaHangViewModel(NhaHangRepository nhaHangRepository) {
        this.nhaHangRepository = nhaHangRepository;
    }

    public void thucHienDangKy(NhaHang nhaHang) {
        // Báo màn hình biết đang tải
        _dangTai.setValue(true);
        _thongBaoLoi.setValue(null); // Xóa lỗi cũ

        quanLyLangNghe.add(nhaHangRepository.dangKyNhaHang(nhaHang) // Lấy Single<NhaHang> từ Repository
                        .subscribeOn(Schedulers.io()) // <-- Schedulers từ RxJava3
                        .observeOn(AndroidSchedulers.mainThread()) // <-- AndroidSchedulers từ RxAndroid3
                        .subscribeWith(new DisposableSingleObserver<NhaHang>() { // <-- Observer từ RxJava3
                            @Override // Hàm này chạy khi gọi API thành công
                            public void onSuccess(NhaHang ketQua) {
                                // Đăng ký thành công
                                _dangTai.setValue(false);
                                _thongBaoLoi.setValue(null);
                                _suKienDangKyThanhCong.setValue(ketQua); // Kích hoạt sự kiện thành công
                            }
                            @Override // Hàm này chạy khi gọi API thất bại
                            public void onError(Throwable e) {
                                // Xảy ra lỗi
                                _dangTai.setValue(false);
                                _thongBaoLoi.setValue("Đăng ký thất bại: " + e.getMessage());
                                e.printStackTrace(); // In log lỗi
                            }
                        }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        quanLyLangNghe.clear();
    }
}
