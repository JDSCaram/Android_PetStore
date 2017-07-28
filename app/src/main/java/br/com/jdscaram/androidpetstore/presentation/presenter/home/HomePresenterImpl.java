package br.com.jdscaram.androidpetstore.presentation.presenter.home;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import br.com.jdscaram.androidpetstore.services.RemoteCallback;
import br.com.jdscaram.androidpetstore.services.ServiceMapper;
import br.com.jdscaram.androidpetstore.services.animals.bean.PetModel;
import retrofit2.Retrofit;


/**
 * {Created by Jonatas Caram on 30/05/2017}.
 */

public class HomePresenterImpl implements HomePresenter {

    public Retrofit retrofit;
    private HomeInteractorView mView;

//    public HomePresenterImpl(DataManager dataManager) {
//        mDataManager = dataManager;
//    }

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
        retrofit.create(ServiceMapper.class)
                .getPets(status)
                .enqueue(new RemoteCallback<List<PetModel>>() {
                    @Override
                    public void onSuccess(List<PetModel> response) {
                        if (!isViewAttached()) return;
                        mView.showHome(response);
                        mView.hideProgress();
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (!isViewAttached()) return;
                        mView.hideProgress();
                        mView.showErrorMessage();
                    }
                });
    }

}
