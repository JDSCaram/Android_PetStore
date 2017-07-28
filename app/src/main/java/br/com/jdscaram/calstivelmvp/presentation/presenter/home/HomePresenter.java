package br.com.jdscaram.calstivelmvp.presentation.presenter.home;

import br.com.jdscaram.calstivelmvp.presentation.presenter.BasePresenter;

/**
 * {Created by Jonatas Caram on 30/05/2017.
 * jcaram@luxfacta.com
 * For Luxfacta Soluções de TI.
 * {@see more in https://www.luxfacta.com}.
 */

public interface HomePresenter extends BasePresenter<HomeInteractorView> {
    void getPets(String status);
}
