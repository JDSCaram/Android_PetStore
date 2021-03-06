package br.com.jdscaram.androidpetstore.dagger.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * {Created by Jonatas Caram on 14/06/2017}.
 */
@Module
public class ApplicationModule {
    private Context mContext;

    public ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }
}
