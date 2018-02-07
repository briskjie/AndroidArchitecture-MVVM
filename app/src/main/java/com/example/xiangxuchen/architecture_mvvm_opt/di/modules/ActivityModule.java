package com.example.xiangxuchen.architecture_mvvm_opt.di.modules;

import com.example.xiangxuchen.architecture_mvvm_opt.di.PerActivity;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xiangxuchen on 2018/2/1.
 */
@Module
public class ActivityModule {
    private final RxAppCompatActivity activity;

    public ActivityModule(RxAppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    RxAppCompatActivity provideActivity() {
        return this.activity;
    }

}
