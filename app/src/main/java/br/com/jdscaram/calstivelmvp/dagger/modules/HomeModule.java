package br.com.jdscaram.calstivelmvp.dagger.modules;

import br.com.jdscaram.calstivelmvp.dagger.scope.PerActivityScope;
import br.com.jdscaram.calstivelmvp.presentation.presenter.home.HomeInteractorView;
import br.com.jdscaram.calstivelmvp.presentation.presenter.home.HomePresenter;
import dagger.Module;
import dagger.Provides;

/**
 * {Created by Jonatas Caram on 22/06/2017.
 */
@Module
public class HomeModule {
    private HomeInteractorView mView;

    public HomeModule(HomeInteractorView mView) {
        this.mView = mView;
    }

    @Provides
    @PerActivityScope
    HomeInteractorView provideHomeInteractorView(){
        return mView;
    }

}
