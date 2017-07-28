package br.com.jdscaram.calstivelmvp.presentation.presenter.search;

import br.com.jdscaram.calstivelmvp.presentation.presenter.BasePresenter;

/**
 * {Created by Jonatas Caram on 30/05/2017}.
 */

public interface SearchPresenter extends BasePresenter<SearchInteractorView> {
    void getPet(long id);
    void cancelRequest();
}
