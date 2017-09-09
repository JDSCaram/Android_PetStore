package br.com.jdscaram.androidpetstore.presentation.presenter.home;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import br.com.jdscaram.androidpetstore.services.RemoteCallback;
import br.com.jdscaram.androidpetstore.services.ServiceMapper;
import br.com.jdscaram.androidpetstore.services.animals.bean.PetModel;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * {Created by Jonatas Caram on 30/05/2017}.
 */

public class HomePresenterImpl implements HomePresenter {

    public Retrofit retrofit;
    private HomeInteractorView mView;

    @Inject
    public HomePresenterImpl(Retrofit retrofit, HomeInteractorView mView) {
        this.retrofit = retrofit;
        this.mView = mView;
    }

    @Override
    public void setView(@NonNull HomeInteractorView view) {
        mView = view;
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public boolean isViewAttached() {
        return mView != null;
    }


    @Override
    public void getPets(String status) {
        if (!isViewAttached()) return;
        mView.showProgress();
        ServiceMapper serviceMapper = retrofit.create(ServiceMapper.class);

        Observable<List<PetModel>> observable = serviceMapper.getPets(status);
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PetModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<PetModel> response) {
                        if (!isViewAttached()) return;
                        mView.showHome(response);
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!isViewAttached()) return;
                        mView.hideProgress();
                        mView.showErrorMessage(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

}
