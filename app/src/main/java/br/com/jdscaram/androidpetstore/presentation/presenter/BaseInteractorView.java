package br.com.jdscaram.androidpetstore.presentation.presenter;

/**
 * {Created by Jonatas Caram on 30/05/2017}.
 */

public interface BaseInteractorView {
    void showProgress();
    void hideProgress();
    void showMessage(String message);
    void showErrorMessage();
    void showErrorMessage(String msg);
}
