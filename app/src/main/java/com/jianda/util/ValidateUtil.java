package com.jianda.util;

/**
 * Created by zhangqun on 2018/3/6.
 */

import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式验证工具类（验证身份证、车牌号等）
 */
public class ValidateUtil {

    /**
     * 验证str是否为正确的身份证格式
     *
     * @param
     * @return
     */
    public static boolean isIdentityCard(EditText view) {
        boolean flag = true;
        String string = view.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        /*
         * { 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",
         * 21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
         * 33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",
         * 42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",
         * 51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",
         * 63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外" }
         */
        String provinces = "11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91";

        Pattern pattern = Pattern.compile("^[1-9]\\d{14}");
        Matcher matcher = pattern.matcher(string);
        Pattern pattern2 = Pattern.compile("^[1-9]\\d{16}[\\d,x,X]$");
        Matcher matcher2 = pattern2.matcher(string);
        // 粗略判断
        if (!matcher.find() && !matcher2.find()) {
            view.setError("身份证号必须为15或18位数字（最后一位可以为X）");
            flag = false;
        } else {
            // 判断出生地
            if (provinces.indexOf(string.substring(0, 2)) == -1) {
                view.setError("身份证号前两位不正确！");
                flag = false;
            }

            // 判断出生日期
            if (string.length() == 15) {
                String birth = "19" + string.substring(6, 8) + "-"
                        + string.substring(8, 10) + "-"
                        + string.substring(10, 12);
                try {
                    Date birthday = sdf.parse(birth);
                    if (!sdf.format(birthday).equals(birth)) {
                        view.setError("出生日期非法！");
                        flag = false;
                    }
                    if (birthday.after(new Date())) {
                        view.setError("出生日期不能在今天之后！");
                        flag = false;
                    }
                } catch (ParseException e) {
                    view.setError("出生日期非法！");
                    flag = false;
                }
            } else if (string.length() == 18) {
                String birth = string.substring(6, 10) + "-"
                        + string.substring(10, 12) + "-"
                        + string.substring(12, 14);
                try {
                    Date birthday = sdf.parse(birth);
                    if (!sdf.format(birthday).equals(birth)) {
                        view.setError("出生日期非法！");
                        flag = false;
                    }
                    if (birthday.after(new Date())) {
                        view.setError("出生日期不能在今天之后！");
                        flag = false;
                    }
                } catch (ParseException e) {
                    view.setError("出生日期非法！");
                    flag = false;
                }
            } else {
                view.setError("身份证号位数不正确，请确认！");
                flag = false;
            }
        }
        if (!flag) {
            view.requestFocus();
        }
        return flag;
    }

    /**
     * 不为空时，验证str是否为正确的身份证格式
     *
     * @param
     * @return
     */
    public static boolean maybeIsIdentityCard(EditText view) {
        boolean flag = true;
        String licenc = view.getText().toString();
        if (!licenc.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            /*
             * { 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",
             * 21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
             * 33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",
             * 42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",
             * 51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",
             * 63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外" }
             */
            String provinces = "11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91";

            Pattern pattern = Pattern.compile("^[1-9]\\d{14}");
            Matcher matcher = pattern.matcher(licenc);
            Pattern pattern2 = Pattern.compile("^[1-9]\\d{16}[\\d,x,X]$");
            Matcher matcher2 = pattern2.matcher(licenc);
            // 粗略判断
            if (!matcher.find() && !matcher2.find()) {
                view.setError("身份证号必须为15或18位数字（最后一位可以为X）");
                flag = false;
            } else {
                // 判断出生地
                if (provinces.indexOf(licenc.substring(0, 2)) == -1) {
                    view.setError("身份证号前两位不正确！");
                    flag = false;
                }

                // 判断出生日期
                if (licenc.length() == 15) {
                    String birth = "19" + licenc.substring(6, 8) + "-"
                            + licenc.substring(8, 10) + "-"
                            + licenc.substring(10, 12);
                    try {
                        Date birthday = sdf.parse(birth);
                        if (!sdf.format(birthday).equals(birth)) {
                            view.setError("出生日期非法！");
                            flag = false;
                        }
                        if (birthday.after(new Date())) {
                            view.setError("出生日期不能在今天之后！");
                            flag = false;
                        }
                    } catch (ParseException e) {
                        view.setError("出生日期非法！");
                        flag = false;
                    }
                } else if (licenc.length() == 18) {
                    String birth = licenc.substring(6, 10) + "-"
                            + licenc.substring(10, 12) + "-"
                            + licenc.substring(12, 14);
                    try {
                        Date birthday = sdf.parse(birth);
                        if (!sdf.format(birthday).equals(birth)) {
                            view.setError("出生日期非法！");
                            flag = false;
                        }
                        if (birthday.after(new Date())) {
                            view.setError("出生日期不能在今天之后！");
                            flag = false;
                        }
                    } catch (ParseException e) {
                        view.setError("出生日期非法！");
                        flag = false;
                    }
                } else {
                    view.setError("身份证号位数不正确，请确认！");
                    flag = false;
                }
            }
            if (!flag) {
                view.requestFocus();
            }
        }
        return flag;
    }

    /**
     * 验证str是否为正确的身份证格式
     *
     * @param
     * @return
     */
    public static boolean isIdentityCard(String licenc) {
        boolean flag = true;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        /*
         * { 11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",
         * 21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
         * 33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",
         * 42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",
         * 51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",
         * 63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外" }
         */
        String provinces = "11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91";

        Pattern pattern = Pattern.compile("^[1-9]\\d{14}");
        Matcher matcher = pattern.matcher(licenc);
        Pattern pattern2 = Pattern.compile("^[1-9]\\d{16}[\\d,x,X]$");
        Matcher matcher2 = pattern2.matcher(licenc);
        // 粗略判断
        if (!matcher.find() && !matcher2.find()) {
            flag = false;
        } else {
            // 判断出生地
            if (provinces.indexOf(licenc.substring(0, 2)) == -1) {
                flag = false;
            }
            // 判断出生日期
            if (licenc.length() == 15) {
                String birth = "19" + licenc.substring(6, 8) + "-"
                        + licenc.substring(8, 10) + "-"
                        + licenc.substring(10, 12);
                try {
                    Date birthday = sdf.parse(birth);
                    if (!sdf.format(birthday).equals(birth)) {
                        flag = false;
                    }
                    if (birthday.after(new Date())) {
                        flag = false;
                    }
                } catch (ParseException e) {
                    flag = false;
                }
            } else if (licenc.length() == 18) {
                String birth = licenc.substring(6, 10) + "-"
                        + licenc.substring(10, 12) + "-"
                        + licenc.substring(12, 14);
                try {
                    Date birthday = sdf.parse(birth);
                    if (!sdf.format(birthday).equals(birth)) {
                        flag = false;
                    }
                    if (birthday.after(new Date())) {
                        flag = false;
                    }
                } catch (ParseException e) {
                    flag = false;
                }
            } else {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 判断是否是车牌号
     */
    public static boolean isPlateNo(String CarNum) {
        //匹配第一位汉字
        String str = "京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼甲乙丙己庚辛壬寅辰戍午未申";
        if (!(CarNum == null || CarNum.equals(""))) {
            String s1 = CarNum.substring(0, 1);//获取字符串的第一个字符
            if (str.contains(s1)) {
                String s2 = CarNum.substring(1, CarNum.length());
                //不包含I O i o的判断
                if (s2.contains("I") || s2.contains("i") || s2.contains("O") || s2.contains("o")) {
                    return false;
                } else {
                    if (!CarNum.matches("^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$")) {
                        return true;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return false;
    }
}