package br.com.jdscaram.androidpetstore.dagger.components;

import br.com.jdscaram.androidpetstore.dagger.modules.SearchModule;
import br.com.jdscaram.androidpetstore.dagger.scope.PerActivityScope;
import br.com.jdscaram.androidpetstore.presentation.ui.search.SearchFragment;
import dagger.Component;

/**
 * {Created by Jonatas Caram on 28/07/2017}.
 */
@PerActivityScope
@Component(dependencies = ApplicationComponent.class, modules = SearchModule.class)
public interface SearchComponent {
    void inject(SearchFragment fragment);
}
