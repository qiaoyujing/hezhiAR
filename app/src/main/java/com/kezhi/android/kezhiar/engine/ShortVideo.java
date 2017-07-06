package com.kezhi.android.kezhiar.engine;

import java.util.UUID;

/**
 * Created by qiaojing123 on 2017/4/9.
 */

public class ShortVideo {
    private UUID mId;
    private int videoView_main;//shortVideo list Imageview
    private int collectView;//shortVideo list collectionv Imageview
    private String mTextView;//video name
    private int collectNumber;
    private int lookNum;
    private int videoDescriptionId;//video details

    public ShortVideo(){
        mId=UUID.randomUUID();
    }
    public UUID getmId() {
        return mId;
    }

    public int getVideoView_main() {
        return videoView_main;
    }

    public void setVideoView_main(int videoView_main) {
        this.videoView_main = videoView_main;
    }

    public int getCollectView() {
        return collectView;
    }


    public void setCollectView(int collectView) {
        this.collectView = collectView;
    }

    public int getCollectNumber() {
        return collectNumber;
    }

    public void setCollectNumber(int collectNumber) {
        this.collectNumber = collectNumber;
    }

    public String getmTextView() {
        return mTextView;
    }
    public void setmTextView(String mTextView) {
        this.mTextView = mTextView;
    }

    public int getLookNum() {
        return lookNum;
    }

    public void setLookNum(int lookNum) {
        this.lookNum = lookNum;
    }

    public int getVideoDescriptionId() {
        return videoDescriptionId;
    }

    public void setVideoDescriptionId(int videoDescriptionId) {
        this.videoDescriptionId = videoDescriptionId;
    }
}
