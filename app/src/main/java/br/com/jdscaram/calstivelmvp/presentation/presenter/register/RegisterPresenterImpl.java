package br.com.jdscaram.calstivelmvp.presentation.presenter.register;

import android.content.Intent;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import br.com.jdscaram.calstivelmvp.presentation.presenter.BaseInteractorView;
import br.com.jdscaram.calstivelmvp.services.ImgServiceMapper;
import br.com.jdscaram.calstivelmvp.services.RemoteCallback;
import br.com.jdscaram.calstivelmvp.services.ServiceMapper;
import br.com.jdscaram.calstivelmvp.services.animals.bean.PetModel;
import br.com.jdscaram.calstivelmvp.services.image.ImageImgurRequest;
import br.com.jdscaram.calstivelmvp.services.image.ImageResponse;
import br.com.jdscaram.calstivelmvp.toolbox.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * {Created by Jonatas Caram on 19/07/2017}.
 */

public class RegisterPresenterImpl implements RegisterPresenter {

    private Retrofit retrofit;
    private RegisterInteractorView mView;

    @Inject
    public RegisterPresenterImpl(Retrofit retrofit, RegisterInteractorView mView) {
        this.retrofit = retrofit;
        this.mView = mView;
    }

    @Override
    public void setView(@NonNull RegisterInteractorView view) {
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
    public void sendRegister(PetModel petModel) {
        if (!isViewAttached()) return;

        mView.showProgress();
        retrofit.create(ServiceMapper.class)
                .registerPet(Constants.BASE_URL + "/pet", petModel)
                .enqueue(new Callback<PetModel>() {
                    @Override
                    public void onResponse(@NonNull Call<PetModel> call, @NonNull Response<PetModel> response) {
                        mView.hideProgress();
                        mView.showRegisterCallback();
                    }

                    @Override
                    public void onFailure(@NonNull Call<PetModel> call, @NonNull Throwable throwable) {
                        mView.hideProgress();
                    }
                });
//
    }

    @Override
    public void uploadImage(String imageBase64) {
        if (!isViewAttached()) return;
        if (imageBase64 != null) {
            ImageImgurRequest request = new ImageImgurRequest(imageBase64);
            mView.showProgress();
            retrofit.create(ImgServiceMapper.class)
                    .postImage(Constants.BASE_URL_IMGUR, Constants.AUTH_IMGUR_CLIENT_ID, request)
                    .enqueue(new RemoteCallback<ImageResponse>() {
                        @Override
                        public void onSuccess(ImageResponse response) {
                            if (!isViewAttached()) return;
                            mView.receiveImageURL(response.getData().getLink());
                            mView.hideProgress();

                        }

                        @Override
                        public void onFailed(Throwable throwable) {
                            if (!isViewAttached()) return;
                            mView.hideProgress();
                        }
                    });

        }
    }
}
