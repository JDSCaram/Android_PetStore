package br.com.jdscaram.calstivelmvp.services.animals.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * {Created by Jonatas Caram on 06/06/2017}.
 */
@Getter @Setter
public class Tags implements Serializable{
    @SerializedName("id")
    private long mId;
    @SerializedName("name")
    private String mName;

    public Tags(long mId, String mName) {
        this.mId = mId;
        this.mName = mName;
    }
}
