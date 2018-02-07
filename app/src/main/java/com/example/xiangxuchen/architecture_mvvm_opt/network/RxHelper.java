package com.example.xiangxuchen.architecture_mvvm_opt.network;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxHelper {


    public static  <T> ObservableTransformer<HttpResult<T>, T> observableTransformer() {
        return new ObservableTransformer<HttpResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<HttpResult<T>> observable) {
                return observable
                        .map(new ResultFilter<T>())
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                /*if (!NetworkUtils.isConnected()) {
                                    ToastUtils.showShort(R.string.no_network);
                                }*/
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    public static  <T> SingleTransformer<HttpResult<T>, T> singleTransformerActivity(final LifecycleProvider<ActivityEvent> provider) {
        return new SingleTransformer<HttpResult<T>, T>() {
            @Override
            public SingleSource<T> apply(@NonNull Single<HttpResult<T>> observable) {
                return observable
                        .map(new ResultFilter<T>())
                        .compose(provider.<T>bindUntilEvent(ActivityEvent.DESTROY))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    public static  <T> SingleTransformer<HttpResult<T>, T> singleTransformerFragment(final LifecycleProvider<FragmentEvent> provider) {
        return new SingleTransformer<HttpResult<T>, T>() {
            @Override
            public SingleSource<T> apply(@NonNull Single<HttpResult<T>> observable) {
                return observable
                        .map(new ResultFilter<T>())
                        .compose(provider.<T>bindUntilEvent(FragmentEvent.DESTROY))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    public static  <T> FlowableTransformer<HttpResult<T>, T> flowableTransformer() {
        return new FlowableTransformer<HttpResult<T>, T>() {
            @Override
            public Publisher<T> apply(@NonNull Flowable<HttpResult<T>> upstream) {
                return upstream
                        .map(new ResultFilter<T>())
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Subscription>() {
                            @Override
                            public void accept(Subscription subscription) throws Exception {

                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    public static class ResultFilter<T> implements Function<HttpResult<T>, T> {
        @Override
        public T apply(@NonNull HttpResult<T> result) throws Exception {
            if (result.getCode() != HttpResult.CODE_OK) {
                throw new ApiException(result.getMsg(), result.getCode());
            } else {
                return result.getData();
            }

        }
    }


}

