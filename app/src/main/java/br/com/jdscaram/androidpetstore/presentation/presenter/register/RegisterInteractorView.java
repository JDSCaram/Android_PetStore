package br.com.jdscaram.androidpetstore.presentation.presenter.register;

import br.com.jdscaram.androidpetstore.presentation.presenter.BaseInteractorView;

/**
 * {Created by Jonatas Caram on 19/07/2017.
 */

public interface RegisterInteractorView extends BaseInteractorView {
    void receiveImageURL(String url);
    void showRegisterCallback();
}
