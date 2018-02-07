package com.example.xiangxuchen.architecture_mvvm_opt.network;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    /**
     * 获取位置信息，本地缓存，两天请求一次
     *
     * @param cityId
     * @return
     */
    @GET("common/subways")
    Single<HttpResult<Void>> getSubways(@Query("cityId") String cityId);


}
