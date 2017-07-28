package br.com.jdscaram.calstivelmvp.presentation.presenter.register;

import android.content.Intent;

import br.com.jdscaram.calstivelmvp.presentation.presenter.BasePresenter;
import br.com.jdscaram.calstivelmvp.services.animals.bean.PetModel;

/**
 * {Created by Jonatas Caram on 19/07/2017}.
 */

public interface RegisterPresenter extends BasePresenter<RegisterInteractorView> {
    void sendRegister(PetModel petRequest);
    void uploadImage(String data);
}
