package com.kezhi.android.kezhiar.engine;

import android.content.Context;

import com.kezhi.android.kezhiar.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by qiaojing123 on 2017/4/9.
 */

public class ShortVideoLab {
    private static ShortVideoLab sShortVideoLab;
    private static int image[]={
            R.drawable.ic_test_image4,
            R.drawable.ic_test_image2,
            R.drawable.test_surf
    };
    private static int videoDetailIds[]={
            R.raw.surprise,
            R.raw.videodetailsss,
            R.raw.surprise
    };

    private List<ShortVideo> mShortVideo;
    public static ShortVideoLab get(Context context) {
        if (sShortVideoLab == null) {
            sShortVideoLab = new ShortVideoLab(context);
        }
        return sShortVideoLab;
    }
    private ShortVideoLab(Context context) {
        mShortVideo = new ArrayList<>();
        for(int i=0;i<3;i++){
            ShortVideo shortVideo=new ShortVideo();
            shortVideo.setCollectView(R.id.iv_imageView_second);
            shortVideo.setVideoView_main(image[i]);
            shortVideo.setmTextView("短视频"+i);
            shortVideo.setCollectNumber(40);
            shortVideo.setLookNum(1);
            shortVideo.setVideoDescriptionId(videoDetailIds[i]);
            mShortVideo.add(shortVideo);
        }

    }

    public List<ShortVideo> getShortVideo() {
        return mShortVideo;
    }

    public ShortVideo getShortVideo(UUID id) {
        for (ShortVideo shortVideo : mShortVideo) {
            if (shortVideo.getmId().equals(id)) {
                return shortVideo;
            }
        }
        return null;
    }
}
