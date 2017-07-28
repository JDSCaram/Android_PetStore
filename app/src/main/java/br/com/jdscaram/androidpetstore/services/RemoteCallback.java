package br.com.jdscaram.androidpetstore.services;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * {Created by Jonatas Caram on 19/06/2017}.
 */

public abstract class RemoteCallback<T> implements Callback<T> {

    private static List<RemoteCallback> mList = new ArrayList<>();

    private boolean isCanceled = false;
    private Object mTag = null;

    public RemoteCallback() {
        mList.add(this);
    }

    public RemoteCallback(Object o) {
        mTag = o;
        mList.add(this);
    }

    public void cancel() {
        isCanceled = true;
        mList.remove(this);
    }

    public static void cancelAll() {
        Iterator<RemoteCallback> iterator = mList.iterator();
        while (iterator.hasNext()) {
            iterator.next().isCanceled = true;
            iterator.remove();
        }
    }

    public static void cancel(Object tag) {
        if (tag != null) {
            Iterator<RemoteCallback> iterator = mList.iterator();
            RemoteCallback item;
            while (iterator.hasNext()) {
                item = iterator.next();
                if (tag.equals(item.mTag)) {
                    item.isCanceled = true;
                    iterator.remove();
                }
            }
        }
    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if (!isCanceled)
            switch (response.code()) {
                case HttpsURLConnection.HTTP_OK:
                case HttpsURLConnection.HTTP_CREATED:
                case HttpsURLConnection.HTTP_ACCEPTED:
                case HttpsURLConnection.HTTP_NOT_AUTHORITATIVE:
                    if (response.body() != null) {
                        onSuccess(response.body());
                    }
                    break;

//            case HttpURLConnection.HTTP_NOT_FOUND:
//                onError();
//                break;

                default:
                    onFailed(new Throwable("Default " + response.code() + " " + response.message()));
            }
        else
            mList.remove(this);
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable throwable) {
        if (isCanceled)
            onFailed(throwable);

        mList.remove(this);
    }

    public abstract void onSuccess(T response);

    //    public abstract void onError(String message);
    public abstract void onFailed(Throwable throwable);
}
