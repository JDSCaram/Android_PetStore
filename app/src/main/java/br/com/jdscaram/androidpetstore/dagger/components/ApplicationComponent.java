package br.com.jdscaram.androidpetstore.dagger.components;


import android.content.Context;

import javax.inject.Singleton;

import br.com.jdscaram.androidpetstore.dagger.modules.ApplicationModule;
import br.com.jdscaram.androidpetstore.dagger.modules.RetrofitModule;
import dagger.Component;
import retrofit2.Retrofit;

/**
 * {Created by Jonatas Caram on 14/06/2017}.
 */
@Singleton
@Component(modules = {ApplicationModule.class, RetrofitModule.class})
public interface ApplicationComponent {
    void inject(Context app);
    Retrofit provideRetrofit();
}
