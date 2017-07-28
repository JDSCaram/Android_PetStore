package br.com.jdscaram.calstivelmvp.services.image;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * {Created by Jonatas Caram on 19/07/2017}.
 */
@Getter @Setter
public class Data {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("datetime")
    private int datetime;
    @SerializedName("type")
    private String type;
    @SerializedName("animated")
    private boolean animated;
    @SerializedName("width")
    private int width;
    @SerializedName("height")
    private int height;
    @SerializedName("size")
    private int size;
    @SerializedName("views")
    private int views;
    @SerializedName("bandwidth")
    private int bandWidth;
    @SerializedName("vote")
    private String vote;
    @SerializedName("favorite")
    private boolean favorite;
    @SerializedName("nsfw")
    private String nsfw;
    @SerializedName("section")
    private String section;
    @SerializedName("account_url")
    private String account_url;
    @SerializedName("account_id")
    private int account_id;
    @SerializedName("is_ad")
    private boolean is_ad;
    @SerializedName("in_most_viral")
    private boolean inMostViral;
    @SerializedName("ad_type")
    private int adType;
    @SerializedName("ad_url")
    private String adUrl;
    @SerializedName("in_gallery")
    private boolean inGallery;
    @SerializedName("deletehash")
    private String deleteHash;
    @SerializedName("name")
    private String name;
    @SerializedName("link")
    private String link;
}
