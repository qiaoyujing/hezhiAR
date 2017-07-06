package com.kezhi.android.kezhiar.engine;

import java.util.UUID;

/**
 * Created by qiaojing123 on 2017/4/2.
 */

public class ArCase {

    private UUID mId;
    private int arCase_main;
    private int collectView;
    private String mTextView;
    private String collectNumTextView;



    public ArCase(){
        mId=UUID.randomUUID();
    }

    public UUID getmId() {
        return mId;
    }

    public int getArCase_main() {
        return arCase_main;
    }

    public void setArCase_main(int arCase_main) {
        this.arCase_main = arCase_main;
    }
    public int getCollectView() {
        return collectView;
    }


    public void setCollectView(int collectView) {
        this.collectView = collectView;
    }

    public String getCollectNumTextView() {
        return collectNumTextView;
    }

    public void setCollectNumTextView(String collectNumTextView) {
        this.collectNumTextView = collectNumTextView;
    }

    public String getmTextView() {
        return mTextView;
    }
    public void setmTextView(String mTextView) {
        this.mTextView = mTextView;
    }
}
