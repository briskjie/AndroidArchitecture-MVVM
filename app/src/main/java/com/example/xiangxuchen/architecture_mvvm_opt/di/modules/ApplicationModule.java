package com.example.xiangxuchen.architecture_mvvm_opt.di.modules;

import android.app.Application;
import android.content.Context;


import com.example.xiangxuchen.architecture_mvvm_opt.repository.Repository;
import com.example.xiangxuchen.architecture_mvvm_opt.repository.RepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xiangxuchen on 2018/2/1.
 */
@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return this.application;
    }

    @Provides
    @Singleton
    Repository providesRepository(RepositoryImpl repository) {
        return repository;
    }
}
