package com.example.xiangxuchen.architecture_mvvm_opt.repository;

import com.example.xiangxuchen.architecture_mvvm_opt.network.HttpResult;

import io.reactivex.Single;

/**
 * Created by xiangxuchen on 2018/2/1.
 */

public interface Repository {
    /**
     * 获取位置信息，本地缓存，两天请求一次
     *
     * @param cityId
     * @return
     */
    Single<HttpResult<Void>> getSubways(String cityId);

}
