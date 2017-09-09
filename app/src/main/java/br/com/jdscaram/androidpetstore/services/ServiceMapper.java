package br.com.jdscaram.androidpetstore.services;

import java.util.List;

import br.com.jdscaram.androidpetstore.services.animals.bean.PetModel;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * {Created by Jonatas Caram on 06/06/2017}.
 */

public interface ServiceMapper {

    @Headers({
            "Content-type: application/json"
    })

    @GET("pet/{petId}")
    Call<PetModel> getPet(@Path("petId") long id);

    @GET("pet/findByStatus")
    Observable<List<PetModel>> getPets(@Query("status") String value);

    @POST
    Call<PetModel> registerPet(@Url String url, @Body PetModel petRequest);
}
