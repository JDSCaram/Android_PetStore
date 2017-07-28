package br.com.jdscaram.calstivelmvp.services.animals.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * {Created by Jonatas Caram on 06/06/2017}.
 */
@Getter
@Setter
public class PetModel implements Serializable {
    @SerializedName("id")
    private long mId;
    @SerializedName("category")
    private Category mCategory;
    @SerializedName("name")
    private String mName;
    @SerializedName("photoUrls")
    private String[] mPhotourls;
    @SerializedName("tags")
    private List<Tags> mTags;
    @SerializedName("status")
    private String mStatus;
}
