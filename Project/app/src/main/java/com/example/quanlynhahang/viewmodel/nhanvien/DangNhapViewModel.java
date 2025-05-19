package com.example.quanlynhahang.viewmodel.nhanvien;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quanlynhahang.model.NhanVien;
import com.example.quanlynhahang.repository.NhanVienRepository;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapViewModel extends ViewModel {
    private NhanVienRepository nhanVienRepository;
    private final CompositeDisposable quanLyLuong = new CompositeDisposable();
    private FirebaseAuth mAuth;

    public DangNhapViewModel() {
        this.nhanVienRepository = NhanVienRepository.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
    }

    private MutableLiveData<NhanVien> _nhanVienDaDangNhap = new MutableLiveData<>();
    public LiveData<NhanVien> getNhanVienDaDangNhap() {return _nhanVienDaDangNhap;}

    private MutableLiveData<String> _dangNhapThanhCong = new MutableLiveData<>();
    public LiveData<String> getDangNhapThanhCong() {return _dangNhapThanhCong;}

    private MutableLiveData<Boolean> _dangTai = new MutableLiveData<>();
    public LiveData<Boolean> getDangTai() {return _dangTai;}

    public void dangNhap(String taiKhoan,String matKhau){
        _dangTai.setValue(true);
        mAuth.signInWithEmailAndPassword(taiKhoan,matKhau)
                .addOnSuccessListener(authResult -> {
                    quanLyLuong.add(nhanVienRepository.dangNhap(authResult.getUser().getUid())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableSingleObserver<NhanVien>() {
                                @Override
                                public void onSuccess(@NonNull NhanVien nhanVien) {
                                    _dangTai.setValue(false);
                                    if(nhanVien != null){
                                        _dangNhapThanhCong.setValue(null);
                                        _nhanVienDaDangNhap.setValue(nhanVien);
                                    }
                                    else{
                                        _dangNhapThanhCong.setValue("Đăng nhập thất bại");
                                        mAuth.signOut();
                                        _nhanVienDaDangNhap.setValue(null);
                                    }

                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    _dangTai.setValue(false);
                                    _dangNhapThanhCong.setValue(e.getMessage());
                                }
                            }));
                }).addOnFailureListener(errorMessage ->{
                    _dangNhapThanhCong.setValue(errorMessage.getMessage());
                });
    }

}
