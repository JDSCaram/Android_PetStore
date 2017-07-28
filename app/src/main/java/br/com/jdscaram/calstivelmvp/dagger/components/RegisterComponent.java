package br.com.jdscaram.calstivelmvp.dagger.components;

import br.com.jdscaram.calstivelmvp.dagger.modules.RegisterModule;
import br.com.jdscaram.calstivelmvp.dagger.scope.PerActivityScope;
import br.com.jdscaram.calstivelmvp.presentation.ui.register.RegisterFragment;
import dagger.Component;

/**
 * {Created by Jonatas Caram on 19/07/2017}.
 */
@PerActivityScope
@Component(dependencies = ApplicationComponent.class, modules = RegisterModule.class)
public interface RegisterComponent {
    void inject(RegisterFragment fragment);
}
