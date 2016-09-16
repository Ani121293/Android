package com.example.disney.myapplication;

import android.media.Image;

import java.io.Serializable;
import java.sql.Blob;

public class Video implements Serializable{
    private String mVideoName;
    private Integer mNumber;
    private String  mDescription;
    private Integer mImageId;
    public Video(String name, Integer number, String description, Integer imageId) {
        mImageId = imageId;
        mVideoName = name;
        mNumber = number;
        mDescription = description;
    }
    public Video(){
        mImageId = 0;
        mVideoName = "";
        mNumber = 0;
        mDescription = "";
    }

    public Integer getmImageId() {
        return mImageId;
    }

    public void setmImageId(Integer mImageId) {
        this.mImageId = mImageId;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public Image getmImage() {
        return mImage;
    }

    public void setmImage(Image mImage) {
        this.mImage = mImage;
    }

    public Blob getmInfo() {
        return mInfo;
    }

    public void setmInfo(Blob mInfo) {
        this.mInfo = mInfo;
    }

    private Image mImage;
    private Blob mInfo;





    public Integer getmNumber() {
        return mNumber;
    }

    public void setmNumber(Integer mNumber) {
        this.mNumber = mNumber;
    }

    public String getmVideoName() {
        return mVideoName;
    }

    public void setmVideoName(String mVideoName) {
        this.mVideoName = mVideoName;
    }
}
