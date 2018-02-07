package com.example.xiangxuchen.architecture_mvvm_opt.network;


import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

public abstract class HttpSingleObserver<T> extends DisposableSingleObserver<T> {

    public static final int ERROR_CODE_NO_NETWORK = -1;
    public static final int ERROR_CODE_NO_LOGINED = 401;
    public static final int ERROR_CODE_NO_CARE = -2;


//    private Context context = AppApplication.getContext();


    public HttpSingleObserver() {

    }


    @Override
    protected void onStart() {

    }

    @Override
    public void onError(@NonNull Throwable e) {

    }


    @Override
    public void onSuccess(@NonNull T t) {


    }

    protected void onSuccessL(T t) {

    }


    protected void onComplete() {

    }

    /**
     * 重写该方法在ui中做一些处理
     *
     * @param e
     */
    protected void onErrorL(ApiException e) {

    }
}
