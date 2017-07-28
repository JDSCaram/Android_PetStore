package br.com.jdscaram.androidpetstore.presentation.presenter.search;

import br.com.jdscaram.androidpetstore.presentation.presenter.BasePresenter;

/**
 * {Created by Jonatas Caram on 30/05/2017}.
 */

public interface SearchPresenter extends BasePresenter<SearchInteractorView> {
    void getPet(long id);
    void cancelRequest();
}
