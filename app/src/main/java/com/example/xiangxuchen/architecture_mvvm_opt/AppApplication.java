package com.example.xiangxuchen.architecture_mvvm_opt;

import android.app.Application;

import com.example.xiangxuchen.architecture_mvvm_opt.di.components.ApplicationComponent;
import com.example.xiangxuchen.architecture_mvvm_opt.di.components.DaggerApplicationComponent;
import com.example.xiangxuchen.architecture_mvvm_opt.di.modules.ApplicationModule;

/**
 * Created by xiangxuchen on 2018/2/7.
 */

public class AppApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initInject();
    }

    private void initInject() {
        this.applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
