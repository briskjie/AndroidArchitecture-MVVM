package com.example.xiangxuchen.architecture_mvvm_opt.interactor;

import com.example.xiangxuchen.architecture_mvvm_opt.network.HttpResult;
import com.example.xiangxuchen.architecture_mvvm_opt.repository.Repository;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import io.reactivex.Single;


/**
 * Created by xiangxuchen on 2018/2/1.
 */

public class GetRentUnitUseCase extends UseCase<Void, GetRentUnitUseCase.Params> {
    @Inject
    public GetRentUnitUseCase(RxAppCompatActivity mContext, Repository mRepository) {
        super(mContext, mRepository);
    }

    @Override
    Single<HttpResult<Void>> buildUseCaseObservable(Params params) {
        return mRepository.getSubways("");
    }

    public static class Params {
        String rentUnitId;

        public Params(String rentUnitId) {
            this.rentUnitId = rentUnitId;
        }
    }
}
