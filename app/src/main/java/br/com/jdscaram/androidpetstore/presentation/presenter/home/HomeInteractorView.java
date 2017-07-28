package br.com.jdscaram.androidpetstore.presentation.presenter.home;

import java.util.List;

import br.com.jdscaram.androidpetstore.presentation.presenter.BaseInteractorView;
import br.com.jdscaram.androidpetstore.services.animals.bean.PetModel;

/**
 * {Created by Jonatas Caram on 30/05/2017}.
 */

public interface HomeInteractorView extends BaseInteractorView {
    void showHome(List<PetModel> items);
}
