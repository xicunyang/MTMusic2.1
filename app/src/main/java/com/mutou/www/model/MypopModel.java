package com.mutou.www.model;

/**
 * Created by 木头 on 2018/7/1.
 */

public class MypopModel {
    private int imageId;
    private String text;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MypopModel(int imageId, String text) {
        this.imageId = imageId;
        this.text = text;
    }
}
