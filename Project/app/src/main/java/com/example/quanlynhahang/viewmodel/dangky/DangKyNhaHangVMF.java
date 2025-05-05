package com.example.quanlynhahang.viewmodel.dangky;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.quanlynhahang.repository.NhaHangRepository;

public class DangKyNhaHangVMF implements ViewModelProvider.Factory{

    private final NhaHangRepository repository; // <-- Tên ngắn gọn

    public DangKyNhaHangVMF(NhaHangRepository repository) { // <-- Tên ngắn gọn
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        // Kiểm tra xem có đúng là đang yêu cầu tạo DangKyNhaHangVM không
        if (modelClass.isAssignableFrom(DangKyNhaHangViewModel.class)) { // <-- Tên ngắn gọn
            try {
                // Tạo DangKyNhaHangVM mới và truyền Repository vào
                return (T) new DangKyNhaHangViewModel(repository); // <-- Tên ngắn gọn
            } catch (Exception e) {
                throw new RuntimeException("Lỗi tạo ViewModel: " + e.getMessage(), e);
            }
        }
        throw new IllegalArgumentException("Không biết ViewModel class: " + modelClass.getName());
    }
}
