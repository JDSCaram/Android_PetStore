package br.com.jdscaram.androidpetstore.dagger.modules;

import br.com.jdscaram.androidpetstore.dagger.scope.PerActivityScope;
import br.com.jdscaram.androidpetstore.presentation.presenter.home.HomeInteractorView;
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
