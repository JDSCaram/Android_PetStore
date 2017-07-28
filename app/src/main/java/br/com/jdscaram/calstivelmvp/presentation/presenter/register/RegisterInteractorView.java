package br.com.jdscaram.calstivelmvp.presentation.presenter.register;

import br.com.jdscaram.calstivelmvp.presentation.presenter.BaseInteractorView;
import br.com.jdscaram.calstivelmvp.services.image.ImageResponse;

/**
 * {Created by Jonatas Caram on 19/07/2017.
 */

public interface RegisterInteractorView extends BaseInteractorView {
    void receiveImageURL(String url);
    void showRegisterCallback();
}
