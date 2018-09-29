package com.jianda.util;


import android.text.TextUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lh on 2017/10/30.
 */

public class StringUtils {

    public static boolean isEmptyText(String text) {
        return TextUtils.isEmpty(text) || text.equalsIgnoreCase("null");
    }

    public static boolean isEqual(String str1, String str2) {
        if (isEmptyText(str1) || isEmptyText(str2)) {
            return false;
        }
        return str1.equals(str2);
    }

    /**
     * 判断是不是一个合法的身份证
     *
     * @param idcard
     * @return
     */
    public static boolean isIdCard(String idcard) {
        IDcardUtils iv = new IDcardUtils();
        return iv.isValidatedAllIdcard(idcard);
    }

    public static String listToString(List<String> list){
        if(list==null){
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for(String string :list) {
            if(first) {
                first=false;
            }else{
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();
    }

    /**
     * 字符串截取
     * @param string
     * @param start
     * @param end
     * @return
     */
    public static String getCutOutString(String string, int start, int end){
        String newString = "";
        int length = string.length();
        if (start < 0 && start > length - 1 && end < 0 && end > length - 1){
            return newString;
        }else {
            return string.substring(start,end);
        }
    }

    /**
     * 验证字符串是否是手机号
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone){
        Pattern phones = Pattern
                .compile("^[1][34758][0-9]{9}$");
        if (phone == null || phone.trim().length() == 0)
            return false;
        return phones.matcher(phone).matches();
    }

    /**
     * 验证输入密码条件(字符与数据同时出现)
     * @return 如果是符合格式的字符串,返回 true否则为false
     */
    public static boolean  IsPassword(String str)
    {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        return match(regex, str);
    }
    /**
     * @param regex 正则表达式字符串
     * @param str 要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
