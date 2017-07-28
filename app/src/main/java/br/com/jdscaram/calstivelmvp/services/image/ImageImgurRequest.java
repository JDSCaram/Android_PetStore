package br.com.jdscaram.calstivelmvp.services.image;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * {Created by Jonatas Caram on 20/07/2017}.
 */
@Getter @Setter
public class ImageImgurRequest implements Serializable{
    private String image;

    public ImageImgurRequest(String image) {
        this.image = image;
    }
}
