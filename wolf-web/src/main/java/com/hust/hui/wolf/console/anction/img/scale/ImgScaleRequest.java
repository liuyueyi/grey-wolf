package com.hust.hui.wolf.console.anction.img.scale;

import com.hust.hui.wolf.console.anction.base.Request;

import java.util.Set;

/**
 * Created by yihui on 2016/12/30.
 */
public class ImgScaleRequest implements Request {

    /**
     * 缩放后的宽
     */
    private Integer w;

    /**
     * 缩放后的高
     */
    private Integer h;

    /**
     * 等比例缩放
     */
    private Double ratio;

    /**
     * 缩放精度
     */
    private Integer quality;


    private String path;

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
