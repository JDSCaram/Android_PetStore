package br.com.jdscaram.calstivelmvp.services;

import java.util.List;

import br.com.jdscaram.calstivelmvp.services.animals.bean.PetModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
    Call<List<PetModel>> getPets(@Query("status") String value);

    @POST
    Call<PetModel> registerPet(@Url String url, @Body PetModel petRequest);
}
