package com.example.xiangxuchen.architecture_mvvm_opt.ui.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.xiangxuchen.architecture_mvvm_opt.interactor.GetRentUnitUseCase;
import com.example.xiangxuchen.architecture_mvvm_opt.network.HttpSingleObserver;

import javax.inject.Inject;

/**
 * Created by xiangxuchen on 2018/2/1.
 */

public class MainViewModel extends AndroidViewModel {
    private GetRentUnitUseCase getRentUnitUseCase;
    @Inject
    public MainViewModel(@NonNull Application application, GetRentUnitUseCase getRentUnitUseCase) {
        super(application);
        this.getRentUnitUseCase = getRentUnitUseCase;
    }

    @Override
    protected void onCleared() {
        getRentUnitUseCase.dispose();
    }

    public void getRentUnit() {
        getRentUnitUseCase.execute(new HttpSingleObserver<Void>() {
            @Override
            public void onSuccess(Void rent) {

            }
        }, new GetRentUnitUseCase.Params("1"));
    }
}
