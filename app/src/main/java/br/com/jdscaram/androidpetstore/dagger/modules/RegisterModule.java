package br.com.jdscaram.androidpetstore.dagger.modules;

import br.com.jdscaram.androidpetstore.dagger.scope.PerActivityScope;
import br.com.jdscaram.androidpetstore.presentation.presenter.register.RegisterInteractorView;
import dagger.Module;
import dagger.Provides;

/**
 * {Created by Jonatas Caram on 19/07/2017}.
 */
@Module
public class RegisterModule {
    private RegisterInteractorView mView;

    public RegisterModule(RegisterInteractorView mView) {
        this.mView = mView;
    }

    @Provides
    @PerActivityScope
    RegisterInteractorView provideRegisterInteractorView(){
        return mView;
    }
}
