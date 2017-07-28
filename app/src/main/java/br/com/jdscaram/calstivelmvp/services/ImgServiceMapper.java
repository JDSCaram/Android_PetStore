package br.com.jdscaram.calstivelmvp.services;

import br.com.jdscaram.calstivelmvp.services.image.ImageImgurRequest;
import br.com.jdscaram.calstivelmvp.services.image.ImageResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * {Created by Jonatas Caram on 19/07/2017}.
 */

public interface ImgServiceMapper {

    @Headers({
            "Content-type: application/json"
    })


    @POST
    Call<ImageResponse> postImage(@Url String otherURL,
                                  @Header("Authorization") String auth,
                                  @Body ImageImgurRequest request
    );
}
