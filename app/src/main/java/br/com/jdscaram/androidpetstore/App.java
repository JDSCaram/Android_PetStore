package br.com.jdscaram.androidpetstore;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;

import br.com.jdscaram.androidpetstore.dagger.components.ApplicationComponent;
import br.com.jdscaram.androidpetstore.dagger.components.DaggerApplicationComponent;
import br.com.jdscaram.androidpetstore.dagger.modules.RetrofitModule;
/**
 * {Created by Jonatas Caram on 30/05/2017}.
 */
public class App extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        initDaggerComponent();
    }

    private void initDaggerComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .retrofitModule(new RetrofitModule(BuildConfig.BASE_URL))
                .build();
        mApplicationComponent.inject(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static ApplicationComponent get(Context context) {
        return (ApplicationComponent) context.getApplicationContext();
    }
}
