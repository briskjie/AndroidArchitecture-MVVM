package com.example.xiangxuchen.architecture_mvvm_opt.interactor;

import com.example.xiangxuchen.architecture_mvvm_opt.network.HttpResult;
import com.example.xiangxuchen.architecture_mvvm_opt.network.RxHelper;
import com.example.xiangxuchen.architecture_mvvm_opt.repository.Repository;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by xiangxuchen on 2018/2/1.
 */

public abstract class UseCase<T, Params> {
    private RxAppCompatActivity mContext;
    protected Repository mRepository;
    private CompositeDisposable disposables;

    public UseCase(RxAppCompatActivity mContext, Repository mRepository) {
        this.mContext = mContext;
        this.mRepository = mRepository;
        disposables = new CompositeDisposable();
    }

    abstract Single<HttpResult<T>> buildUseCaseObservable(Params params);

    public void execute(DisposableSingleObserver<T> single, Params params) {
        final Single<T> tSingle = this.buildUseCaseObservable(params).compose(RxHelper.<T>singleTransformerActivity(mContext));
        addDisposable(tSingle.subscribeWith(single));
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    private void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }
}
