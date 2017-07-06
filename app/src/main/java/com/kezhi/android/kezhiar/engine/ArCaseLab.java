package com.kezhi.android.kezhiar.engine;

import android.content.Context;

import com.kezhi.android.kezhiar.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by qiaojing123 on 2017/4/2.
 */

public class ArCaseLab {
    private static ArCaseLab sArCaseLab;
    private static int image[]={
            R.drawable.ic_test_image4,
            R.drawable.ic_test_image2,
            R.drawable.scrubber_control_normal_holo,
            R.drawable.ic_test_image3
    };
    private List<ArCase> mArCase;
    public static ArCaseLab get(Context context) {
        if (sArCaseLab == null) {
            sArCaseLab = new ArCaseLab(context);
        }
        return sArCaseLab;
    }
    private ArCaseLab(Context context) {
        mArCase = new ArrayList<>();
        for(int i=0;i<4;i++){
            ArCase arCase=new ArCase();
            arCase.setArCase_main(image[i]);
            arCase.setCollectView(R.id.iv_imageView_second);
            arCase.setmTextView("ARCASE"+i);
            arCase.setCollectNumTextView(""+432+i);
            mArCase.add(arCase);
        }

        }

    public List<ArCase> getArCase() {
        return mArCase;
    }

    public ArCase getArCase(UUID id) {
        for (ArCase arCase : mArCase) {
            if (arCase.getmId().equals(id)) {
                return arCase;
            }
        }
        return null;
    }
}
