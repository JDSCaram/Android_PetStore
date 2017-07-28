package br.com.jdscaram.calstivelmvp.services;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import br.com.jdscaram.calstivelmvp.BuildConfig;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * {Created by Jonatas Caram on 19/06/2017}.
 */

public class ServiceFactory {

    private static final int HTTP_READ_TIMEOUT = 10000;
    private static final int HTTP_CONNECT_TIMEOUT = 6000;

    public static ServiceMapper makeService() {
        return makeService(makeOkHttpClient(dispatcher()));
    }

    public static Dispatcher dispatcher() {
        return new Dispatcher();
    }

    private static OkHttpClient makeOkHttpClient(Dispatcher dispatcher) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder();
        httpClientBuilder.connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(makeLoggingInterceptor());
        httpClientBuilder.dispatcher(dispatcher);
        return httpClientBuilder.build();
    }

    private static HttpLoggingInterceptor makeLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        return logging;
    }


    private static ServiceMapper makeService(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(ServiceMapper.class);
    }

}
