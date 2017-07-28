package br.com.jdscaram.calstivelmvp.presentation.presenter.search;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import javax.inject.Inject;

import br.com.jdscaram.calstivelmvp.services.DataManager;
import br.com.jdscaram.calstivelmvp.services.RemoteCallback;
import br.com.jdscaram.calstivelmvp.services.ServiceMapper;
import br.com.jdscaram.calstivelmvp.services.animals.bean.PetModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * {Created by Jonatas Caram on 30/05/2017}.
 */

public class SearchPresenterImpl implements SearchPresenter {

    private Retrofit mRetrofit;
    private SearchInteractorView mView;
    private RemoteCallback<PetModel> mCallback;

    @Inject
    public SearchPresenterImpl(Retrofit mRetrofit, SearchInteractorView mView) {
        this.mRetrofit = mRetrofit;
        this.mView = mView;
    }

    @Override
    public void setView(@NonNull SearchInteractorView view) {
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
    public void getPet(long id) {
        if (!isViewAttached()) return;

        mView.showProgress();

        mCallback = new RemoteCallback<PetModel>() {
            @Override
            public void onSuccess(PetModel response) {
                if (!isViewAttached()) return;
                final PetModel pet = response;
                mView.showHistory(new ArrayList<PetModel>() {{
                    add(pet);
                }});
                mView.hideProgress();
            }

            @Override
            public void onFailed(Throwable throwable) {
                if (!isViewAttached()) return;
                mView.hideProgress();
                mView.showErrorMessage();
            }
        };

        mRetrofit.create(ServiceMapper.class)
                .getPet(id)
                .enqueue(mCallback);


    }

    @Override
    public void cancelRequest() {
        if (mCallback != null)
            mCallback.cancel();
    }

}
