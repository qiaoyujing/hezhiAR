package com.kezhi.android.kezhiar.engine;

import android.content.Context;

import com.kezhi.android.kezhiar.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by qiaojing123 on 2017/4/5.
 */

public class TypicalCourseLab {
    private static TypicalCourseLab sTypicalCourseLab;
    private List<TypicalCourse> mTypicalCourse;
    private static int image[]={
            R.drawable.ic_test_image4,
            R.drawable.test_surf,
            R.drawable.ic_test_image3,
            R.drawable.test_surf
    };
    public static TypicalCourseLab get(Context context) {
        if (sTypicalCourseLab == null) {
            sTypicalCourseLab = new TypicalCourseLab(context);
        }
        return sTypicalCourseLab;
    }
    private TypicalCourseLab(Context context) {
        mTypicalCourse = new ArrayList<>();
       for(int i=0;i<4;i++){
           TypicalCourse typicalCourse=new TypicalCourse();
           typicalCourse.setmTextView("精品课堂"+i);
           typicalCourse.setTypicalCourse_main(image[i]);
           typicalCourse.setCollectView(R.id.iv_imageView_second);
           typicalCourse.setCollectNumTextView(""+i+60);
            mTypicalCourse.add(typicalCourse);
        }

    }

    public List<TypicalCourse> getTypicalCourse() {
        return mTypicalCourse;
    }

    public TypicalCourse getTypicalCourse(UUID id) {
        for (TypicalCourse typicalCourse : mTypicalCourse) {
            if (typicalCourse.getmId().equals(id)) {
                return typicalCourse;
            }
        }
        return null;
    }
}


