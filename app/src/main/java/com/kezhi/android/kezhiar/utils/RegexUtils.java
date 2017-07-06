package com.kezhi.android.kezhiar.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kezhi.android.kezhiar.utils.ConstUtils.*;


/**
 * Created by songtao on 2017/5/4
 */

public class RegexUtils {
    private RegexUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isEmpty(String phoneNum){
        if(TextUtils.isEmpty(phoneNum)){
            return false;
        }
        return true;
    }

    /**
     * 验证用户密码
     * <p>取值范围为a-z,A-Z,0-9,用户密码必须是6-12位</p>
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isUserpassword(CharSequence input) {
        return isMatch(REGEX_USERPASSWORD, input);
    }

    /**
     * 验证手机号（简单）
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobileSimple(CharSequence input) {
        return isMatch(REGEX_MOBILE_SIMPLE, input);
    }

    /**
     * 验证邮箱
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isEmail(CharSequence input) {
        return isMatch(REGEX_EMAIL, input);
    }


    /**
     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(String regex, CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

}
