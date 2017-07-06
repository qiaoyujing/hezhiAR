package com.kezhi.android.kezhiar.utils;

import android.text.InputType;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * Created by songtao on 2017/5/7.
 */

public class CheckBoxUtil {

    public static void initCheckBox(final EditText mEditText, CheckBox mCheckBox){
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    // mEditTextPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }else {
                    mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

}
