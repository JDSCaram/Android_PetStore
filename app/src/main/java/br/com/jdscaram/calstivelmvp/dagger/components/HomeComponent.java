package br.com.jdscaram.calstivelmvp.dagger.components;

import br.com.jdscaram.calstivelmvp.dagger.modules.HomeModule;
import br.com.jdscaram.calstivelmvp.dagger.scope.PerActivityScope;
import br.com.jdscaram.calstivelmvp.presentation.ui.home.HomeFragment;
import dagger.Component;

/**
 * {Created by Jonatas Caram on 22/06/2017}.
 */
@PerActivityScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = HomeModule.class
)
public interface HomeComponent {
    void inject(HomeFragment fragment);
}
