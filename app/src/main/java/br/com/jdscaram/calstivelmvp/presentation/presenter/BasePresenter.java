package br.com.jdscaram.calstivelmvp.presentation.presenter;

import android.support.annotation.NonNull;

/**
 * {Created by Jonatas Caram on 30/05/2017}.
 */

public interface BasePresenter<T extends BaseInteractorView> {
    void setView(@NonNull T view);
    void onDestroy();
    boolean isViewAttached();
}
