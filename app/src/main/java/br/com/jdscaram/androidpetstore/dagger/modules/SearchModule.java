package br.com.jdscaram.androidpetstore.dagger.modules;

import br.com.jdscaram.androidpetstore.dagger.scope.PerActivityScope;
import br.com.jdscaram.androidpetstore.presentation.presenter.search.SearchInteractorView;
import dagger.Module;
import dagger.Provides;

/**
 * {Created by Jonatas Caram on 28/07/2017}.
 */
@Module
public class SearchModule {
    private SearchInteractorView mView;

    public SearchModule(SearchInteractorView mView) {
        this.mView = mView;
    }

    @Provides
    @PerActivityScope
    SearchInteractorView provideSearchInteractorView() {
        return mView;
    }
}
