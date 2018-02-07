package com.example.xiangxuchen.architecture_mvvm_opt.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.xiangxuchen.architecture_mvvm_opt.AppApplication;
import com.example.xiangxuchen.architecture_mvvm_opt.di.components.ActivityComponent;
import com.example.xiangxuchen.architecture_mvvm_opt.di.components.ApplicationComponent;
import com.example.xiangxuchen.architecture_mvvm_opt.di.components.DaggerActivityComponent;
import com.example.xiangxuchen.architecture_mvvm_opt.di.modules.ActivityModule;
import com.example.xiangxuchen.architecture_mvvm_opt.ui.vm.ViewModelFactory;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<V extends ViewDataBinding, VM extends ViewModel>extends RxAppCompatActivity {

    protected V binding;
    protected VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewDataBinding();
        initData();

    }

    public void initData() {
    }

    public void initViewDataBinding() {
        binding = DataBindingUtil.setContentView(this, initContentView());
        binding.setVariable(initVariableId(), initViewModel());
    }

    public abstract int initVariableId();

    public abstract int initContentView();

    @SuppressWarnings("unchecked")
    ViewModel initViewModel() {
        ViewModelFactory viewModelFactory = initViewModelFactory();
        if (viewModelFactory != null) {
            //get VM virtual type
            Type t = this.getClass().getGenericSuperclass();
            Class<VM> vmClass = (Class<VM>) ((ParameterizedType) t).getActualTypeArguments()[0];
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(vmClass);
        }
        return viewModel;
    }

    public abstract ViewModelFactory initViewModelFactory();


    protected ApplicationComponent getApplicationComponent() {
        return ((AppApplication) getApplication()).getApplicationComponent();
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder().applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this)).build();
    }
}
