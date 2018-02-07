package com.example.xiangxuchen.architecture_mvvm_opt.repository;

import android.content.Context;

import com.example.xiangxuchen.architecture_mvvm_opt.network.ApiService;
import com.example.xiangxuchen.architecture_mvvm_opt.network.HttpResult;
import com.example.xiangxuchen.architecture_mvvm_opt.network.ServiceFactory;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by xiangxuchen on 2018/2/1.
 */

public class RepositoryImpl implements Repository {
    private Context mContext;
    private ApiService mApiService;

    @Inject
    public RepositoryImpl(Context mContext) {
        this.mContext = mContext;
        this.mApiService = ServiceFactory.getService(ApiService.class);
    }

    @Override
    public Single<HttpResult<Void>> getSubways(String cityId) {
        return mApiService.getSubways("");
    }
}
