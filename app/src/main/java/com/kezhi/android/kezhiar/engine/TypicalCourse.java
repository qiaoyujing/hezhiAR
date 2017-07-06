package com.kezhi.android.kezhiar.engine;

import java.util.UUID;

/**
 * Created by qiaojing123 on 2017/4/4.
 */

public class TypicalCourse {
    private UUID mId;
    private int typicalCourse_main;
    private int collectView;
    private int geli;
    private String mTextView;
    private String collectNumTextView;

    public TypicalCourse(){
        mId=UUID.randomUUID();
    }

    public UUID getmId() {
        return mId;
    }

    public int getTypicalCourse_main() {
        return typicalCourse_main;
    }

    public void setTypicalCourse_main(int typicalCourse_main) {
        this.typicalCourse_main = typicalCourse_main;
    }

    public int getCollectView() {
        return collectView;
    }

    public void setCollectView(int collectView) {
        this.collectView = collectView;
    }

    public int getGeli() {
        return geli;
    }

    public void setGeli(int geli) {
        this.geli = geli;
    }

    public String getmTextView() {
        return mTextView;
    }

    public void setmTextView(String mTextView) {
        this.mTextView = mTextView;
    }

    public String getCollectNumTextView() {
        return collectNumTextView;
    }

    public void setCollectNumTextView(String collectNumTextView) {
        this.collectNumTextView = collectNumTextView;
    }
}
