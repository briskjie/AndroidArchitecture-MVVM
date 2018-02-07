package com.example.xiangxuchen.architecture_mvvm_opt.ui.vm;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

public class ViewModelFactory<T extends ViewModel> implements ViewModelProvider.Factory {
    private T viewModel;

    @Inject
    public ViewModelFactory(T viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        if (aClass.isAssignableFrom(viewModel.getClass())) {
            return (T) viewModel;
        }
        return null;
    }

}