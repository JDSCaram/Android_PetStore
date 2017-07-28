package br.com.jdscaram.androidpetstore.presentation.presenter.register;

import br.com.jdscaram.androidpetstore.presentation.presenter.BasePresenter;
import br.com.jdscaram.androidpetstore.services.animals.bean.PetModel;

/**
 * {Created by Jonatas Caram on 19/07/2017}.
 */

public interface RegisterPresenter extends BasePresenter<RegisterInteractorView> {
    void sendRegister(PetModel petRequest);
    void uploadImage(String data);
}
