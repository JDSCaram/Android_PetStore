package br.com.jdscaram.calstivelmvp.presentation.presenter.search;

import java.util.List;

import br.com.jdscaram.calstivelmvp.presentation.presenter.BaseInteractorView;
import br.com.jdscaram.calstivelmvp.services.animals.bean.PetModel;

/**
 * {Created by Jonatas Caram on 30/05/2017}.
 */

public interface SearchInteractorView extends BaseInteractorView {
    void showHistory(List<PetModel> items);
}
