package br.com.jdscaram.androidpetstore.services;

import java.util.List;

import br.com.jdscaram.androidpetstore.services.animals.bean.PetModel;

/**
 * {Created by Jonatas Caram on 19/06/2017}.
 */

public class DataManager {

    private static DataManager sInstance;

    private final ServiceMapper mServiceMapper;

    public static DataManager getInstance() {
        if (sInstance == null) {
            sInstance = new DataManager();
        }
        return sInstance;
    }

    public DataManager() {
        mServiceMapper = ServiceFactory.makeService();
    }


    public void getPet(long id, RemoteCallback<PetModel> callback) {
        mServiceMapper.getPet(id).enqueue(callback);
    }

    public void getPets(RemoteCallback<List<PetModel>> callback) {
        mServiceMapper.getPets("pending").enqueue(callback);
    }
}
