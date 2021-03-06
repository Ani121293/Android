package com.example.disney.videoApp;

import android.media.Image;

import java.io.Serializable;
import java.sql.Blob;

public class Video implements Serializable {
    private String mVideoName;
    private Integer mNumber;
    private String mDescription;
    private Integer mImageId;
    private Integer markFavorite;
    private Integer mshowDetailsImageID;

    public Video(Integer number,String name,  String description, Integer imageId, Integer showDetailsImageID) {
        mImageId = imageId;
        mVideoName = name;
        mNumber = number;
        mDescription = description;
        mshowDetailsImageID = showDetailsImageID;
    }

    public Video(String name, String description, Integer imageId, Integer favoriteImageId) {
        mImageId = imageId;
        mVideoName = name;
        mDescription = description;
        markFavorite = favoriteImageId;
    }

    public Video() {
        mImageId = 0;
        mVideoName = "";
        mNumber = 0;
        mDescription = "";
        markFavorite = 0;
        mshowDetailsImageID = 0;
    }

    public Integer getMshowDetailsImageID() {
        return mshowDetailsImageID;
    }

    public void setMshowDetailsImageID(Integer mshowDetailsImageID) {
        this.mshowDetailsImageID = mshowDetailsImageID;
    }

    public Integer getMarkFavorite() {
        return markFavorite;
    }

    public void setMarkFavorite(Integer markFavorite) {
        this.markFavorite = markFavorite;
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
