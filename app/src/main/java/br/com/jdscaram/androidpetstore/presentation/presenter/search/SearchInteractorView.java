package br.com.jdscaram.androidpetstore.presentation.presenter.search;

import java.util.List;

import br.com.jdscaram.androidpetstore.presentation.presenter.BaseInteractorView;
import br.com.jdscaram.androidpetstore.services.animals.bean.PetModel;

/**
 * {Created by Jonatas Caram on 30/05/2017}.
 */

public interface SearchInteractorView extends BaseInteractorView {
    void showHistory(List<PetModel> items);
}
