package com.example.xiangxuchen.architecture_mvvm_opt.ui;


import com.android.databinding.library.baseAdapters.BR;
import com.example.xiangxuchen.architecture_mvvm_opt.R;
import com.example.xiangxuchen.architecture_mvvm_opt.databinding.ActivityMainBinding;
import com.example.xiangxuchen.architecture_mvvm_opt.ui.vm.MainViewModel;
import com.example.xiangxuchen.architecture_mvvm_opt.ui.vm.ViewModelFactory;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {
    @Inject
    ViewModelFactory<MainViewModel> viewModelFactory;

    @Override
    public void initData() {

    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    public ViewModelFactory initViewModelFactory() {
        getActivityComponent().inject(this);
        return viewModelFactory;
    }
}
