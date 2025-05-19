package com.example.quanlynhahang.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class NhanVienMainAdapter extends FragmentStateAdapter {
    private FragmentActivity fragmentActivity;
    private ArrayList<Fragment> listFragment;
    public NhanVienMainAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> listFragment) {
        super(fragmentActivity);
        this.listFragment = listFragment;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getItemCount() {
        return listFragment.size();
    }

}
