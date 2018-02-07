package com.example.xiangxuchen.architecture_mvvm_opt.di.components;

import android.app.Application;
import android.content.Context;

import com.example.xiangxuchen.architecture_mvvm_opt.di.modules.ApplicationModule;
import com.example.xiangxuchen.architecture_mvvm_opt.repository.Repository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by xiangxuchen on 2018/2/1.
 * <p>
 * A component whose lifetime is the life of the application
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context context();

    Repository repository();

    Application application();

}
