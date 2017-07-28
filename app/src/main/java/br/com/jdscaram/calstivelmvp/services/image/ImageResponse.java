package br.com.jdscaram.calstivelmvp.services.image;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * {Created by Jonatas Caram on 19/07/2017}.
 */
@Getter @Setter
public class ImageResponse {

    @SerializedName("data")
    private Data data;
    @SerializedName("success")
    private boolean success;
    @SerializedName("status")
    private int status;
}
